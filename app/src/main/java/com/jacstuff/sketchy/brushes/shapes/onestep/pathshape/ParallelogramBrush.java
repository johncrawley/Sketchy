package com.jacstuff.sketchy.brushes.shapes.onestep.pathshape;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class ParallelogramBrush extends AbstractPathShape {

    private final Point topRight, topLeft, bottomRight, bottomLeft;

    public ParallelogramBrush(){
        super(BrushShape.PARALLELOGRAM);
        topRight = new Point();
        topLeft = new Point();
        bottomRight = new Point();
        bottomLeft = new Point();
    }

    @Override
    public void generatePath(PointF p){
        setPoints();
        path = new Path();
        path.reset();
        path.moveTo(bottomLeft.x, bottomLeft.y);
        path.lineTo(topLeft.x, topLeft.y);
        path.lineTo(topRight.x, topRight.y);
        path.lineTo(bottomRight.x, bottomRight.y);
        path.close();
    }


    private void setPoints(){
        bottomLeft.x = -halfBrushSize;
        bottomLeft.y = halfBrushSize;

        bottomRight.x = halfBrushSize;
        bottomRight.y = halfBrushSize;

        topRight.x = halfBrushSize + quarterBrushSize;
        topRight.y = -halfBrushSize;

        topLeft.x = -quarterBrushSize;
        topLeft.y = -halfBrushSize;
    }
}