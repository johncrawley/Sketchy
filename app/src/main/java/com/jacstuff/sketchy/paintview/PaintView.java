package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.history.BitmapHistory;
import com.jacstuff.sketchy.paintview.history.HistoryItem;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.ui.SettingsPopup;


public class PaintView extends View {

    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private final Paint paint, shadowPaint, previewPaint;
    private int brushSize;
    private Bitmap bitmap;
    private Canvas canvas;
    private ColorSelector colorSelector;
    private BrushStyle currentBrushStyle = BrushStyle.FILL;
    private Brush currentBrush;
    private BrushFactory brushFactory;
    private boolean isCanvasLocked;
    private MainViewModel viewModel;
    private PaintHelperManager paintHelperManager;
    private KaleidoscopeHelper kaleidoscopeHelper;
    private boolean isPreviewLayerToBeDrawn;
    private Bitmap previewBitmap;
    private final BitmapHistory bitmapHistory;
    private final Paint drawPaint = new Paint();
    private final PaintGroup paintGroup;
    private SettingsPopup settingsPopup;
    private boolean ignoreMoveAndUpActions = false;
    private final Context context;
    private BitmapLoader bitmapLoader;
    private InfinityModeColorBlender fractalColorBlender;


    public PaintView(Context context) {
        this(context, null);
    }


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = createPaint(Color.WHITE);
        previewPaint =  createPaint(Color.DKGRAY);
        shadowPaint = createPaint(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.MITER);
        paintGroup = new PaintGroup(paint, previewPaint, shadowPaint);
        bitmapHistory = new BitmapHistory(context);
    }


    public void setPaintHelperManager(PaintHelperManager paintHelperManager){
        this.paintHelperManager = paintHelperManager;
        this.paintHelperManager.init(paint, shadowPaint, paintGroup);
    }


    public void init(MainViewModel viewModel, SettingsPopup settingsPopup) {
        this.viewModel = viewModel;
        this.settingsPopup = settingsPopup;

        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        bitmapLoader = new BitmapLoader(this, canvas, drawPaint);
        initBrushes();

        if(bitmapHistory.isEmpty()){
            drawPlainBackgroundAndSaveToHistory();
        }
        initKaleidoscope();
        paint.setColor(viewModel.color);
        fractalColorBlender = new InfinityModeColorBlender(viewModel, colorSelector, paint);
        invalidate();
    }


    public PaintGroup getPaintGroup(){
        return this.paintGroup;
    }


    public BitmapHistory getBitmapHistory(){
        return bitmapHistory;
    }


    public void assignMostRecentBitmap(){
        loadHistoryItem(false);
    }


    public void setBrushStyle(BrushStyle brushStyle){
        currentBrushStyle = brushStyle;
        currentBrush.setStyle(brushStyle);
    }


    public Bitmap getBitmap(){
        return bitmap;
    }


    public Canvas getCanvas(){
        return canvas;
    }

    public Paint getShadowPaint(){
        return shadowPaint;
    }

    public PaintHelperManager getPaintHelperManager(){
        return paintHelperManager;
    }


    public void setBrushShape(BrushShape brushShape){
        currentBrush = brushFactory.getResettedBrushFor(brushShape, currentBrushStyle);
        currentBrush.setBrushSize(brushSize);
    }


    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
        currentBrush.setBrushSize(brushSize);
        paintHelperManager.getGradientHelper().updateBrushSize(brushSize);
        paintHelperManager.getShadowHelper().updateOffsetFactor(brushSize/2);
    }


    public void setLineWidth(int lineWidth){
        paintGroup.setStrokeWidth(lineWidth);
        currentBrush.notifyStrokeWidthChanged();
    }


    public void resetCanvas(){
        isCanvasLocked = true;
        drawPlainBackgroundAndSaveToHistory();
        isCanvasLocked = false;
    }


    public Paint getPreviewPaint(){
        return previewPaint;
    }

    public void pushHistory(){
        bitmapHistory.push(bitmap);
    }


    public void enablePreviewLayer(){
        isPreviewLayerToBeDrawn = true;
        previewBitmap = Bitmap.createBitmap(bitmap);
        canvas.setBitmap(previewBitmap);
    }


    public void disablePreviewLayer(){
        isPreviewLayerToBeDrawn = false;
        canvas.setBitmap(bitmap);
    }


    private Paint createPaint(int color){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setColor(color);
        return paint;
    }


    private void initBrushes(){
        brushFactory = new BrushFactory(this, paintGroup, brushSize, viewModel);
        currentBrush = brushFactory.getResettedBrushFor(BrushShape.CIRCLE, currentBrushStyle);
    }


    private void initKaleidoscope(){
        kaleidoscopeHelper = paintHelperManager.getKaleidoscopeHelper();
        kaleidoscopeHelper.setCanvas(canvas);
        kaleidoscopeHelper.setDefaultCenter(getWidth()/2, getHeight()/2);
    }


    private void drawPlainBackgroundAndSaveToHistory(){
        Paint blankPaint = new Paint();
        blankPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        blankPaint.setColor(DEFAULT_BG_COLOR);
        canvas.drawRect(0,0, getWidth(), getHeight(), blankPaint);
        bitmapHistory.push(bitmap);
        invalidate();
    }


    public void setColorSelector(ColorSelector colorSelector){
        this.colorSelector = colorSelector;
        if(fractalColorBlender != null){
            fractalColorBlender.setColorSelector(colorSelector);
        }
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

        if(isPopupBeingDismissed(event) || isCanvasLocked){
            return true;
        }
        float x = event.getX();
        float y = event.getY();

        assignColorsBlursAndGradients(x, y, event);
        try {
                handleDrawing(x, y, event);
        }catch (IllegalArgumentException e){
            //do nothing, sometimes there's an illegalArgException related to drawing gradients
            // immediately after rotating screen
        }
        return true;
    }


    private void handleDrawing(float x, float y, MotionEvent event){
        paintHelperManager.getAngleHelper().updateAngle();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                currentBrush.touchDown(x, y, paint);
                break;
            case MotionEvent.ACTION_MOVE :
                currentBrush.touchMove(x, y, paint);
                break;
            case MotionEvent.ACTION_UP :
                currentBrush.touchUp(x, y, paint);
        }
    }


    public void undo(){
        loadHistoryItem(true);
    }


    private void loadHistoryItem(boolean isCurrentDiscarded){
        HistoryItem historyItem = isCurrentDiscarded ?  bitmapHistory.getPrevious() : bitmapHistory.getCurrent();

        Bitmap historyBitmap = bitmapLoader.getCorrectlyOrientatedBitmapFrom(historyItem);
        if(historyBitmap == null){
            return;
        }
        bitmapLoader.drawBitmapToScale(historyBitmap);
        disablePreviewLayer();
        invalidate();
    }


    public void loadBitmap(Bitmap bm){
        bitmapLoader.drawBitmapToScale(bm);
        bitmapHistory.push(bitmap);
    }


    int getScreenOrientation(){
        return context.getResources().getConfiguration().orientation;
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


    private void assignColorsBlursAndGradients(float x, float y, MotionEvent event){
        if(isDragBrushWithTouchMoveOrUp(event)){
            return;
        }
        assignColors();
        paintHelperManager.getGradientHelper().assignGradient(x,y, viewModel.color, viewModel.previousColor);
    }


    private void assignColors(){
        if(kaleidoscopeHelper.isEnabled() && viewModel.isInfinityModeEnabled){
            fractalColorBlender.assignNextInfinityModeColor();
            return;
        }
        viewModel.color = colorSelector.getNextColor();
        if(viewModel.color != paint.getColor()){
            viewModel.previousColor = paint.getColor();
        }
        paint.setColor(viewModel.color);
    }


    private boolean isDragBrushWithTouchMoveOrUp(MotionEvent event){
        return event.getAction() != MotionEvent.ACTION_DOWN && !currentBrush.isColorChangedOnDown();
    }

}