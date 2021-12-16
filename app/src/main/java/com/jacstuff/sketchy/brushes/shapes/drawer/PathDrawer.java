package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.PathBrush;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import androidx.core.util.Consumer;


public class PathDrawer extends BasicDrawer{

    private final KaleidoscopePathDrawer kaleidoscopePathDrawer;
    private Paint flickerGuardPaint;
    private PathBrush shadowBrush;


    public PathDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
        kaleidoscopePathDrawer = new KaleidoscopePathDrawer(paintView, viewModel, kaleidoscopeHelper);
        setupFlickerGuardPaint();
    }


    @Override
    public void initExtra(){
        shadowBrush = paintView.getBrushFactory().getShadowPathBrush();
        kaleidoscopePathDrawer.setShadowHelper(paintHelperManager.getShadowHelper());
    }


    private void setupFlickerGuardPaint(){
        flickerGuardPaint = new Paint(paintView.getPreviewPaint());
        flickerGuardPaint.setStyle(Paint.Style.FILL);
        kaleidoscopePathDrawer.setFlickerGuardPaint(flickerGuardPaint);
    }


    @Override
    public void down(float x1, float y1, Paint paint) {
        placementHelper.registerTouchDown(x1, y1);
        PointF adjustedPoint = placementHelper.calculatePoint(x1,y1);
        float x = adjustedPoint.x;
        float y = adjustedPoint.y;
        updateColorGradientAndAngle(x,y);
        Point pathPoint = new Point((int)x, (int)y);
        paintHelperManager.getKaleidoscopeHelper().setCenter(x,y);
        drawToCanvas(pathPoint, (c) -> draw(pathPoint, (paintArg, brushArg) -> brushArg.onTouchDown(pathPoint, c, paintArg)));
    }


    @Override
    public void move(float x1, float y1, Paint paint) {
        PointF adjustedPoint = placementHelper.calculatePoint(x1,y1);
        float x = adjustedPoint.x;
        float y = adjustedPoint.y;
        updateColorGradientAndAngle(x,y);
        Point pathPoint = new Point((int)x, (int)y);
        paintView.disablePreviewLayer();
        drawToCanvas(pathPoint, (c) -> draw(pathPoint, (paintArg, brushArg) -> brushArg.onTouchMove(pathPoint, c, paintArg)));
        drawFlickerGuard(x,y);
    }


    @Override
    public void up(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        paintHelperManager.getColorHelper().resetCurrentIndex();
        kaleidoscopePathDrawer.resetPreviousPoint();
        brush.onTouchUp(x, y, paint);
        paintView.invalidate();
        paintView.pushHistory();
    }


    private void drawFlickerGuard(float x, float y){
        if(kaleidoscopeHelper.isEnabled()){
            return;
        }
        paintView.enablePreviewLayer();
        canvas.drawCircle(x,y, paint.getStrokeWidth()/2, flickerGuardPaint);
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


    public void draw(Point point, BiConsumerX<Paint, Brush> drawTouch){
        canvas.save();
        canvas.translate(point.x, point.y);
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            drawTouch.accept(shadowPaint, shadowBrush);
        }
        drawTouch.accept(paint, brush);
        canvas.restore();
    }

}
