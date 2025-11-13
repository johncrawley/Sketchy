package com.jacstuff.sketchy.brushes.shapes.twostep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.drawer.TwoStepDrawer;
import com.jacstuff.sketchy.brushes.shapes.initializer.DragRectInitializer;
import com.jacstuff.sketchy.utils.MathUtils;

public class CurvedLineBrush extends AbstractTwoStepBrush implements Brush, TwoStepBrush{

    float downX, downY, upX, upY;
    private float lineMidpointX, lineMidpointY;
    final Path path;


    public CurvedLineBrush() {
        super(BrushShape.CURVE);
        brushInitializer = new DragRectInitializer();
        path = new Path();
        isDrawnFromCenter = false;
        resetStepState();

    }


    @Override
    public void postInit(){
        super.postInit();
        this.drawer = new TwoStepDrawer(paintView, viewModel, this);
        drawer.init();
    }


    @Override
    public void reset(){
        downX = 0;
        downY = 0;
        upX = 0;
        upY = 0;
        resetStepState();
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        if (stepState == StepState.FIRST) {
            downX = p.x;
            downY = p.y;
        }
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        if(stepState == StepState.SECOND){
            drawShape(x, y, 0,0, paint);
            return;
        }
        canvas.drawLine(downX, downY, x, y, paint);
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint) {
        onTouchUp(x, y, 0,0, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        if(stepState == StepState.SECOND){
            drawShape(x, y, offsetX, offsetY, paint);
            return;
        }
        canvas.drawLine(downX, downY, x, y, paint);
        upX = x;
        upY = y;
        lineMidpointX = ((upX + downX) /2);
        lineMidpointY = ((upY + downY) /2);
    }


    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }


    @Override
    public PointF getLineMidPoint() {
       PointF p = new PointF();
       p.x = lineMidpointX;
       p.y = lineMidpointY;
       return p;
    }


    @Override
    public PointF getShapeMidPoint() {
        return null;
    }


    private void drawShape(float x, float y, float offsetX, float offsetY, Paint paint){
        path.reset();
        path.moveTo(downX - offsetX, downY - offsetY);
        PointF point = getModifiedPoint(x,y);
        path.quadTo( point.x - offsetX, point.y - offsetY, upX - offsetX, upY - offsetY);
        canvas.drawPath(path, paint);
    }


    private PointF getModifiedPoint(float x, float y){
        PointF point = new PointF();
        float distance = MathUtils.getDistance(x,y, lineMidpointX, lineMidpointY);
        float d = distance == 0 ? 1 : distance;

        point.x = x + (d * (x - lineMidpointX))/distance;
        point.y = y + (d * (y - lineMidpointY))/distance;
        return point;
    }

}