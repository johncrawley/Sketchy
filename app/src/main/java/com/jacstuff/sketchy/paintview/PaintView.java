package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.brushes.BlurType;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.brushes.ShadowType;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.paintview.helpers.AngleHelper;
import com.jacstuff.sketchy.paintview.helpers.BlurHelper;
import com.jacstuff.sketchy.paintview.helpers.GradientHelper;
import com.jacstuff.sketchy.paintview.helpers.ShadowHelper;


public class PaintView extends View {

    private int canvasWidth, canvasHeight, midCanvasX, midCanvasY;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private Paint paint, shadowPaint, previewPaint;
    private int brushSize, halfBrushSize;
    private Bitmap bitmap;
    private Canvas canvas;
    private ColorSelector colorSelector;
    private BrushStyle currentBrushStyle = BrushStyle.FILL;
    private Brush currentBrush;
    private BrushFactory brushFactory;
    private boolean wasCanvasModifiedSinceLastSaveOrReset;
    private boolean isCanvasLocked;
    private int previousColor = Color.WHITE;

    private ShadowHelper shadowHelper;
    private BlurHelper blurHelper;
    private GradientHelper gradientHelper;
    private AngleHelper angleHelper;

    private final int TOTAL_DEGREES = 360;
    private int degree_increment = 30;
    private boolean isKaleidoscopeEnabled = false;
    private boolean displayPreviewLayer;
    private Bitmap previewBitmap;
    private Paint drawPaint = new Paint();

    private PaintGroup paintGroup;

