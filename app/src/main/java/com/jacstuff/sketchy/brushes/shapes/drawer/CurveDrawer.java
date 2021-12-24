package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;


// for drawing curves
// onMove only draws preview line
// doesn't draw proper line until onUp
// doesn't draw shadow until onUp
// indicates that colors shouldn't change onDown
public class CurveDrawer extends AbstractDrawer implements Drawer{



    private enum State{ DRAW_LINE, DRAW_CURVE}
    private State state;

    public CurveDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
        isColorChangedOnDown = false;
        state = State.DRAW_LINE;
    }



    @Override
    public void down(float x, float y, Paint paint) {
        if(state == State.DRAW_LINE) {
            updateColorGradientAndAngle(x, y);
            paintHelperManager.getKaleidoscopeHelper().setCenter(x, y);
        }
        brush.onTouchDown(new Point((int)x, (int)y), canvas,paint);
        //paintView.invalidate();
    }


    @Override
    public void move(float x, float y, Paint paint) {
        paintView.enablePreviewLayer();
        brush.onTouchMove(x,y, paint);
        paintView.invalidate();
    }


    @Override
    public void up(float x, float y, Paint paint) {
        if(state == State.DRAW_CURVE){
            paintView.disablePreviewLayer();
        }
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(x, y, paint);
        }
        else{
            drawDragLine(x,y, paint);
        }
        if(state == State.DRAW_CURVE) {
            paintView.pushHistory();
            paintView.invalidate();
            state = State.DRAW_LINE;
            return;
        }
        state = State.DRAW_CURVE;
    }


    void drawDragLine(float x, float y, Paint paint){
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchUp(x, y, 0,0, paintView.getShadowPaint());
        }
        brush.onTouchUp(x, y, 0, 0, paint);
    }


    @Override
    public void drawKaleidoscopeSegment(float x, float y, Paint paint){
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brushTouchUp(x,y, paintView.getShadowPaint());
        }
        brushTouchUp(x,y, paint);
    }


    private void brushTouchUp(float x, float y, Paint paint){
        brush.onTouchUp(x,y, kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY(), paint);
    }

}

