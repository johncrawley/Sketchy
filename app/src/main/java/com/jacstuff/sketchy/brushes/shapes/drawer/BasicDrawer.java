package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.paintview.KaleidoscopeDrawer;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;


public class BasicDrawer implements Drawer {


    private final PaintHelperManager paintHelperManager;
    private final KaleidoscopeHelper kaleidoscopeHelper;
    private final KaleidoscopeDrawer kaleidoscopeDrawer;
    private final Canvas canvas;
    private final Brush brush;
    private final PaintView paintView;


    public BasicDrawer(Brush brush, PaintView paintView, MainViewModel viewModel){
        this.brush = brush;
        this.paintView = paintView;
        this.canvas =  paintView.getCanvas();
        this.paintHelperManager = paintView.getPaintHelperManager();
        kaleidoscopeHelper = paintHelperManager.getKaleidoscopeHelper();
        kaleidoscopeDrawer = new KaleidoscopeDrawer(paintView, viewModel, kaleidoscopeHelper);
    }


    public void init(){
        kaleidoscopeDrawer.initParentDrawer(this);
    }


    @Override
    public void down(float x, float y, Paint paint) {
        paintHelperManager.getKaleidoscopeHelper().setCenter(x,y);
        drawToCanvas(x,y, paint);
    }


    @Override
    public void move(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        drawToCanvas(x,y, paint);
        paintView.enablePreviewLayer();
        if(!kaleidoscopeHelper.isInfinityModeEnabled()) {
            drawToCanvas(x, y, paintView.getPreviewPaint());
        }
    }


    @Override
    public void up(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        paintView.invalidate();
        paintView.pushHistory();
    }


    private void drawToCanvas(float x, float y, Paint paint){
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(x,y, paint);
        }
        else{
            rotateAndDraw(x,y, paint);
        }
        paintView.invalidate();
    }


    public void rotateAndDraw(float x, float y, Paint paint){
        canvas.save();
        canvas.translate(x, y);
        canvas.rotate(paintHelperManager.getAngleHelper().getAngle());
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchDown(x,y, paintView.getShadowPaint());
        }
        brush.onTouchDown(x, y, paint);
        canvas.restore();
    }


    @Override
    public void drawKaleidoscopeSegment(float x, float y, float angle, Paint paint){
        rotateAndDraw(x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY(), paint);
    }


}
