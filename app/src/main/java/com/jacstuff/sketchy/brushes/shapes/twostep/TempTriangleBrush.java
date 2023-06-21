package com.jacstuff.sketchy.brushes.shapes.twostep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.drawer.ArbitraryTriangleDrawer;
import com.jacstuff.sketchy.brushes.shapes.initializer.DragRectInitializer;
import com.jacstuff.sketchy.utils.MathUtils;


public class TempTriangleBrush extends AbstractTwoStepBrush  implements Brush, TwoStepBrush {

    float downX, downY, upX, upY;
    private float lineMidpointX, lineMidpointY;
    final Path path;
    private float thirdPointX, thirdPointY;


    public TempTriangleBrush() {
        super(BrushShape.TRIANGLE_ARBITRARY);
        brushInitializer = new DragRectInitializer();
        path = new Path();
        isDrawnFromCenter = false;
        resetState();
        setBrushShape(BrushShape.TRIANGLE_ARBITRARY);
    }


    @Override
    public void postInit(){
        super.postInit();
        this.drawer = new ArbitraryTriangleDrawer(paintView, mainViewModel, this);
        drawer.init();
    }



    void drawShape(float x, float y, float offsetX, float offsetY, Paint paint){
        thirdPointX = x;
        thirdPointY = y;
        path.reset();
        path.moveTo(downX - offsetX, downY - offsetY);
        path.lineTo(thirdPointX -offsetX,thirdPointY - offsetY);
        path.lineTo(upX -offsetX, upY - offsetY);
        path.close();
        canvas.drawPath(path, paint);
    }


    public PointF getShapeMidPoint(){
        PointF point = new PointF();
        point.x = (downX + upX + thirdPointX) /3;
        point.y = (downY + upY + thirdPointY) / 3;
        return point;
    }


    @Override
    public void reset(){
        downX = 0;
        downY = 0;
        upX = 0;
        upY = 0;
        resetState();
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


    public PointF getLineMidPoint() {
        PointF p = new PointF();
        p.x = lineMidpointX;
        p.y = lineMidpointY;
        return p;
    }


}
