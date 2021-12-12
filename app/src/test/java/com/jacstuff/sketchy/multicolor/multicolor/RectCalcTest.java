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
}
