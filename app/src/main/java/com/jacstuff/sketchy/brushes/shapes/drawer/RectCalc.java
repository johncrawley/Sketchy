package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.PointF;

/*
    In order to draw a drag-rectangle at an angle, so that the bottom corner is on the touch-point,
     - we need to:
        - calculate the distance between the 2 corners
        - create a rectangle that is horizontal with the appropriate side length
        - rotate it into position.
 */
public class RectCalc {

    public PointF calculateRect(PointF p1, PointF p2, int angle){
        PointF pb = translateToZero( p1, p2);
        int startingAngleDegrees = 360;
        double startingAngle = Math.toRadians((startingAngleDegrees - angle));
        float cos =  (float)Math.cos(startingAngle);
        float sine = (float)Math.sin(startingAngle);
        PointF corner = new PointF();
        corner.x = p1.x + ((pb.x * cos) - (pb.y * sine));
        corner.y = p1.y + (pb.x * sine) + (pb.y * cos);
        return corner;
    }


    private PointF translateToZero(PointF p1, PointF p2){
        PointF p = new PointF();
        p.x = p2.x - p1.x;
        p.y = p2.y - p1.y;
        return p;
    }

}
