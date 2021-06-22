package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.brushes.BlurType;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.brushes.ShadowType;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.model.TextControlsDto;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.paintview.helpers.AngleHelper;
import com.jacstuff.sketchy.paintview.helpers.BlurHelper;
import com.jacstuff.sketchy.paintview.helpers.GradientHelper;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.paintview.helpers.ShadowHelper;
import com.jacstuff.sketchy.ui.SettingsPopup;


public class PaintView extends View {

    private int canvasWidth, canvasHeight;

    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private final Paint paint, shadowPaint, previewPaint;
    private int brushSize, halfBrushSize;
    private Bitmap bitmap;
    private Canvas canvas;
    private ColorSelector colorSelector;
    private BrushStyle currentBrushStyle = BrushStyle.FILL;
    private Brush currentBrush;
    private BrushFactory brushFactory;
    private boolean wasCanvasModifiedSinceLastSaveOrReset;
    private boolean isCanvasLocked;
    private Matrix matrix;
    private MainViewModel viewModel;
    private Bitmap glitchImage;
    private final Paint glitchPaint = new Paint();


    private final ShadowHelper shadowHelper;
    private final BlurHelper blurHelper;
    private GradientHelper gradientHelper;
    private final AngleHelper angleHelper;
    private KaleidoscopeHelper kaleidoscopeHelper;

    private boolean isPreviewLayerToBeDrawn;
    private Bitmap previewBitmap;
    private final BitmapHistory bitmapHistory;
    private final Paint drawPaint = new Paint();

    private final PaintGroup paintGroup;
    private SettingsPopup settingsPopup;
    private boolean ignoreMoveAndUpActions = false;
    private TextControlsDto textControlsDto;

    public PaintView(Context context) {
        this(context, null);
    }


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = createPaint(Color.WHITE);
        previewPaint =  createPaint(Color.DKGRAY);
        shadowPaint = createPaint(Color.BLACK);

        paint.setAntiAlias(true);
        paint.setDither(true);

        paintGroup = new PaintGroup(paint, previewPaint, shadowPaint);

        bitmapHistory = new BitmapHistory(context);

