package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;

public class ParallelogramBrush extends AbstractBrush implements Brush {

    private final Point topRight, topLeft, bottomRight, bottomLeft;
    private final Path path;

    public ParallelogramBrush(){
        super(BrushShape.PARALLELOGRAM);
        path = new Path();
        topRight = new Point();
        topLeft = new Point();
        bottomRight = new Point();
        bottomLeft = new Point();
    }

    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        canvas.drawPath(path, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        int quarterBrushSize = Math.max(2, halfBrushSize/2);

        bottomLeft.x = -halfBrushSize;
        bottomLeft.y = halfBrushSize;
        bottomRight.x = halfBrushSize;
        bottomRight.y = halfBrushSize;
        topRight.x = halfBrushSize + quarterBrushSize;
        topRight.y = -halfBrushSize;
        topLeft.x = -quarterBrushSize;
        topLeft.y = -halfBrushSize;
        path.reset();
        path.moveTo(bottomLeft.x, bottomLeft.y);
        path.lineTo(topLeft.x, topLeft.y);
        path.lineTo(topRight.x, topRight.y);
        path.lineTo(bottomRight.x, bottomRight.y);
        path.close();
    }
}