package com.jacstuff.sketchy.brushes.shapes.smoothpath;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractBrush;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;

import java.util.ArrayList;
import java.util.List;

public class SmoothPathBrush extends AbstractBrush implements Brush {

    private Path path, smoothPath;
    private List<Point> inputPoints;
    private List<Point> smoothPoints;
    private boolean isSmoothPathReady;
    private Paint movePaint;
    private List<Point> tempPoints;
    private final ImportantPoints importantPoints;

    public SmoothPathBrush(){
        super(BrushShape.SMOOTH_PATH);
        brushInitializer = new LineInitializer();
        drawerType = DrawerFactory.Type.SMOOTH_PATH;
        initPointsLists();
        importantPoints = new ImportantPoints();
        isDrawnFromCenter = false;
    }


    private void initPointsLists(){
        int initialCapacity = 1000;
        inputPoints = new ArrayList<>(initialCapacity);
        smoothPoints = new ArrayList<>(initialCapacity);
        tempPoints  = new ArrayList<>(initialCapacity);
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        setBrushSize((int)paint.getStrokeWidth());
        initPath(p);
        initMovePaint(paint);
        init(inputPoints, p);
        init(smoothPoints, p);
        isSmoothPathReady = false;
        importantPoints.initPoints();
    }


    @Override
    public void onTouchMove(Point p, Canvas canvas, Paint paint){
        currentStyle.onDraw();
        path.lineTo(p.x, p.y);
        inputPoints.add(p);
        importantPoints.updatePoints(p);
        canvas.drawPath(path, movePaint);
    }


    @Override
    public PointF getShapeMidpoint(){
        return importantPoints.getMidpoint();
    }


    @Override
    public PointF getShapeMinPoint(){
        return importantPoints.getMinPoint();
    }


    @Override
    public PointF getShapeMaxPoint(){
        return importantPoints.getMaxPoint();
    }


    @Override
    public void onTouchUp(Point p, Canvas canvas, Paint paint){
        if(inputPoints.size() < 5){
            canvas.drawPath(path, movePaint);
            return;
        }
        setupSmoothPath(p);
        canvas.drawPath(smoothPath, paint);
    }


    private void initPath(Point p){
        path = new Path();
        path.moveTo(p.x, p.y);
    }


    private void initMovePaint(Paint paint){
        movePaint = new Paint(paint);
        movePaint.setStyle(Paint.Style.STROKE);
    }


    private void init(List<Point> pointsList, Point firstPoint){
        pointsList.clear();
        pointsList.add(firstPoint);
    }


    private void setupSmoothPath(Point negativeOffset){
        if(isSmoothPathReady){
            return;
        }
        calculateSmoothPoints();
        createSmoothPath(negativeOffset);
        isSmoothPathReady = true;
    }


    private void calculateSmoothPoints(){
        adjustSmoothPoints();
        joinFirstAndLastPointsIfCloseTogether();
        for(int i = 0, numberOfRounds = 3; i < numberOfRounds; i++){
            adjustSmoothPoints();
        }
    }


    private void joinFirstAndLastPointsIfCloseTogether(){
        int proximityThreshold = 40;
        Point firstPoint = smoothPoints.get(0);
        Point lastPoint = smoothPoints.get(smoothPoints.size()-1);
        if(arePointsClose(firstPoint, lastPoint, proximityThreshold)){
            adjustPointsToAverage(firstPoint, lastPoint);
        }
    }


    private boolean arePointsClose(Point p1, Point p2, int minDistance){
        return areCoordinatesClose(p1.x, p2.x, minDistance)
                && areCoordinatesClose(p1.y, p2.y, minDistance);
    }


    private void adjustPointsToAverage(Point p1, Point p2){
        int avgX = (p1.x + p2.x) /2;
        int avgY = (p1.y + p2.y) /2;
        p1.x = avgX;
        p1.y = avgY;
        p2.x = avgX;
        p2.y = avgY;
    }


    private boolean areCoordinatesClose(int a1, int a2, int minDistance){
        return Math.abs(a1 - a2) < minDistance;
    }


    private void adjustSmoothPoints(){
        clearListAndFirstPointTo(smoothPoints);
        clearListAndFirstPointTo(tempPoints);
        for(int i = 1; i < inputPoints.size()-1; i++){
            Point p = new Point(getAveragePointX(i), getAveragePointY(i));
            smoothPoints.add(p);
            tempPoints.add(p);
        }
        addLastPointTo(smoothPoints);
        addLastPointTo(tempPoints);
        inputPoints.clear();
        inputPoints.addAll(tempPoints);
    }


    private void clearListAndFirstPointTo(List<Point> pointList){
        pointList.clear();
        pointList.add(inputPoints.get(0));
    }


    private void addLastPointTo(List<Point> pointList){
        pointList.add(inputPoints.get(inputPoints.size()-1));
    }


    private int getAveragePointX(int index){
        return (inputPoints.get(index).x
                + inputPoints.get(index-1).x
                + inputPoints.get(index+1).x)/3;
    }


    private int getAveragePointY(int index){
        return (inputPoints.get(index).y
                + inputPoints.get(index-1).y
                + inputPoints.get(index+1).y)/3;
    }


    private void createSmoothPath(Point negativeOffset){
        smoothPath = new Path();
        smoothPath.moveTo(smoothPoints.get(0).x - negativeOffset.x,
                smoothPoints.get(0).y - negativeOffset.y);

        for(int i=1; i<smoothPoints.size(); i++){
            smoothPath.lineTo(smoothPoints.get(i).x - negativeOffset.x,
                    smoothPoints.get(i).y - negativeOffset.y);
        }
    }


}