package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class HexagonBrush extends AbstractShape{

    private final Point leftPoint, rightPoint, bottomLeftPoint, bottomRightPoint, topLeftPoint, topRightPoint;
    private final double edgeDistanceRatio;
    private int shortHeight;
    private int quarterBrushSize;
    private Path path;


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
    public void generatePath(PointF point) {
        leftPoint.set( - halfBrushSize, 0);
        rightPoint.set(halfBrushSize, 0);

        bottomLeftPoint.set(- quarterBrushSize, shortHeight);
        bottomRightPoint.set( quarterBrushSize, shortHeight);

        topLeftPoint.set( - quarterBrushSize, -shortHeight);
        topRightPoint.set( + quarterBrushSize, -shortHeight);

        path = new Path();
        path.moveTo(leftPoint.x, leftPoint.y);
        path.lineTo(topLeftPoint. x, topLeftPoint.y);
        path.lineTo(topRightPoint.x, topRightPoint.y);
        path.lineTo(rightPoint.x, rightPoint.y);
        path.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        path.lineTo(bottomLeftPoint.x, bottomLeftPoint.y);
        path.lineTo(leftPoint.x, leftPoint.y);

        path.close();
    }


    @Override
    public void draw(PointF point, Canvas canvas, Paint paint) {
        canvas.drawPath(path, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        shortHeight = (int)(halfBrushSize * edgeDistanceRatio);
        quarterBrushSize = halfBrushSize / 2;
    }


}