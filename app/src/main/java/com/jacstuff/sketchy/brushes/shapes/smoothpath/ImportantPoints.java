package com.jacstuff.sketchy.brushes.shapes.smoothpath;

import android.graphics.Point;
import android.graphics.PointF;

public class ImportantPoints {

    private int midX, midY;
    private int minX,minY, maxX, maxY;

    public void initPoints(){
        midX = 0;
        midY = 0;
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
    }


    public void updatePoints(Point p){
        midX = (midX + p.x) / 2;
        midY = (midY + p.y) / 2;
        minX = Math.min(minX, p.x);
        minY = Math.min(minY, p.y);
        maxX = Math.max(maxX, p.x);
        maxY = Math.max(maxY, p.y);
    }


    public PointF getMidpoint(){
        return new PointF(midX, midY);
    }


    public PointF getMinPoint(){
        return new PointF(minX, minY);
    }


    public PointF getMaxPoint(){
        return new PointF(maxX, maxY);
    }



}
