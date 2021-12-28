package com.jacstuff.sketchy.utils;

import android.graphics.Point;

public class MathUtils {

    public static float getDistance(Point p1, Point p2){
        double ac = Math.abs(p2.y - p1.y);
        double cb = Math.abs(p2.x - p1.x);
        return (float)Math.hypot(ac, cb);
    }

    public static float getDistance(float x1, float y1, float x2, float y2){
        double ac = Math.abs(y2 - y1);
        double cb = Math.abs(x2 - x1);
        return (float)Math.hypot(ac, cb);
    }
}
