package com.jacstuff.sketchy.multicolor.multicolor;

import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.drawer.RectCalc;

import org.junit.Test;

public class RectCalcTest {

    private RectCalc rectCalc;

    @Test
    public void testCalc(){
        RectCalc rectCalc = new RectCalc();
        PointF p1 = new PointF(0,0);
        PointF p2 = new PointF(10, 10);
        p2.x = 10;
        p2.y = 10;
        log("p1 : " + p1.x + "," + p1.y + " p2: " + p2.x + "," + p2.y);
        PointF p3 = rectCalc.calculateRect(p1, p2, 0);
        System.out.println("Point 3: " + p3.x + "," + p3.y);
    }

    private void log(String msg){
        System.out.println("RectCalcTest : " + msg);
    }

    @Test
    public void testIntersection(){
        int angle = 0;

        float x1 = 10;
        float y1 = 10;

        float x2 = 20;
        float y2 = 20;

        float slopeOfMiddleLine = (y2 - y1) / (x2 -x1);
        System.out.println("Slope of middle line :"  + slopeOfMiddleLine);

        double slope1 = Math.toRadians(angle);
        double slope2 = Math.toRadians(angle + 90);

        for(int i=0; i< 360; i+=3){
            double rads = Math.toRadians(i);
            log("slope of angle " + i + " using tan : " + Math.tan(rads) + " " + Math.atan(rads) + " "  + Math.tan(i) + " " + Math.atan(i));
        }

        double b1 = slope1 == 0 ? 0 : y1/ (slope1 * x1);
        double b2 = slope2 == 0 ? 0 : y2/ (slope2 * x2);
        System.out.println("b1, b2: " + b1 + ", " + b2 + " slope1, slope2: " + slope1 + " , " + slope2);

        double x3 = (b2 - b1) / (slope1 - slope2);
        double y3 = slope1 * x3 + b1;

        System.out.println(" point 3 : " + x3 + "," + y3);

        float px1 = 10;
        float px2 = 10;


    }
}
