package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.twostep.StepState;
import com.jacstuff.sketchy.brushes.shapes.twostep.TwoStepBrush;
import com.jacstuff.sketchy.paintview.PaintView;

public class TwoStepDrawer extends AbstractDrawer implements Drawer{

    private PointF down;
    final TwoStepBrush twoStepBrush;
    private PointF lineMidPoint;

    public TwoStepDrawer(PaintView paintView, TwoStepBrush twoStepBrush){
        super(paintView);
        isColorChangedOnDown = false;
        this.twoStepBrush = twoStepBrush;
    }


    @Override
    public void down(float x, float y, Paint paint) {
        if(twoStepBrush.isInFirstStep()) {
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
        if(twoStepBrush.isInSecondStep()){
            paintView.disablePreviewLayer();
        }
        if(kaleidoscopeHelper.isEnabled() && twoStepBrush.isInSecondStep()){
            assignGradientForCurveMode(x,y, true);
            kaleidoscopeDrawer.drawKaleidoscope(x, y, paint);
        }
        else{
            drawDragLine(x,y, paint);
        }
        if(twoStepBrush.isInSecondStep()) {
            paintView.pushHistory();
            paintView.invalidate();
            twoStepBrush.setStateTo(StepState.FIRST);
            return;
        }

        assignLineMidPoint(x,y);
        twoStepBrush.setStateTo(StepState.SECOND);
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
        if(!twoStepBrush.isInSecondStep()) {
            return;
        }
        PointF down = createCoordinatePoint(lineMidPoint.x, lineMidPoint.y, isUsingKaleidoscopeOffsets);
        PointF up = createCoordinatePoint(x,y, isUsingKaleidoscopeOffsets);
        PointF mid = getShapeMidpoint(down, up, isUsingKaleidoscopeOffsets);
        paintHelperManager.getGradientHelper().assignGradientForDragShape(down, up, mid, false);
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


    PointF getShapeMidpoint(PointF down, PointF up, boolean isUsingKaleidoscopeOffsets){
        PointF mid = twoStepBrush.getLineMidPoint();
        mid.x = down.x + ((up.x - mid.x + getKaleidoscopeOffsetX(isUsingKaleidoscopeOffsets)) / 2);
        mid.y = down.y + ((up.y - mid.y + getKaleidoscopeOffsetY(isUsingKaleidoscopeOffsets)) / 2);
        return mid;
    }


    int getKaleidoscopeOffsetX(boolean isUsingKaleidoscopeOffsets){
        if(kaleidoscopeHelper.isEnabled() && isUsingKaleidoscopeOffsets){
            return kaleidoscopeHelper.getCenterX();
        }
        return 0;
    }


    int getKaleidoscopeOffsetY(boolean isUsingKaleidoscopeOffsets){
        if(kaleidoscopeHelper.isEnabled() && isUsingKaleidoscopeOffsets){
            return kaleidoscopeHelper.getCenterY();
        }
        return 0;
    }


    void drawDragLine(float x, float y, Paint paint){
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchUp(x, y, 0,0, paintView.getShadowPaint());
        }
        brush.onTouchUp(x, y, 0, 0, paint);
    }


}

