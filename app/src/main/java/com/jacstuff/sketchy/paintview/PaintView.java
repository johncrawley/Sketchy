package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.sketchy.brushes.Easel;
import com.jacstuff.sketchy.brushes.shapes.drawer.Drawer;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.helpers.SensitivityHelper;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.paintview.history.HistoryMemoryHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;


public class PaintView extends View {

    private final Paint blankPaint, canvasBitmapPaint;
    private final PaintGroup paintGroup;
    private int brushSize;
    private Bitmap bitmap, previewBitmap;
    private Canvas canvas;
    private Brush currentBrush;
    private BrushFactory brushFactory;
    private boolean isCanvasLocked;
    private PaintHelperManager paintHelperManager;
    private boolean isPreviewLayerToBeDrawn;
   // private boolean ignoreMoveAndUpActions = false;
    //private SettingsPopup settingsPopup;
    private final Context context;
    private BitmapLoader bitmapLoader;
    private SensitivityHelper sensitivityHelper;
    private float x, y;
    boolean isTouchDownRegistered = false;
    private MainViewModel viewModel;
    private Easel easel;
    private DrawerFactory drawerFactory;
    private Drawer drawer;

    public PaintView(Context context) {

        this(context, null);
    }


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paintGroup = new PaintGroup();
        blankPaint = paintGroup.getBlankPaint();
        canvasBitmapPaint = paintGroup.getCanvasBitmapPaint();
    }


    public boolean isInPortrait(){
        return getMeasuredHeight() > getMeasuredWidth();
    }


    public void init(BrushFactory brushFactory, MainViewModel viewModel, PaintHelperManager paintHelperManager) {
        log("entered init()");
        this.viewModel = viewModel;
       // this.settingsPopup = settingsPopup;
        this.brushFactory = brushFactory;
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        this.paintHelperManager = paintHelperManager;
        canvas = new Canvas(bitmap);
        paintHelperManager.init(this, context);
        sensitivityHelper = paintHelperManager.getSensitivityHelper();
        bitmapLoader = new BitmapLoader(this, canvas, canvasBitmapPaint);
        initBrushes();

        if(viewModel.drawHistory.isEmpty()){
            drawPlainBackgroundAndSaveToHistory();
        }
        paintHelperManager.getKaleidoscopeHelper().setCanvas(canvas);
        paintHelperManager.initDimensions(getWidth(), getHeight());
        log("init() about to invalidate()");
        initEasel();
        drawerFactory = new DrawerFactory(this);
        drawerFactory.init();

        var drawerType = viewModel.currentBrush.getDrawerType();
        drawer = drawerFactory.get(drawerType);
        drawer.setBrushShape(viewModel.currentBrush);
        //currentBrush.init(drawerFactory);


        invalidate();
    }

    private void initEasel(){
        easel = new Easel();
        easel.setCanvas(canvas);
    }


    @Override
    protected void onDraw(Canvas viewCanvas) {
        viewCanvas.save();
        if(previewBitmap != null && bitmap != null){
            viewCanvas.drawBitmap(isPreviewLayerToBeDrawn ? previewBitmap : bitmap, 0, 0, canvasBitmapPaint);
        }
        viewCanvas.restore();
    }


    private void log(String msg){
        System.out.println("^^^ PaintView: " + msg);
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        if( isCanvasLocked){
            return true;
        }
        drawWithBrush(event);
        /*
        try {
            drawWithBrush(event);
        }
        catch (RuntimeException e){
            printError(e.getMessage());
        }*/
        return true;
    }


    private void printError(String msg){
        System.out.println("^^^ PaintView exception caught: " + msg);
    }


    public PaintGroup getPaintGroup(){
        return this.paintGroup;
    }


    public Bitmap getBitmap(){
        return bitmap;
    }


    public Canvas getCanvas(){
        return canvas;
    }


    public Paint getShadowPaint(){
        return paintGroup.getShadowPaint();
    }


    public Paint getPaint(){
        return paintGroup.getDrawPaint();
    }


    public PaintHelperManager getPaintHelperManager(){
        return paintHelperManager;
    }


    public Paint getPreviewPaint(){
        return paintGroup.getPreviewPaint();
    }


    public void pushHistory(){
        boolean isLowOnMemory = HistoryMemoryHelper.isLowMemoryFor(bitmap, context, viewModel.drawHistory.size());
        viewModel.drawHistory.push(bitmap, getScreenOrientation(), isLowOnMemory);
    }


    public void assignMostRecentBitmap(){
        loadHistoryItem(false);
    }


    public BrushFactory getBrushFactory(){
        return brushFactory;
    }


    public void setBrushShape(BrushShape brushShape){
        currentBrush.onDeallocate();
        currentBrush = brushFactory.getBrushFor(brushShape);
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


    public void recalculateBrush(){
        if(currentBrush == null){
            return;
        }
        currentBrush.recalculateDimensions();
    }


    public void undo(){
        if(currentBrush.isOnFirstStep()){
            loadHistoryItem(true);
            return;
        }
        currentBrush.reset();
        disablePreviewLayer();
        invalidate();
    }


    public void redo(){
        if(currentBrush.isOnFirstStep()){
            loadNextHistoryItem();
            return;
        }
        currentBrush.reset();
        disablePreviewLayer();
        invalidate();
    }


    public Brush getCurrentBrush(){
        return currentBrush;
    }


    public void loadBitmap(Bitmap bm){
        bitmapLoader.drawBitmapToScale(bm);
        pushHistory();
    }


    public void drawBitmap(Bitmap loadedBitmap, float offsetX, float offsetY){
        Paint loadedBitmapPaint = new Paint();
        canvas.drawBitmap(loadedBitmap, offsetX, offsetY, loadedBitmapPaint);
        pushHistory();
    }


    int getScreenOrientation(){
        return context.getResources().getConfiguration().orientation;
    }


    private void initBrushes(){
        brushFactory.init(this, brushSize, getMaxDimension());
        currentBrush = brushFactory.getBrushFor(BrushShape.CIRCLE);
    }


    private int getMaxDimension(){
        return Math.max(getWidth(), getHeight());
    }


    private void drawPlainBackgroundAndSaveToHistory(){
        canvas.drawRect(0,0, getWidth(), getHeight(), blankPaint);
        pushHistory();
        invalidate();
    }


    private void drawWithBrush(MotionEvent event){
        x = event.getX();
        y = event.getY();
        var point = new PointF(x,y);

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                onTouchDown(point);
                break;
            case MotionEvent.ACTION_MOVE :
                onTouchMove(point);
                break;
            case MotionEvent.ACTION_UP :
                onTouchUp(point);
                break;
        }
    }


    private void onTouchDown(PointF point){
        isTouchDownRegistered = true;
        log("entered onTouchDown(PointF)");
        viewModel.currentBrush.onTouchDown(point, easel);
    }


    private void onTouchDown(){
        isTouchDownRegistered = true;
        currentBrush.touchDown(x, y, paintGroup.getDrawPaint());

        // paintGroup.initDrawPaint();
    }


    private void onTouchMove(){
        if(!isTouchDownRegistered){
            return;
        }
        if(!sensitivityHelper.shouldDraw(currentBrush)){
            return;
        }
        currentBrush.setBrushSize(20);
        var drawPaint = paintGroup.getDrawPaint();
        // drawPaint = new Paint();
        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setStrokeWidth(20);
        // drawPaint.setColor(Color.BLUE);
        // canvas.drawCircle(x,y, 30, drawPaint);
        // invalidate();
        currentBrush.touchMove(x, y, drawPaint);
        log("drawPaint color: " + drawPaint.getColor() + " white color: " + Color.WHITE);

    }


    private void onTouchMove(PointF p){
        if(!isTouchDownRegistered){
            return;
        }
        if(!sensitivityHelper.shouldDraw(currentBrush)){
            return;
        }
        viewModel.currentBrush.onTouchMove(p, easel);
    }


    public void onTouchUp(PointF p){
        if(!isTouchDownRegistered){
            return;
        }
        isTouchDownRegistered = false;
        viewModel.currentBrush.onTouchUp(p, easel);
    }

    public void onTouchUp(){
        if(!isTouchDownRegistered){
            return;
        }
        isTouchDownRegistered = false;
       // currentBrush.touchUp(x, y, paintGroup.getDrawPaint());
    }


    private void loadHistoryItem(boolean isCurrentDiscarded){
        var historyItem = isCurrentDiscarded ?  viewModel.drawHistory.assignPrevious() : viewModel.drawHistory.getCurrent();
        var historyBitmap = bitmapLoader.getCorrectlyOrientatedBitmapFrom(historyItem);
        if(historyBitmap != null){
            bitmapLoader.drawBitmapToScale(historyBitmap);
            disablePreviewLayer();
            invalidate();
        }
    }


    private void loadNextHistoryItem(){
        var historyItem = viewModel.drawHistory.getNext();
        var historyBitmap = bitmapLoader.getCorrectlyOrientatedBitmapFrom(historyItem);
        if(historyBitmap != null){
            bitmapLoader.drawBitmapToScale(historyBitmap);
            disablePreviewLayer();
            invalidate();
        }
    }


    private int getCurrentScreenOrientation(){
        return context.getResources().getConfiguration().orientation;
    }


}