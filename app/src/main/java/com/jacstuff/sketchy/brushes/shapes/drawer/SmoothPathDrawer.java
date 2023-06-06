package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class SmoothPathDrawer extends BasicDrawer{

    private final KaleidoscopePathDrawer kaleidoscopePathDrawer;
    private Paint flickerGuardPaint;


    public SmoothPathDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
        kaleidoscopePathDrawer = new KaleidoscopePathDrawer(paintView, viewModel, kaleidoscopeHelper);
        setupFlickerGuardPaint();
    }


    @Override
    public void initExtra(){
        kaleidoscopePathDrawer.setShadowHelper(paintHelperManager.getShadowHelper());
    }


    private void setupFlickerGuardPaint(){
        flickerGuardPaint = new Paint(paintView.getPreviewPaint());
        flickerGuardPaint.setStyle(Paint.Style.FILL);
        kaleidoscopePathDrawer.setFlickerGuardPaint(flickerGuardPaint);
    }


    @Override
    public void down(float x1, float y1, Paint paint) {
        updateColorGradientAndAngle(x1, y1);
        placementHelper.registerTouchDown(x1, y1);
        PointF adjustedPoint = placementHelper.calculatePoint(x1,y1);
        float x = adjustedPoint.x;
        float y = adjustedPoint.y;
        updateColorGradientAndAngle(x,y);
        Point pathPoint = new Point((int)x, (int)y);
        paintHelperManager.getKaleidoscopeHelper().setCenter(x,y);
        drawToCanvasOnDown(pathPoint);
    }


    @Override
    public void move(float x1, float y1, Paint paint) {
        PointF adjustedPoint = placementHelper.calculatePoint(x1,y1);
        float x = adjustedPoint.x;
        float y = adjustedPoint.y;
        Point pathPoint = new Point((int)x, (int)y);
        paintView.enablePreviewLayer();
        brush.onTouchMove(pathPoint, canvas, paint);
        paintView.invalidate();
        drawFlickerGuard(x,y);
    }


    @Override
    public void up(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        paintHelperManager.getColorHelper().resetCurrentIndex();
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(x, y, paint);
        }
        else{
            Point zeroPoint = new Point(0,0);
            if(paintHelperManager.getShadowHelper().isShadowEnabled()){
                brush.onTouchUp(zeroPoint, canvas, paintView.getShadowPaint());
            }
            brush.onTouchUp(zeroPoint, canvas, paint);
        }
        paintView.pushHistory();
        paintView.invalidate();
    }


    private void drawFlickerGuard(float x, float y){
        if(kaleidoscopeHelper.isEnabled()){
            return;
        }
        canvas.drawCircle(x,y, paint.getStrokeWidth()/2, flickerGuardPaint);
    }


    void drawToCanvasOnDown(Point p){
        drawShadow(p);
        brush.onTouchDown(p, canvas, shadowPaint);
    }


    @Override
    public void drawKaleidoscopeSegment(float x, float y, Paint paint){
        Point kaleidoscopeCenter = new Point(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchUp(kaleidoscopeCenter, canvas, paintView.getShadowPaint());
        }
        brush.onTouchUp(kaleidoscopeCenter, canvas, paint);
    }


    private void drawShadow(Point p){
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchDown(p, canvas, shadowPaint);
        }
    }


}
