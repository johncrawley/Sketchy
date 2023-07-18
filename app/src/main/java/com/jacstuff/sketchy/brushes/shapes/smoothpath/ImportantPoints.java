package com.jacstuff.sketchy.brushes.shapes.smoothpath;

import android.graphics.Point;
import android.graphics.PointF;

public class ImportantPoints {

    private int minX,minY, maxX, maxY;

    public void initPoints(){
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
    }


    public void updatePoints(Point p){
        minX = Math.min(minX, p.x);
        minY = Math.min(minY, p.y);
        maxX = Math.max(maxX, p.x);
        maxY = Math.max(maxY, p.y);
    }


    public PointF getMidpoint(){
        return new PointF((minX + maxX)/2f, (minY + maxY)/2f);
    }


    public PointF getMinPoint(){
        return new PointF(minX, minY);
    }


    public PointF getMaxPoint(){
        return new PointF(maxX, maxY);
    }



}
