package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.CurvedLineBrush;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class CurveDrawer extends AbstractDrawer implements Drawer{

    private float xDown, yDown;
    private PointF down;
    private final CurvedLineBrush curvedLineBrush;

    public CurveDrawer(PaintView paintView, MainViewModel viewModel, CurvedLineBrush curvedLineBrush){
        super(paintView, viewModel);
        isColorChangedOnDown = false;
        this.curvedLineBrush = curvedLineBrush;
    }


    @Override
    public void down(float x, float y, Paint paint) {
        if(curvedLineBrush.isInDrawLineMode()) {
            updateColorGradientAndAngle(x, y);
            paintHelperManager.getKaleidoscopeHelper().setCenter(x, y);
            down = new PointF();
            down.x = x;
            down.y = y;
        }
        brush.onTouchDown(new Point((int)x, (int)y), canvas,paint);
        xDown = x;
        yDown = y;
    }


    @Override
    public void move(float x, float y, Paint paint) {
        paintView.enablePreviewLayer();
        if(curvedLineBrush.isInDrawCurveMode()){
            PointF up = new PointF();
            up.x = x;
            up.y = y;

            paintHelperManager.getGradientHelper().assignGradientForDragShape(down, up, getMidPoint(down, up), false);
        }
        brush.onTouchMove(x,y, paint);
        paintView.invalidate();
    }


    private PointF getMidPoint(PointF down, PointF up){
        PointF mid = new PointF();
        mid.x = down.x + ((up.x - curvedLineBrush.getLineMidpointX()) / 2);
        mid.y = down.y + ((up.y - curvedLineBrush.getLineMidpointY()) / 2);
        return mid;
    }


    @Override
    public void up(float x, float y, Paint paint) {
        if(curvedLineBrush.isInDrawCurveMode()){
            paintView.disablePreviewLayer();
        }
        if(kaleidoscopeHelper.isEnabled() && curvedLineBrush.isInDrawCurveMode()){
            kaleidoscopeDrawer.drawKaleidoscope(x, y, paint);
        }
        else{
            drawDragLine(x,y, paint);
        }
        if(curvedLineBrush.isInDrawCurveMode()) {
            paintView.pushHistory();
            paintView.invalidate();
            curvedLineBrush.setStateTo(CurvedLineBrush.State.DRAW_LINE);
            return;
        }
        curvedLineBrush.setStateTo(CurvedLineBrush.State.DRAW_CURVE);
        assignLineMidPoint(x,y);
    }


    private void assignLineMidPoint(float lineUpX, float lineUpY){
        lineMidPoint = new PointF();
        lineMidPoint.x = (down.x + lineUpX) /2;
        lineMidPoint.y = (down.y + lineUpY) /2;
    }

    PointF lineMidPoint;

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

