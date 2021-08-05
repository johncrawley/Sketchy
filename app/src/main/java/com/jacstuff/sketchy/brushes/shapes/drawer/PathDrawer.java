package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.paintview.KaleidoscopePathDrawer;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import androidx.core.util.Consumer;

public class PathDrawer extends BasicDrawer{

    private final KaleidoscopePathDrawer kaleidoscopePathDrawer;
    private Paint flickerGuardPaint;

    public PathDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
        kaleidoscopePathDrawer = new KaleidoscopePathDrawer(paintView, viewModel, kaleidoscopeHelper);
        setupFlickerGuardPaint();
    }


    private void setupFlickerGuardPaint(){
        flickerGuardPaint = new Paint(paintView.getPreviewPaint());
        flickerGuardPaint.setStyle(Paint.Style.FILL);
        kaleidoscopePathDrawer.setFlickerGuardPaint(flickerGuardPaint);
    }


    @Override
    public void down(float x, float y, Paint paint) {
        Point point = new Point((int)x, (int)y);
        paintHelperManager.getKaleidoscopeHelper().setCenter(x,y);
        drawToCanvas(point, (c) -> draw(point, (paintArg) -> brush.onTouchDown(point, c, paintArg)));
    }


    @Override
    public void move(float x, float y, Paint paint) {
        Point point = new Point((int)x, (int)y);
        paintView.disablePreviewLayer();
        drawToCanvas(point, (c) -> draw(point, (paintArg) -> brush.onTouchMove(point, c, paintArg)));
        drawFlickerGuard(x,y);
        drawPreviewWhenInfinityModeOff(x, y);
    }


    private void drawFlickerGuard(float x, float y){
        if(kaleidoscopeHelper.isEnabled()){
            return;
        }
        paintView.enablePreviewLayer();
        canvas.drawCircle(x,y, paint.getStrokeWidth()/2, flickerGuardPaint);
    }




    @Override
    public void up(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        kaleidoscopePathDrawer.resetPreviousPoint();
        brush.onTouchUp(x, y, paint);
        paintView.invalidate();
        paintView.pushHistory();
    }


    void drawToCanvas(Point p, Consumer<Canvas> drawMethod){
        if(kaleidoscopeHelper.isEnabled()){
            Point pk = new Point(p.x - kaleidoscopeHelper.getCenterX(), p.y - kaleidoscopeHelper.getCenterY());
            kaleidoscopePathDrawer.drawKaleidoscope(pk, p);
        }
        else{
            drawMethod.accept(canvas);
        }
        paintView.invalidate();
    }


    public void draw(Point point, Consumer<Paint> drawTouch){
        canvas.save();
        canvas.translate(point.x, point.y);
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            drawTouch.accept(shadowPaint);
        }
        drawTouch.accept(paint);
        canvas.restore();
    }


    private void drawPreviewWhenInfinityModeOff(float x, float y){
        if(!kaleidoscopeHelper.isInfinityModeEnabled()) {
            drawToCanvas(x, y, paintView.getPreviewPaint());
        }
    }

}
