package com.jacstuff.sketchy.brushes.shapes.onestep.pathshape;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;


public class TriangleBrush extends AbstractPathShape {


    private final Point leftPoint, rightPoint, topPoint;
    private int height;
    private int triangleHeight = 30;

    public TriangleBrush(){
        super(BrushShape.TRIANGLE);
        leftPoint = new Point();
        rightPoint = new Point();
        topPoint = new Point();
    }


    @Override
    public void generatePath(PointF p){
        leftPoint.set(-halfBrushSize, height);
        rightPoint.set(halfBrushSize, height);
        topPoint.set(0, -height);

        path = new Path();
        path.moveTo(leftPoint.x, leftPoint.y);
        path.lineTo(topPoint. x, topPoint.y);
        path.lineTo(rightPoint.x, rightPoint.y);
        path.close();
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