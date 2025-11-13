package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;


public class TriangleBrush extends AbstractBrush implements Brush {


    private final Point leftPoint, rightPoint, topPoint;
    private int height;

    public TriangleBrush(){
        super(BrushShape.TRIANGLE);
        leftPoint = new Point();
        rightPoint = new Point();
        topPoint = new Point();
    }

    @Override
    public void reinitialize(){
        super.reinitialize();
        recalculateDimensions();
    }

    @Override
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
        height = (int)((brushSize/ 100f) * viewModel.triangleHeight);
    }

}