package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;

public class PointedOvalBrush extends AbstractBrush implements Brush {

    private float quarterBrushSize;

    public PointedOvalBrush(){
        super(BrushShape.POINTED_OVAL);
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        Point leftPoint = new Point(-halfBrushSize, 0);
        Point rightPoint = new Point(halfBrushSize, 0);
        int  midPointLength = (int) quarterBrushSize;
        Point midPointTop = new Point(0, -midPointLength);
        Point midPointBottom = new Point(0, midPointLength);

        Path path = new Path();
        path.moveTo( leftPoint.x, leftPoint.y);
        path.quadTo(midPointTop.x, midPointTop.y, rightPoint.x, rightPoint.y);
        path.quadTo(midPointBottom.x, midPointBottom.y, leftPoint.x, leftPoint.y);
        path.close();
        canvas.drawPath(path, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x, y, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        quarterBrushSize = halfBrushSize / 2f;
    }

}