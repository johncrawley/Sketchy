package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.PointF;

public class RectCalc {

    public PointF calculateRect(PointF p1, PointF p2, int angle){
        log(" entered caculateRect p1: "+ p1.x + "," + p1.y + " p2: " + p2.x + "," + p2.y);

        PointF rotP1 = new PointF();
        rotP1.x = p1.y;
        rotP1.y = p1.x;
        //rotP1.x = p1.x;
        //rotP1.y = p1.y;

        PointF pb = new PointF();
        pb.x = p2.y;
        pb.y = p2.x;
       // pb.x = p2.x;
       // pb.y = p2.y;
        PointF translatedP2 = translateToZero( pb, rotP1);
        log(" p1 : " + p1.x + "," + p1.y);
        log(" p2 : " + p2.x + "," + p2.y);
        log("Translated p2 is now: " + translatedP2.x + "," + translatedP2.y);
        int startingAngle = 0;
        float cos = (float)Math.cos(startingAngle + angle);
        float sine = (float)Math.sin( startingAngle + angle);

        float cosSquared = cos * cos;
        float sineSquared = sine * sine;

        PointF corner = new PointF();
        corner.x = (pb.x / (cos + sineSquared)) + (pb.y / (cosSquared + sineSquared));
        corner.y = (pb.y / (cos + sineSquared))  -(pb.y / (cosSquared - sine));

        log(" tX2, tY2: " + corner.x + "," + corner.y);

        return corner;
    }


    private PointF translateToZero(PointF p2, PointF p1){
        PointF p = new PointF();
        p.x = p2.x - p1.x;
        p.y = p2.y - p1.y;
        return p;
    }



    private PointF translateBack(PointF pointToTranslate, PointF originalZeroPoint){
        PointF p = new PointF();
        p.x = pointToTranslate.x + originalZeroPoint.x;
        p.y = pointToTranslate.y + originalZeroPoint.y;
        return p;
    }

    private void log(String msg){
        System.out.println("RectCalc: " + msg);
    }
}
