package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBrush extends AbstractBrush implements Brush {

    private final List<Point> points, targetPoints;
    private final Random random;
    private int quarterBrushSize;
    private int xSign = 1;
    private int ySign = 1;
    private boolean wasXSignAdjustedLast;
    private boolean areNewPointsRequired;


    public RandomBrush() {
        super(BrushShape.RANDOM);
        int initialCapacity = 12;
        random = new Random(System.currentTimeMillis());
        points = new ArrayList<>(initialCapacity);
        targetPoints = new ArrayList<>(initialCapacity);
    }


    @Override
    public void setBrushSize(int brushSize) {
        int oldQuarterSize = quarterBrushSize;
        super.setBrushSize(brushSize);
        quarterBrushSize = halfBrushSize /2;
        if(!mainViewModel.doesRandomBrushMorph){
           adjustPointsWithBrushSizeDifference(quarterBrushSize - oldQuarterSize);
        }
    }


    @Override
    public void reinitialize(){
        super.reinitialize();
        assignRandomPoints();
    }

    @Override
    public void touchMove(float x, float y, Paint paint){
        super.touchMove(x, y, paint);
        adjustPoints();
    }


    @Override
    public void touchUp(float x, float y, Paint paint){
        super.touchUp(x,y,paint);
        areNewPointsRequired = true;
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        Path path = new Path();
        if(areNewPointsRequired){
            assignRandomPoints();
            areNewPointsRequired = false;
        }
        Point firstPoint = points.get(0);
        path.moveTo(firstPoint.x, firstPoint.y);
        for(int i=1; i<points.size(); i++){
            path.lineTo(points.get(i).x, points.get(i).y);
        }
        path.close();
        canvas.drawPath(path, paint);
    }


    private void adjustPoints(){
        if(mainViewModel.doesRandomBrushMorph){
            for(int i=0; i< points.size(); i++){
               adjustPoint(i);
            }
        }
    }


    private void adjustPoint(int index){
        Point p = points.get(index);
        Point target = targetPoints.get(index);
        adjustPoint(p, target);
        if(areEqual(p, target)){
            assignNewTarget(index);
        }
    }


    private void assignRandomPoints(){
        addRandomPoints(points);
        addRandomPoints(targetPoints);
    }


    private void addRandomPoints(List<Point> list){
        list.clear();
        int numberOfVertices = 3 + mainViewModel.randomBrushNumberOfPoints;
        for(int i=0; i < numberOfVertices; i++){
            list.add(createRandomPoint());
        }
    }


    private void adjustPoint(Point p, Point target){
        if(p.x < target.x){
            p.x++;
        }
        else if(p.x > target.x){
            p.x--;
        }
        if(p.y < target.y){
            p.y++;
        }
        else if(p.y > target.y){
            p.y--;
        }
    }


    private void adjustPointsWithBrushSizeDifference(int quarterBrushSizeDifference){
        for(Point p : points){
            adjustPointWithBrushSizeDifference(p, quarterBrushSizeDifference);
        }
    }


    private void adjustPointWithBrushSizeDifference(Point p, int diff){
        p.x = adjustCoordinateToDifference(p.x, diff);
        p.y = adjustCoordinateToDifference(p.y, diff);
    }


    private int adjustCoordinateToDifference(int value, int diff){
        if(value < 0){
            diff *= - 1;
        }
        return value + diff;
        //return value < 0 ? value - quarterBrushSizeDifference : value + quarterBrushSizeDifference;
    }


    private boolean areEqual(Point p1, Point p2){
        return p1.x == p2.x && p1.y == p2.y;
    }


    private void assignNewTarget(int index){
        targetPoints.set(index, createRandomPoint());
    }


    private Point createRandomPoint(){
        adjustSigns();
       return new Point(createRandomCoordinate(xSign), createRandomCoordinate(ySign));
    }


    private void adjustSigns(){
        if(wasXSignAdjustedLast){
            ySign *= -1;
        }
        else{
            xSign *= -1;
        }
        wasXSignAdjustedLast = !wasXSignAdjustedLast;
    }


    private int createRandomCoordinate(int sign){
        int value = quarterBrushSize + random.nextInt(2 + quarterBrushSize);
        return value * sign;
       // return random.nextBoolean() ? value : value * -1;
    }

}
