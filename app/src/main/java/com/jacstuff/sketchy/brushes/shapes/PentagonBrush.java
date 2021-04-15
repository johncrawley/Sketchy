package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class PentagonBrush extends AbstractBrush implements Brush {


    private Point leftPoint, rightPoint, bottomLeftPoint, bottomRightPoint, topPoint;
    private Point testPoint;
    private double edgeDistanceRatio;
    private int shortHeight;
    private int quarterBrushSize;
    private float heightToOppositePointRatio = 1.051414f;
    private int angleFromTopPointToBottomLeft = 72;
    private int angleFromTopPointToBottomRight = 288;

    private int angleFromBottomRightPointToLeft = 144;
    private int angleFromBottomLeftPointToRight = 36;
    private float distanceToOppositePoint = 0f;


    public PentagonBrush(Canvas canvas, Paint paint){
        super(canvas, paint);
        leftPoint = new Point();
        rightPoint = new Point();
        bottomLeftPoint = new Point();
        bottomRightPoint = new Point();
        topPoint = new Point();
        testPoint = new Point();

        tempPoint = new Point();
    }

    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        shortHeight = (int)(halfBrushSize * edgeDistanceRatio);
        quarterBrushSize = halfBrushSize / 2;

        distanceToOppositePoint = brushSize * heightToOppositePointRatio;
    }

    private int getXPoint(float x0, int angle){
        return (int) (distanceToOppositePoint * Math.cos(angle)) + (int)x0;
    }

    private int getYPoint(float y0, int angle){
        return (int)(distanceToOppositePoint * Math.sin(angle)) + (int)y0;
    }

    Point tempPoint;
    @Override
    public void onTouchDown(float x, float y){

        topPoint.set(x, y - halfBrushSize);

        bottomLeftPoint.set(getXPoint(x, angleFromTopPointToBottomLeft), getYPoint(y, angleFromTopPointToBottomLeft));
        bottomRightPoint.set(getXPoint(x, angleFromTopPointToBottomRight), getYPoint(y, angleFromTopPointToBottomRight));

        leftPoint.set(getXPoint(bottomRightPoint.x, angleFromBottomRightPointToLeft ), getYPoint(bottomRightPoint.y, angleFromBottomRightPointToLeft));
        rightPoint.set(getXPoint(bottomLeftPoint.x, angleFromBottomLeftPointToRight ), getYPoint(bottomLeftPoint.y, angleFromBottomLeftPointToRight));

        tempPoint.set(x, y);

        Path path = new Path();
        path.moveTo(topPoint.x, topPoint.y);
        //path.lineTo(rightPoint.x, rightPoint.y);
        //path.lineTo(bottomRightPoint.x, bottomRightPoint.y);
        path.lineTo(bottomLeftPoint.x, bottomLeftPoint.y);
        //path.lineTo(leftPoint.x, leftPoint.y);
        //path.lineTo(topPoint.x, topPoint.y);

        path.close();

        canvas.drawPath(path, paint);

        path = new Path();
        path.moveTo(topPoint.x, topPoint.y);
        path.lineTo(tempPoint.x, tempPoint.y);
        path.close();
        canvas.drawPath(path, paint);


    }



    @Override
    public void onTouchMove(float x, float y){
        onTouchDown(x ,y);
    }

}