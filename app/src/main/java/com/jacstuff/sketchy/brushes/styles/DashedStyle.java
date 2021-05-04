package com.jacstuff.sketchy.brushes.styles;

import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class DashedStyle extends AbstractStyle implements Style {

    DashPathEffect dashPathEffect, dashPathEffect2;
    PathDashPathEffect pathDashPathEffect;
    private CornerPathEffect cornerPathEffect;
    private PaintGroup paintGroup;

    public DashedStyle(){
        float onStroke = 15;
        float offStroke = 36;
        dashPathEffect = new DashPathEffect(new float[] {onStroke, offStroke}, 0);
        pathDashPathEffect = new PathDashPathEffect(makePathDash(), 12, 5, PathDashPathEffect.Style.MORPH);
        cornerPathEffect = new CornerPathEffect(10);
        dashPathEffect2 = new DashPathEffect(new float[] {20, 10, 15, 15}, 3);
    }


    @Override
    public void init(PaintGroup paintGroup, int brushSize ) {
        this.paintGroup = paintGroup;
        this.brushSize = brushSize;
        paintGroup.setStyle(Paint.Style.STROKE);
        assignPath();
    }


    private static Path makePathDash() {
        Path p = new Path();
        p.moveTo(-6, 4);
        p.lineTo(6,4);
        p.lineTo(6,3);
        p.lineTo(-6, 3);
        p.close();
        p.moveTo(-6, -4);
        p.lineTo(6,-4);
        p.lineTo(6,-3);
        p.lineTo(-6, -3);
        return p;
    }


    private static Path makePathDash2(float lineWidthFactor) {

        Path p = new Path();
        p.moveTo(1 * lineWidthFactor, 1);
        p.lineTo(1,-1);
        p.lineTo(-2,-1 * lineWidthFactor);
        p.lineTo(-1, 2);
        p.close();
        return p;
    }


    private void assignPath(){
        float lineWidth = paintGroup.getLineWidth();
        float sizeFactor = (float)brushSize / 50;
        float spaceFactor = lineWidth / 5;
        float on1 = (5 * sizeFactor);//- spaceFactor;
        float off1 = on1 * 3 + spaceFactor;
     //   float off1 = (5 * sizeFactor) + spaceFactor;
       // float on2 = (5 * sizeFactor);// - spaceFactor;
       // float off2 = (5 * sizeFactor) + spaceFactor;
        dashPathEffect2 = new DashPathEffect(new float[] {on1, off1}, 1);
        System.out.println("DashedStyle.assignPath() : lineWidth: " + lineWidth);
        pathDashPathEffect = new PathDashPathEffect(makePathDash2(spaceFactor), 12, 5, PathDashPathEffect.Style.ROTATE);
        paintGroup.setPathEffect(pathDashPathEffect);
    }


    void onDrawAfterSettingsChanged(){
        assignPath();
    }

}


