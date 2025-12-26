package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class PointedOvalBrush extends AbstractPathShape{

    private float midpointHeight;

    public PointedOvalBrush(){
        super(BrushShape.POINTED_OVAL);
    }


    @Override
    public void generatePath(PointF p){
        Point leftPoint = new Point(-halfBrushSize, 0);
        Point rightPoint = new Point(halfBrushSize, 0);

        Point midPointTop = new Point(0, -(int)midpointHeight);
        Point midPointBottom = new Point(0, (int)midpointHeight);

        Path path = new Path();
        path.moveTo( leftPoint.x, leftPoint.y);
        path.quadTo(midPointTop.x, midPointTop.y, rightPoint.x, rightPoint.y);
        path.quadTo(midPointBottom.x, midPointBottom.y, leftPoint.x, leftPoint.y);
        path.close();
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        midpointHeight = halfBrushSize / 0.9f;
    }

}