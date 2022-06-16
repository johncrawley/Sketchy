package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.sketchy.controls.settings.menu.ConnectedLineIconModifier;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.helpers.SensitivityHelper;
import com.jacstuff.sketchy.paintview.history.DrawHistory;
import com.jacstuff.sketchy.paintview.history.HistoryItem;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.ui.SettingsPopup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import static com.jacstuff.sketchy.paintview.helpers.PaintFactory.createPaint;


public class PaintView extends View {

    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private Paint paint, shadowPaint, previewPaint;
    private Paint drawPaint, blankPaint;
    private PaintGroup paintGroup;
    private int brushSize;
    private Bitmap bitmap, previewBitmap;
    private Canvas canvas;
    private Brush currentBrush;
    private BrushFactory brushFactory;
    private boolean isCanvasLocked;
    private PaintHelperManager paintHelperManager;
    private boolean isPreviewLayerToBeDrawn;
    private boolean ignoreMoveAndUpActions = false;
    private DrawHistory drawHistory;
    private SettingsPopup settingsPopup;
    private final Context context;
    private BitmapLoader bitmapLoader;
    private SensitivityHelper sensitivityHelper;
    private float x, y;
    boolean isTouchDownRegistered = false;


    public PaintView(Context context) {
        this(context, null);
    }


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaints();
    }


    public void setPaintHelperManager(PaintHelperManager paintHelperManager){
        this.paintHelperManager = paintHelperManager;
        this.paintHelperManager.init(paint, shadowPaint, previewPaint, paintGroup);
        sensitivityHelper = paintHelperManager.getSensitivityHelper();
    }


    public void init(SettingsPopup settingsPopup, BrushFactory brushFactory, MainViewModel viewModel, DrawHistory drawHistory) {
        this.drawHistory = drawHistory;
        this.settingsPopup = settingsPopup;
        this.brushFactory = brushFactory;
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        bitmapLoader = new BitmapLoader(this, canvas, drawPaint);
        initBrushes();

        if(drawHistory.isEmpty()){
            drawPlainBackgroundAndSaveToHistory();
        }
        paintHelperManager.getKaleidoscopeHelper().setCanvas(canvas);
        paintHelperManager.initDimensions(getWidth(), getHeight());
        invalidate();
    }


    @Override
    protected void onDraw(Canvas viewCanvas) {
        viewCanvas.save();
        viewCanvas.drawBitmap(isPreviewLayerToBeDrawn ? previewBitmap : bitmap, 0, 0, drawPaint);
        viewCanvas.restore();
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        if(isPopupBeingDismissed(event) || isCanvasLocked){
            return true;
        }
        try {
            drawWithBrush(event);
        }
        //catch(IllegalArgumentException e){
        catch (Exception e){
            e.printStackTrace();
            //do nothing, sometimes there's an illegalArgException related to drawing gradients
            // immediately after rotating screen
        }
        return true;
    }


    public PaintGroup getPaintGroup(){
        return this.paintGroup;
    }

    public DrawHistory getDrawHistory(){
        return drawHistory;
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

    public Paint getPaint(){
        return paint;
    }

    public PaintHelperManager getPaintHelperManager(){
        return paintHelperManager;
    }

    public Paint getPreviewPaint(){
        return previewPaint;
    }

    public void pushHistory(){
        drawHistory.push(bitmap);
    }

    public void assignMostRecentBitmap(){
        loadHistoryItem(false);
    }

    public BrushFactory getBrushFactory(){
        return brushFactory;
    }


    public void setBrushShape(BrushShape brushShape){
        currentBrush.onDeallocate();
        currentBrush = brushFactory.getReinitializedBrushFor(brushShape);
        currentBrush.setBrushSize(brushSize);
        paintHelperManager.getGradientHelper().updateBrush(currentBrush);
    }


    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
        if(currentBrush != null) {
            currentBrush.setBrushSize(brushSize);
        }
        paintHelperManager.getShadowHelper().updateOffsetFactor(brushSize/2);
    }


    public void setLineWidth(int lineWidth){
        paintGroup.setStrokeWidth(lineWidth);
        if(currentBrush != null) {
            currentBrush.notifyStrokeWidthChanged();
        }
    }


    public void resetCanvas(){
        disablePreviewLayer();
        isCanvasLocked = true;
        invalidate();
        currentBrush.reset();
        drawPlainBackgroundAndSaveToHistory();
        isCanvasLocked = false;
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


    public void onTouchUp(){
        if(!isTouchDownRegistered){
            return;
        }
        isTouchDownRegistered = false;
        currentBrush.touchUp(x, y, paint);
    }


    public void recalculateBrush(){
        if(currentBrush == null){
            return;
        }
        currentBrush.recalculateDimensions();
    }


    public void undo(){
        loadHistoryItem(true);
    }


    public Brush getCurrentBrush(){
        return currentBrush;
    }


    public void loadBitmap(Bitmap bm){
        bitmapLoader.drawBitmapToScale(bm);
        drawHistory.push(bitmap);
    }


    public void drawBitmap(Bitmap loadedBitmap, float offsetX, float offsetY){
        Paint loadedBitmapPaint = new Paint();
        canvas.drawBitmap(loadedBitmap, offsetX, offsetY, loadedBitmapPaint);
        drawHistory.push(bitmap);
    }


    int getScreenOrientation(){
        return context.getResources().getConfiguration().orientation;
    }


    private void initPaints(){
        paint = createPaint(Color.WHITE);
        previewPaint = createPaint(Color.DKGRAY);
        shadowPaint = createPaint(Color.BLACK);
        drawPaint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paintGroup = new PaintGroup(paint, previewPaint, shadowPaint);
        blankPaint = new Paint();
        blankPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        blankPaint.setColor(DEFAULT_BG_COLOR);
    }


    private void initBrushes(){
        brushFactory.init(this, brushSize, getMaxDimension());
        currentBrush = brushFactory.getReinitializedBrushFor(BrushShape.CIRCLE);
    }


    private int getMaxDimension(){
        return Math.max(getWidth(), getHeight());
    }


    private void drawPlainBackgroundAndSaveToHistory(){
        canvas.drawRect(0,0, getWidth(), getHeight(), blankPaint);
        drawHistory.push(bitmap);
        invalidate();
    }


    private void drawWithBrush(MotionEvent event){
        x = event.getX();
        y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                onTouchDown();
                break;
            case MotionEvent.ACTION_MOVE :
                onTouchMove();
                break;
            case MotionEvent.ACTION_UP :
                onTouchUp();
                break;
        }
    }


    private void onTouchDown(){
        isTouchDownRegistered = true;
        currentBrush.touchDown(x, y, paint);
    }


    private void onTouchMove(){
        if(!isTouchDownRegistered){
            return;
        }
        if(!sensitivityHelper.shouldDraw(currentBrush)){
            return;
        }
        currentBrush.touchMove(x, y, paint);
    }


    private void loadHistoryItem(boolean isCurrentDiscarded){
        HistoryItem historyItem = isCurrentDiscarded ?  drawHistory.getPrevious() : drawHistory.getCurrent();
        Bitmap historyBitmap = bitmapLoader.getCorrectlyOrientatedBitmapFrom(historyItem);
        if(historyBitmap == null){
            return;
        }
        bitmapLoader.drawBitmapToScale(historyBitmap);
        disablePreviewLayer();
        invalidate();
    }


    private boolean isPopupBeingDismissed(MotionEvent event){
        if(settingsPopup.isVisible() && event.getAction() == MotionEvent.ACTION_DOWN){
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

}