    public PaintView(Context context) {
        this(context, null);
    }


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);

        previewPaint = new Paint();
        previewPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        previewPaint.setStrokeJoin(Paint.Join.ROUND);
        previewPaint.setStrokeCap(Paint.Cap.SQUARE);
        previewPaint.setColor(Color.DKGRAY);

        shadowPaint = new Paint();
        shadowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        shadowPaint.setStrokeJoin(Paint.Join.ROUND);
        shadowPaint.setStrokeCap(Paint.Cap.SQUARE);
        shadowPaint.setColor(Color.BLACK);

        paintGroup = new PaintGroup(paint, previewPaint, shadowPaint);


        shadowHelper = new ShadowHelper(shadowPaint);
        blurHelper = new BlurHelper(paint);
        gradientHelper = new GradientHelper(paint);
        angleHelper = new AngleHelper();
    }


    public void notifyPictureSaved(){
        wasCanvasModifiedSinceLastSaveOrReset = false;
    }


    public boolean canvasWasModifiedSinceLastSaveOrReset(){
        return wasCanvasModifiedSinceLastSaveOrReset;
    }


    public void setBrushStyle(BrushStyle brushStyle){
        currentBrushStyle = brushStyle;
        currentBrush.setStyle(brushStyle);
    }


    public void setCurrentColor(int color){
        paint.setColor(color);
    }


    public Bitmap getBitmap(){
        return bitmap;
    }


    public void setBrushShape(BrushShape brushShape){
        currentBrush = brushFactory.getResettedBrushFor(brushShape, currentBrushStyle);
        currentBrush.setBrushSize(brushSize);
    }

    public void setKaleidoScopeSegments(int segments){
        isKaleidoscopeEnabled = segments > 1;
        this.degree_increment = TOTAL_DEGREES / segments;
    }


    public void setAnglePreset(AngleType angleType){
        angleHelper.setAngle(angleType);
    }

    public void setAngle(int angle){
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
        gradientHelper.setGradientRadius(radiusFactor, canvasWidth);
    }


    public void setLineWidth(int lineWidth){

        paint.setStrokeWidth(lineWidth);
        previewPaint.setStrokeWidth(lineWidth);
        shadowPaint.setStrokeWidth(lineWidth);
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


    public void init(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        midCanvasX = canvasWidth/2;
        midCanvasY = canvasHeight /2;
        drawPlainBackground();
        initBrushes();
    }


    public void resetCanvas(){
        wasCanvasModifiedSinceLastSaveOrReset = false;
        isCanvasLocked = true;
        int currentColor = paint.getColor();
        resetStylesAndColors();
        drawPlainBackground();
        invalidate();
        currentBrush.setStyle(currentBrushStyle);
        paint.setColor(currentColor);
        isCanvasLocked = false;
    }


    private void resetStylesAndColors(){
        paint.setColor(DEFAULT_BG_COLOR);
        paint.setPathEffect(null);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    private void drawPlainBackground(){
        int currentColor = paint.getColor();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0, canvasWidth, canvasHeight, paint);
        paint.setColor(currentColor);
    }


    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        canvas = new Canvas(bitmap);
        paint.setColor(DEFAULT_BG_COLOR);
        initBrushes();
    }


    private void initBrushes(){
        brushFactory = new BrushFactory(canvas, paintGroup, brushSize);
        currentBrush = brushFactory.getResettedBrushFor(BrushShape.CIRCLE, currentBrushStyle);
    }


    public void setColorSelector(ColorSelector colorSelector){
        this.colorSelector = colorSelector;
    }


    @Override
    protected void onDraw(Canvas viewCanvas) {
        viewCanvas.save();
        viewCanvas.drawColor(DEFAULT_BG_COLOR);
        if(displayPreviewLayer){
            viewCanvas.drawBitmap(previewBitmap,0,0, drawPaint);
        }
        else {
            viewCanvas.drawBitmap(bitmap, 0, 0, drawPaint);
        }
        viewCanvas.restore();
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(!isTouchDownEventWithLineShape(event)){
            assignColorsBlursAndGradients(x,y);
        }

        if(BrushShape.LINE == currentBrush.getBrushShape()){
            handleLineDrawing(event, x,y);
            return true;
        }
        handleDrawing(x,y,event.getAction());

        return true;
    }


    private void assignColorsBlursAndGradients(float x, float y){
        int color = colorSelector.getNextColor();
        if(color != paint.getColor()){
            previousColor = paint.getColor();
        }
        paint.setColor(color);
        blurHelper.assignBlur();
        gradientHelper.assignGradient(x,y, color, previousColor);
    }


    private boolean isTouchDownEventWithLineShape(MotionEvent event){
        return event.getAction() != MotionEvent.ACTION_DOWN && currentBrush.getBrushShape() == BrushShape.LINE;
    }


    private void handleLineDrawing(MotionEvent motionEvent, float x, float y){
        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                currentBrush.onTouchDown(x,y, paint);
                break;
            case MotionEvent.ACTION_MOVE :
                setPreviewLayerEnabled(true);
                currentBrush.onTouchMove(x,y, shadowPaint);
                currentBrush.onTouchMove(x,y, paint);
                break;
            case MotionEvent.ACTION_UP :
                setPreviewLayerEnabled(false);
                currentBrush.onTouchUp(x,y,shadowPaint);
                currentBrush.onTouchUp(x,y, paint);
        }
        invalidate();
    }


    private void setPreviewLayerEnabled(boolean enabled){
        if(enabled){
            displayPreviewLayer = true;
            previewBitmap = Bitmap.createBitmap(bitmap);
            canvas.setBitmap(previewBitmap);
            return;
        }
        displayPreviewLayer = false;
        canvas.setBitmap(bitmap);
    }


    private void handleDrawing(float x, float y, int action){
        wasCanvasModifiedSinceLastSaveOrReset = true;
        if(isCanvasLocked){
            return;
        }
        angleHelper.updateAngle();
        switch(action) {
            case MotionEvent.ACTION_DOWN :
                drawToCanvas(x,y, paint);
                break;

            case MotionEvent.ACTION_MOVE :
                displayPreviewLayer = false;
                canvas.setBitmap(bitmap);
                drawToCanvas(x,y, paint);
                displayPreviewLayer = true;
                previewBitmap = Bitmap.createBitmap(bitmap);
                canvas.setBitmap(previewBitmap);
                drawToCanvas(x,y, previewPaint);
                break;

            case MotionEvent.ACTION_UP :
                displayPreviewLayer = false;
                canvas.setBitmap(bitmap);
                invalidate();
        }
    }


    private void drawToCanvas(float x, float y, Paint paint){
        if(isKaleidoscopeEnabled){
            drawKaleidoscope(x,y, paint);
            return;
        }
        rotateAndDraw(x,y, paint);
        invalidate();
    }


    private void drawKaleidoscope(float x, float y, Paint paint){
        canvas.save();
        canvas.translate(midCanvasX, midCanvasY);
        for(int angle = 0; angle < TOTAL_DEGREES; angle += degree_increment){
            canvas.save();
            canvas.rotate(angle);
            rotateAndDraw(x - midCanvasX, y- midCanvasY, paint);
            canvas.restore();
            invalidate();
        }
        canvas.restore();
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





