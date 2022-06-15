package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

// for drawing lines
// onMove only draws preview line
// doesn't draw proper line until onUp
// doesn't draw shadow until onUp
// indicates that colors shouldn't change onDown
public class DragLineDrawer extends AbstractDrawer implements Drawer{


    public DragLineDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
        isColorChangedOnDown = false;
    }


    @Override
    public void down(float x, float y, Paint paint) {
        updateColorGradientAndAngle(x,y);
        paintHelperManager.getKaleidoscopeHelper().setCenter(x,y);
        brush.onTouchDown(new Point((int)x, (int)y), canvas,paint);
        paintView.invalidate();
    }


    @Override
    public void move(float x, float y, Paint paint) {
        paintView.enablePreviewLayer();
        brush.onTouchMove(x, y, paint);
        paintView.invalidate();

    }


    @Override
    public void up(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(x, y, paint);
        }
        else{
            drawDragLine(x,y, paint);
        }
        paintView.pushHistory();
        paintView.invalidate();
    }


    void drawDragLine(float x, float y, Paint paint){
        if(isShadowEnabled()){
            brush.onTouchUp(x, y, 0,0, paintView.getShadowPaint());
        }
        brush.onTouchUp(x, y, 0, 0, paint);
    }


    @Override
    public void drawKaleidoscopeSegment(float x, float y, Paint paint){
        if(isShadowEnabled()){
            brushTouchUp(x,y, paintView.getShadowPaint());
        }
        brushTouchUp(x,y, paint);
    }


    private void brushTouchUp(float x, float y, Paint paint){
        brush.onTouchUp(x,y, kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY(), paint);
    }


    private boolean isShadowEnabled(){
        return paintHelperManager.getShadowHelper().isShadowEnabled();
    }
}

