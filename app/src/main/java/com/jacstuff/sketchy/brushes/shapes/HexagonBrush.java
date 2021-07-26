package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;

public class HexagonBrush extends AbstractBrush implements Brush {


    private final Point leftPoint, rightPoint, bottomLeftPoint, bottomRightPoint, topLeftPoint, topRightPoint;
    private final double edgeDistanceRatio;
    private int shortHeight;
    private int quarterBrushSize;


    public HexagonBrush(){
        super(BrushShape.HEXAGON);
        leftPoint = new Point();
        rightPoint = new Point();
        bottomLeftPoint = new Point();
        bottomRightPoint = new Point();
        topLeftPoint = new Point();
        topRightPoint = new Point();
        edgeDistanceRatio = Math.sqrt(3f) / 2;
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        shortHeight = (int)(halfBrushSize * edgeDistanceRatio);
        quarterBrushSize = halfBrushSize / 2;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        leftPoint.set( - halfBrushSize, 0);
        rightPoint.set(halfBrushSize, 0);

        bottomLeftPoint.set(- quarterBrushSize, shortHeight);
        bottomRightPoint.set( quarterBrushSize, shortHeight);

        topLeftPoint.set( - quarterBrushSize, -shortHeight);
        topRightPoint.set( + quarterBrushSize, -shortHeight);

        Path path = new Path();
        path.moveTo(leftPoint.x, leftPoint.y);
        path.lineTo(topLeftPoint. x, topLeftPoint.y);
        path.lineTo(topRightPoint.x, topRightPoint.y);
        path.lineTo(rightPoint.x, rightPoint.y);
        path.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        path.lineTo(bottomLeftPoint.x, bottomLeftPoint.y);
        path.lineTo(leftPoint.x, leftPoint.y);

        path.close();

        canvas.drawPath(path, paint);
    }

}