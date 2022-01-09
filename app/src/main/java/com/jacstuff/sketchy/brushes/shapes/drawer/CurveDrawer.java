package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.CurvedLineBrush;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class CurveDrawer extends AbstractDrawer implements Drawer{

    private PointF down;
    private final CurvedLineBrush curvedLineBrush;
    private PointF lineMidPoint;

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
    }


    @Override
    public void move(float x, float y, Paint paint) {
        paintView.enablePreviewLayer();
        assignGradientForCurveMode(x, y, false);
        brush.onTouchMove(x,y, paint);
        paintView.invalidate();
    }


    @Override
    public void up(float x, float y, Paint paint) {
        if(curvedLineBrush.isInDrawCurveMode()){
            paintView.disablePreviewLayer();
        }
        if(kaleidoscopeHelper.isEnabled() && curvedLineBrush.isInDrawCurveMode()){
            assignGradientForCurveMode(x,y, true);
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

        assignLineMidPoint(x,y);
        curvedLineBrush.setStateTo(CurvedLineBrush.State.DRAW_CURVE);
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


    private void assignGradientForCurveMode(float x, float y, boolean isUsingKaleidoscopeOffsets){
        if(!curvedLineBrush.isInDrawCurveMode()) {
            return;
        }
        PointF down = createCoordinatePoint(lineMidPoint.x, lineMidPoint.y, isUsingKaleidoscopeOffsets);
        PointF up = createCoordinatePoint(x,y, isUsingKaleidoscopeOffsets);
        paintHelperManager.getGradientHelper().assignGradientForDragShape(down, up, getMidPoint(down, up), false);
    }


    private PointF createCoordinatePoint(float x, float y, boolean isUsingKaleidoscopeOffsets){
        PointF point = new PointF();
        point.x = x - (kaleidoscopeHelper.isEnabled() && isUsingKaleidoscopeOffsets ? kaleidoscopeHelper.getCenterX() : 0);
        point.y = y - (kaleidoscopeHelper.isEnabled() && isUsingKaleidoscopeOffsets ? kaleidoscopeHelper.getCenterY() : 0);
        return point;
    }


    private void assignLineMidPoint(float lineUpX, float lineUpY){
        lineMidPoint = new PointF();
        lineMidPoint.x = (down.x + lineUpX) /2;
        lineMidPoint.y = (down.y + lineUpY) /2;
    }


    private PointF getMidPoint(PointF down, PointF up){
        PointF mid = new PointF();
        mid.x = down.x + ((up.x - curvedLineBrush.getLineMidpointX()) / 2);
        mid.y = down.y + ((up.y - curvedLineBrush.getLineMidpointY()) / 2);
        return mid;
    }


    void drawDragLine(float x, float y, Paint paint){
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchUp(x, y, 0,0, paintView.getShadowPaint());
        }
        brush.onTouchUp(x, y, 0, 0, paint);
    }


}

