package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class HexagonBrush extends AbstractBrush implements Brush {


    private Point leftPoint, rightPoint, bottomLeftPoint, bottomRightPoint, topLeftPoint, topRightPoint;
    private Point testPoint;
    private double edgeDistanceRatio;
    private int shortHeight;
    private int quarterBrushSize;


    public HexagonBrush(Canvas canvas, Paint paint){
        super(canvas, paint);
        leftPoint = new Point();
        rightPoint = new Point();
        bottomLeftPoint = new Point();
        bottomRightPoint = new Point();
        topLeftPoint = new Point();
        topRightPoint = new Point();
        testPoint = new Point();
        edgeDistanceRatio = Math.sqrt(3f) / 2;
    }

    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        shortHeight = (int)(halfBrushSize * edgeDistanceRatio);
        quarterBrushSize = halfBrushSize / 2;
    }


    @Override
    public void onTouchDown(float x, float y){

        leftPoint.set(x - halfBrushSize, y);
        rightPoint.set(x + halfBrushSize, y);

        bottomLeftPoint.set(x - quarterBrushSize, y + shortHeight);
        bottomRightPoint.set(x + quarterBrushSize, y + shortHeight);


        topLeftPoint.set(x - quarterBrushSize, y - shortHeight);
        topRightPoint.set(x + quarterBrushSize, y - shortHeight);

        testPoint.set(x - brushSize, y + brushSize);

        Path path = new Path();
        path.moveTo(leftPoint.x, leftPoint.y);
        path.lineTo(topLeftPoint. x, topLeftPoint.y);
        path.lineTo(topRightPoint.x, topRightPoint.y);
        path.lineTo(rightPoint.x, rightPoint.y);
        path.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        path.lineTo(bottomLeftPoint.x, bottomLeftPoint.y);
        //path.lineTo(testPoint.x, testPoint.y);
        path.lineTo(leftPoint.x, leftPoint.y);

        path.close();

        canvas.drawPath(path, paint);
    }



    @Override
    public void onTouchMove(float x, float y){
        onTouchDown(x ,y);
    }

}