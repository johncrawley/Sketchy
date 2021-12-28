package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.drawer.CurveDrawer;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;
import com.jacstuff.sketchy.utils.MathUtils;

public class CurvedLineBrush extends AbstractBrush implements Brush {

    private float downX, downY, upX, upY;
    public enum State { DRAW_LINE, DRAW_CURVE }
    private State state;
    private final Path path;


    public CurvedLineBrush() {
        super(BrushShape.CURVE);
        brushInitializer = new LineInitializer();
        state = State.DRAW_LINE;
        path = new Path();
    }

    @Override
    public void postInit(){
        super.postInit();
        this.drawer = new CurveDrawer(paintView, mainViewModel, this);
    }


    @Override
    public void reset(){
        downX = 0;
        downY = 0;
        upX = 0;
        upY = 0;
        resetState();
    }

    public void resetState(){
        state = State.DRAW_LINE;
    }

    public void setStateTo(State state){
        this.state = state;
    }

    public State getState(){
        return  state;
    }

    public boolean isInDrawLineMode(){
        return state == State.DRAW_LINE;
    }

    public boolean isInDrawCurveMode(){
        return state == State.DRAW_CURVE;
    }

    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        if (state == State.DRAW_LINE) {
            downX = p.x;
            downY = p.y;
        }
    }

    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        if(state == State.DRAW_CURVE){
            drawCurve(x, y, paint);
            return;
        }
        canvas.drawLine(downX, downY, x, y, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        if(state == State.DRAW_CURVE){
            drawCurve(x, y, paint);
            return;
        }
        canvas.drawLine(downX, downY, x, y, paint);
        upX = x;
        upY = y;
    }


    private void drawCurve(float x, float y, Paint paint){
        path.reset();
        path.moveTo(downX, downY);
        PointF point = getModifiedPoint(x,y);
        path.quadTo( point.x, point.y, upX, upY);
        canvas.drawPath(path, paint);
    }


    private PointF getModifiedPoint(float x, float y){
        PointF point = new PointF();
        float lineMidpointX = (upX + downX) /2;
        float lineMidpointY = (upY + downY) /2;
        float distance = MathUtils.getDistance(x,y, lineMidpointX, lineMidpointY);
        float d = distance == 0 ? 1 : distance;

        point.x = x + (d * (x - lineMidpointX))/distance;
        point.y = y + (d * (y - lineMidpointY))/distance;
        return point;
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint) {
        onTouchUp(x, y, 0,0, paint);
    }


    @Override
    public void setStyle(BrushStyle brushStyle){
        super.setStyle(brushStyle);
    }


    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }
}