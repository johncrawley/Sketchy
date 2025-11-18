package com.jacstuff.sketchy.brushes.shapes.twostep;

import android.graphics.PointF;

import com.jacstuff.sketchy.utils.MathUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrianglePoints {

    private final int MAX_NUMBER_OF_POINTS = 100;
    private final Map<String, PointF> recentPoints;
    private PointF defaultPoint;


    public TrianglePoints(){
        defaultPoint = getDefaultPoint();
        recentPoints = new LinkedHashMap<>(MAX_NUMBER_OF_POINTS){
            public boolean removeEldestEntry(Map.Entry<String,PointF> eldest) {
                return size() > MAX_NUMBER_OF_POINTS;
            }
        };
    }


    public TrianglePoints(TrianglePoints existingTrianglePoints){
        this();
        recentPoints.putAll(existingTrianglePoints.getAllPoints());
    }


    public static PointF getDefaultPoint(){
        return new PointF(-100, -100);
    }


    private boolean isNotDefault(PointF p){
        return !(p.x == defaultPoint.x && p.y == defaultPoint.y);
    }


    public void addPoints(PointF ...points){
        for(PointF p : points){
            if(p != null && isNotDefault(p)){
                addPoint(p);
            }
        }
    }


    private void log(String msg){
        System.out.println("^^^ TrianglePoints: " + msg);
    }

    public void reset(){
        recentPoints.clear();
    }


    public Map<String, PointF> getAllPoints(){
        return recentPoints;
    }


    public PointF getNearbyPointToOrUse(PointF originalPoint){
        log("entered getNearbyPointOrAdd()");
        final float threshold = 40f;
        return recentPoints.values()
                .parallelStream()
                .filter(p -> MathUtils.getDistance(p, originalPoint) < threshold)
                .findFirst()
                .orElse(originalPoint);
    }


    public void addPoint(PointF p){
        log("entered addPoint(p)");
        String key = p.x + "," + p.y;
        recentPoints.put(key, p);
    }


    public List<PointF> getNearestPointsTo(PointF point){
        if(recentPoints.size() == 2){
            return new ArrayList<>(recentPoints.values());
        }
        if(recentPoints.size() < 2){
            return null;
        }
        return recentPoints.values().stream()
                .sorted((p1,p2) -> Float.compare(MathUtils.getDistance(p1, point), MathUtils.getDistance(p2, point)))
                .limit(2)
                .collect(Collectors.toList());
    }

}
