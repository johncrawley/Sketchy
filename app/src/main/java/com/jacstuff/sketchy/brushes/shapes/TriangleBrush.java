package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.Easel;


public class TriangleBrush extends AbstractShape implements Brushable {


    private final Point leftPoint, rightPoint, topPoint;
    private int height;
    private int triangleHeight = 1;

    public TriangleBrush(){
        super(BrushShape.TRIANGLE);
        leftPoint = new Point();
        rightPoint = new Point();
        topPoint = new Point();
    }


    @Override
    public void onTouchDown(PointF p, Easel easel){

    }


    @Override
    public void onTouchMove(PointF p, Easel easel){

    }


    @Override
    public void onTouchUp(PointF p, Easel easel){

    }


    public void setTriangleHeight(int height){
        this.triangleHeight = height;
    }


    public int getTriangleHeight(){
        return triangleHeight;
    }


    public void reinitialize(){
        recalculateDimensions();
    }


    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        leftPoint.set(-halfBrushSize, height);
        rightPoint.set(halfBrushSize, height);
        topPoint.set(0, -height);

        Path path = new Path();
        path.moveTo(leftPoint.x, leftPoint.y);
        path.lineTo(topPoint. x, topPoint.y);
        path.lineTo(rightPoint.x, rightPoint.y);
        path.close();
        canvas.drawPath(path, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        recalculateDimensions();
    }


    @Override
    public void recalculateDimensions(){
        super.recalculateDimensions();
        height = (int)((brushSize/ 100f) * triangleHeight);
    }

}