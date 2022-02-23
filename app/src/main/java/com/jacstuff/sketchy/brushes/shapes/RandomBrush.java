package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBrush extends AbstractBrush implements Brush {

    private final List<Point> points, targetPoints;
    private final MainViewModel viewModel;
    private final Random random;
    private int quarterBrushSize;


    public RandomBrush(MainViewModel viewModel) {
        super(BrushShape.RANDOM);
        int initialCapacity = 12;
        this.viewModel = viewModel;
        random = new Random(System.currentTimeMillis());
        points = new ArrayList<>(initialCapacity);
        targetPoints = new ArrayList<>(initialCapacity);
        setRandomPoints();
    }


    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
        setRandomPoints();
        quarterBrushSize = halfBrushSize /2;
    }


    @Override
    public void touchMove(float x, float y, Paint paint){
        super.touchMove(x, y, paint);
        adjustPoints();
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        Path path = new Path();
        Point firstPoint = points.get(0);
        path.moveTo(firstPoint.x, firstPoint.y);
        for(int i=1; i<points.size(); i++){
            path.lineTo(points.get(i).x, points.get(i).y);
        }
        path.close();
        canvas.drawPath(path, paint);
    }


    private void adjustPoints(){
        if(viewModel.doesRandomBrushMorph){
            for(int i=0; i< points.size(); i++){
                Point p = points.get(i);
                Point target = targetPoints.get(i);
                adjustPoint(p, target);
                if(areEqual(p,target)){
                    assignNewTarget(i);
                }
            }
        }
    }


    private void setRandomPoints(){
        addRandomPoints(points);
        addRandomPoints(targetPoints);
    }


    private void addRandomPoints(List<Point> list){
        list.clear();
        for(int i=0; i < viewModel.randomBrushNumberOfPoints; i++){
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


    private boolean areEqual(Point p1, Point p2){
        return p1.x == p2.x && p1.y == p2.y;
    }


    private void assignNewTarget(int index){
        targetPoints.set(index, createRandomPoint());
    }


    private Point createRandomPoint(){
       return new Point(createRandomCoordinate(), createRandomCoordinate());
    }


    private int createRandomCoordinate(){
        int value = quarterBrushSize + random.nextInt(2 + quarterBrushSize);
        return random.nextBoolean() ? value : value * -1;
    }


}
