package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import androidx.core.util.Consumer;

public class PathDrawer extends BasicDrawer{

    private final Canvas kaleidoscopeCanvas;

    public PathDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
        kaleidoscopeCanvas = paintView.getKaleidoscopeSegmentCanvas();
    }


    @Override
    public void down(float x, float y, Paint paint) {
        paintHelperManager.getKaleidoscopeHelper().setCenter(x,y);
        drawToCanvas((int)x, (int)y, () -> draw(paint, (p) -> brush.onTouchDown(new Point((int)x, (int)y), canvas, p)));
        //drawToCanvas(x,y, paint);
    }


    @Override
    public void move(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        drawToCanvas((int)x, (int)y, () -> draw(paint, (p) -> brush.onTouchMove(new Point((int)x, (int)y), canvas, p)));
       // drawToCanvasMove(x,y, paint);
        paintView.enablePreviewLayer();
        drawPreviewWhenInfinityModeOff(x, y);
    }


    @Override
    public void up(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        brush.onTouchUp(x, y, paint);
        paintView.invalidate();
        paintView.pushHistory();
    }


    void drawToCanvas(int x, int y, Runnable runnable){
        if(kaleidoscopeHelper.isEnabled()){
            Point p = new Point(x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY());
            kaleidoscopeDrawer.drawKaleidoscope(p , brush.getBrushSize(), runnable);
        }
        else{
            runnable.run();
        }
        paintView.invalidate();
    }


    public void draw(Paint defaultPaint, Consumer<Paint> drawTouch){
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            drawTouch.accept(shadowPaint);
        }
        drawTouch.accept(defaultPaint);
    }


    private void drawPreviewWhenInfinityModeOff(float x, float y){
        if(!kaleidoscopeHelper.isInfinityModeEnabled()) {
            drawToCanvas(x, y, paintView.getPreviewPaint());
        }
    }

}
