package com.jacstuff.sketchy.brushes.shapes.twostep;

import android.graphics.PointF;

import com.jacstuff.sketchy.utils.MathUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TrianglePoints {

    private final int MAX_NUMBER_OF_POINTS = 100;
    private final ArrayDeque<PointF> recentPoints;

    public TrianglePoints(){
        recentPoints = new ArrayDeque<>(MAX_NUMBER_OF_POINTS);
    }

    public TrianglePoints(TrianglePoints existingTrianglePoints){
        recentPoints = new ArrayDeque<>(existingTrianglePoints.getAllPoints());
    }


    public void addPoints(PointF ...points){
        if(recentPoints.size() >= MAX_NUMBER_OF_POINTS){
            removeOldestPoints();
        }
        for(PointF p : points){
           addPoint(p);
        }
    }


    public void reset(){
        recentPoints.clear();
    }


    private ArrayDeque<PointF> getAllPoints(){
        return recentPoints;
    }


    private void addPoint(PointF p){
        for(PointF existingPoint : recentPoints){
            if(p.x == existingPoint.x && p.y == existingPoint.y){
                return;
            }
        }
        recentPoints.addFirst(p);
    }


    public PointF getClosePointOrAddToExisting(PointF originalPoint){
        final float threshold = 40f;
        Optional<PointF> result = recentPoints.parallelStream().filter(p -> MathUtils.getDistance(p, originalPoint) < threshold).findFirst();
        if(result.isPresent()){
            return result.get();
        }
        addPoint(originalPoint);
        return originalPoint;
    }


    public List<PointF> getNearestPointsTo(PointF point){
        if(recentPoints.size() == 2){
            return new ArrayList<>(recentPoints);
        }
        if(recentPoints.size() < 2){
            return List.of(new PointF(100,100),
                    new PointF(130, 130));
        }
        return recentPoints.stream()
                .sorted((p1,p2) -> Float.compare(MathUtils.getDistance(p1, point), MathUtils.getDistance(p2, point)))
                .limit(2)
                .collect(Collectors.toList());
    }


    private void removeOldestPoints(){
        for(int i = 0; i < 3; i++){
            recentPoints.removeLast();
        }
    }
}