        shadowHelper = new ShadowHelper(shadowPaint);
        blurHelper = new BlurHelper(paint);
        angleHelper = new AngleHelper();
    }


    public void setKaleidoscopeHelper(KaleidoscopeHelper kaleidoscopeHelper){
        this.kaleidoscopeHelper = kaleidoscopeHelper;
    }

    public void setGradientHelper(GradientHelper gradientHelper){
        this.gradientHelper = gradientHelper;
    }

    public void initBrushes(){
        brushFactory = new BrushFactory(canvas, paintGroup, brushSize, textControlsDto);
        currentBrush = brushFactory.getResettedBrushFor(BrushShape.CIRCLE, currentBrushStyle);
    }


    public void init(MainViewModel viewModel, int canvasWidth, int canvasHeight, SettingsPopup settingsPopup, TextControlsDto textControlsDto) {
        this.viewModel = viewModel;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.settingsPopup = settingsPopup;
        this.textControlsDto = textControlsDto;

        if(canvasHeight <= 0){
            canvasHeight = this.getHeight();
        }
        if(canvasHeight <= 0){
            canvasHeight = 1000;
        }
        bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        initBrushes();
        if(bitmapHistory.isEmpty()){
            drawPlainBackgroundAndSaveToHistory();
        }

        gradientHelper.init(paint);
        kaleidoscopeHelper.setDefaultCenter(canvasWidth/2, canvasHeight/2);
        paint.setColor(viewModel.color);
        initMatrixIfNull();
        invalidate();
    }


    private void initMatrixIfNull(){
        if(matrix == null){
            matrix = new Matrix();
            matrix.postScale(1,1);
        }
    }

    private Paint createPaint(int color){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setColor(color);
        return paint;
    }


    public PaintGroup getPaintGroup(){
        return this.paintGroup;
    }


    public void notifyPictureSaved(){
        wasCanvasModifiedSinceLastSaveOrReset = false;
    }


    public BitmapHistory getBitmapHistory(){
        return bitmapHistory;
    }


    public void useMostRecentHistory(){
        useHistory(false);
    }


    public boolean canvasWasModifiedSinceLastSaveOrReset(){
        return wasCanvasModifiedSinceLastSaveOrReset;
    }


    public void setBrushStyle(BrushStyle brushStyle){
        currentBrushStyle = brushStyle;
        currentBrush.setStyle(brushStyle);
    }


    public Bitmap getBitmap(){
        return bitmap;
    }


    public void setBrushShape(BrushShape brushShape){
        currentBrush = brushFactory.getResettedBrushFor(brushShape, currentBrushStyle);
        currentBrush.setBrushSize(brushSize);
    }


    public void setKaleidoscopeSegments(int numberOfSegments){
        kaleidoscopeHelper.setSegments(numberOfSegments);
    }


    public void setAnglePreset(AngleType angleType){
        angleHelper.setAngle(angleType);
    }


    public void setExactAngle(int angle){
        angleHelper.setAngle(AngleType.OTHER);
        angleHelper.setAngle(angle);
    }


    public void setBlurType(BlurType blurType){
        blurHelper.setBlurType(blurType);
    }


    public void setGradientType(GradientType gradientType){
        gradientHelper.setGradientType(gradientType);
    }


    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
        halfBrushSize = brushSize /2;
        currentBrush.setBrushSize(brushSize);
        gradientHelper.updateBrushSize(brushSize);
        shadowHelper.updateOffsetFactor(halfBrushSize);
    }


    public void setRadialGradientRadius(int radiusFactor){
        gradientHelper.setGradientRadius(radiusFactor);
    }


    public void setLineWidth(int lineWidth){
        paintGroup.setStrokeWidth(lineWidth);
        currentBrush.notifyStyleChange();
    }


    public void setBlurRadius(int blurRadius){
        blurHelper.setBlurRadius(blurRadius);
    }


    public void setShadowType(ShadowType shadowType){
        shadowHelper.set(shadowType);
    }


    public void setShadowSize(int size){
        shadowHelper.setShadowSize(size, halfBrushSize);
    }


    public void resetCanvas(){
        wasCanvasModifiedSinceLastSaveOrReset = false;
        isCanvasLocked = true;
        drawPlainBackgroundAndSaveToHistory();
        invalidate();
        isCanvasLocked = false;
    }


    private void drawPlainBackgroundAndSaveToHistory(){
        Paint blankPaint = new Paint();
        blankPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        blankPaint.setColor(DEFAULT_BG_COLOR);
        canvas.drawRect(0,0, canvasWidth, canvasHeight, blankPaint);
        bitmapHistory.push(bitmap);
        invalidate();
    }


    public void setColorSelector(ColorSelector colorSelector){
        this.colorSelector = colorSelector;
    }


    @Override
    protected void onDraw(Canvas viewCanvas) {
        viewCanvas.save();
        viewCanvas.drawColor(DEFAULT_BG_COLOR);
        viewCanvas.drawBitmap(isPreviewLayerToBeDrawn ? previewBitmap : bitmap, 0, 0, drawPaint);
        viewCanvas.restore();
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {

        if(isPopupBeingDismissed(event) | isCanvasLocked){
            return true;
        }

        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();

        if(!isTouchDownEventWithLineShape(event)){
            assignColorsBlursAndGradients(x,y);
        }
        wasCanvasModifiedSinceLastSaveOrReset = true;
        try {
            if (BrushShape.LINE == currentBrush.getBrushShape()) {
                handleLineDrawing(x, y, event);
            } else {
                handleDrawing(x, y, event);
            }
            if (action == MotionEvent.ACTION_UP) {
                bitmapHistory.push(bitmap);
            }
        }catch (IllegalArgumentException e){
            //do nothing, sometimes there's an illegalArgException related to drawing gradients
            // immediately after rotating screen
        }
        return true;
    }


    public void undo(){
        useHistory(true);
    }


    public void useHistory(boolean isCurrentDiscarded){
        Bitmap historyBitmap = isCurrentDiscarded ?  bitmapHistory.getPrevious() : bitmapHistory.getCurrent();

        if(historyBitmap == null){
            return;
        }
        initMatrixIfNull();
        canvas.drawBitmap(historyBitmap, matrix, drawPaint);
        disablePreviewLayer();
        invalidate();
    }


    private boolean isPopupBeingDismissed(MotionEvent event){
        if(settingsPopup.isVisible()){
            settingsPopup.dismiss();
            ignoreMoveAndUpActions = true;
            return true;
        }
        if(ignoreMoveAndUpActions){
            if (event.getAction() == MotionEvent.ACTION_UP){
                ignoreMoveAndUpActions = false;
            }
            return true;
        }
        return false;
    }


    private void assignColorsBlursAndGradients(float x, float y){
        viewModel.color = colorSelector.getNextColor();
        if(viewModel.color != paint.getColor()){
            viewModel.previousColor = paint.getColor();
        }
        paint.setColor(viewModel.color);
        gradientHelper.assignGradient(x,y, viewModel.color, viewModel.previousColor);
    }


    private boolean isTouchDownEventWithLineShape(MotionEvent event){
        return event.getAction() != MotionEvent.ACTION_DOWN && currentBrush.getBrushShape() == BrushShape.LINE;
    }


    private void handleLineDrawing(float x, float y, MotionEvent event){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                currentBrush.onTouchDown(x,y, paint);
                break;
            case MotionEvent.ACTION_MOVE :
                enablePreviewLayer();
                currentBrush.onTouchMove(x,y, paint);
                break;
            case MotionEvent.ACTION_UP :
                disablePreviewLayer();
                if(kaleidoscopeHelper.isEnabled()){
                    drawKaleidoscope(x, y, paint,true);
                }
                else{
                    drawDragLine(x,y);
                }
        }
        invalidate();
    }


    private void handleDrawing(float x, float y, MotionEvent event){
        angleHelper.updateAngle();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                kaleidoscopeHelper.setCenter(x,y);
                drawToCanvas(x,y, paint);
                break;

            case MotionEvent.ACTION_MOVE :
                disablePreviewLayer();
                drawToCanvas(x,y, paint);
                enablePreviewLayer();
                drawToCanvas(x,y, previewPaint);
                break;

            case MotionEvent.ACTION_UP :
                disablePreviewLayer();
                invalidate();
        }
    }


    private void drawToCanvas(float x, float y, Paint paint){
        if(kaleidoscopeHelper.isEnabled()){
            drawKaleidoscope(x,y, paint);
            return;
        }
        rotateAndDraw(x,y, paint);
        invalidate();
    }


    private void enablePreviewLayer(){
        isPreviewLayerToBeDrawn = true;
        previewBitmap = Bitmap.createBitmap(bitmap);
        canvas.setBitmap(previewBitmap);
    }


    private void disablePreviewLayer(){
        isPreviewLayerToBeDrawn = false;
        canvas.setBitmap(bitmap);
    }


    private void drawKaleidoscope(float x, float y, Paint paint){
        drawKaleidoscope(x,y, paint, false);
    }


    private void drawKaleidoscope(float x, float y, Paint paint, boolean isDragLine){

        canvas.save();
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());

        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()){
            drawKaleidoscopeSegment(x, y, angle, isDragLine, paint);
        }

        if(viewModel.isGlitchModeEnabled){
         drawGlitchSegments(x,y);
        }

        canvas.restore();
    }


    private void drawGlitchSegments(float x, float y){
        glitchImage = Bitmap.createScaledBitmap(bitmap, 500, 500, false);

        BitmapShader fillBMPshader = new BitmapShader(glitchImage, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        glitchPaint.setStyle(Paint.Style.FILL);
        glitchPaint.setShader(fillBMPshader);
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()) {
            drawGlitchModeSegment(x, y, angle);
        }

    }


    private void drawGlitchModeSegment(float x, float y, float angle){
        canvas.save();
        canvas.rotate(angle);
        float gx = x - (kaleidoscopeHelper.getCenterX());// + (brushSize/2f));
        float gy = y - (kaleidoscopeHelper.getCenterY());// + (brushSize/2f));
        canvas.drawBitmap(glitchImage ,gx, gy, glitchPaint);
        //canvas.drawCircle(gx, gy, getMeasuredWidth() /3.5f, glitchPaint);
        canvas.restore();
        invalidate();
    }




    private void drawKaleidoscopeSegment(float x, float y, float angle, boolean isDragLine, Paint paint){
        canvas.save();
        canvas.rotate(angle);

        if(isDragLine){
            drawDragLine(x , y, kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
        }
        else {
            rotateAndDraw(x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY(), paint);
        }
        canvas.restore();
        invalidate();
    }


    private void drawDragLine(float x, float y){
        drawDragLine(x,y,0,0);
    }


    private void drawDragLine(float x, float y, int offsetX, int offsetY){
        if(shadowHelper.isShadowEnabled()){
            currentBrush.onTouchUp(x,y, offsetX, offsetY, shadowPaint);
        }
        currentBrush.onTouchUp(x,y, offsetX, offsetY, paint);
    }


    private void rotateAndDraw(float x, float y, Paint paint){
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(angleHelper.getAngle());
        if(shadowHelper.isShadowEnabled()){
            currentBrush.onTouchDown(0,0, shadowPaint);
        }
        currentBrush.onTouchDown(0,0, paint);
        canvas.restore();
    }

}





