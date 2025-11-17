package com.jacstuff.sketchy.brushes.shapes.twostep;

import android.graphics.PointF;

import com.jacstuff.sketchy.utils.MathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TrianglePoints {

    private final int MAX_NUMBER_OF_POINTS = 100;
    private final Set<PointF> recentPoints;

    public TrianglePoints(){
        recentPoints = new LinkedHashSet<>(MAX_NUMBER_OF_POINTS);
    }

    public TrianglePoints(TrianglePoints existingTrianglePoints){
        recentPoints = Collections.newSetFromMap(new LinkedHashMap<>(){
            protected boolean removeEldestEntry(Map.Entry<PointF, Boolean> eldest) {
                return size() > MAX_NUMBER_OF_POINTS;
            }
        });
        recentPoints.addAll(existingTrianglePoints.getAllPoints());
    }


    public void addPoints(PointF ...points){
        log("entered addPoints(... points) number of points to add: " + points.length);

        for(PointF p : points){
            if(p != null){
                addPoint(p);
            }
        }
        log("addPoints(...points) number of points now: " + recentPoints.size());
    }


    private void log(String msg){
        System.out.println("^^^ TrianglePoints: " + msg);
    }

    public void reset(){
        recentPoints.clear();
    }


    public Set<PointF> getAllPoints(){
        return recentPoints;
    }


    public PointF getNearbyPointOrAdd(PointF originalPoint){
        log("entered getNearbyPointOrAdd()");
        final float threshold = 40f;
        Optional<PointF> result = recentPoints.parallelStream().filter(p -> MathUtils.getDistance(p, originalPoint) < threshold).findFirst();
        if(result.isPresent()){
            return result.get();
        }
        addPoint(originalPoint);
        return originalPoint;
    }


    public void addPoint(PointF p){
        log("entered addPoint(p)");
        /*
        for(PointF existingPoint : recentPoints){
            if(p.x == existingPoint.x && p.y == existingPoint.y){
                return;
            }
        }

         */
        recentPoints.add(p);
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

}
