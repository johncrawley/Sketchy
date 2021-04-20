package com.jacstuff.sketchy.brushes.styles;

import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;

public class DashedStyle extends AbstractStyle implements Style {

    DashPathEffect dashPathEffect, dashPathEffect2;
    PathDashPathEffect pathDashPathEffect;
    private CornerPathEffect cornerPathEffect;

    public DashedStyle(){
        float onStroke = 15;
        float offStroke = 36;
        dashPathEffect = new DashPathEffect(new float[] {onStroke, offStroke}, 0);
       pathDashPathEffect = new PathDashPathEffect(makePathDash(), 12, 5, PathDashPathEffect.Style.MORPH);
        cornerPathEffect = new CornerPathEffect(10);
        dashPathEffect2 = new DashPathEffect(new float[] {20, 10, 15, 15}, 3);
    }

    @Override
    public void init(Paint p, int brushSize) {
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.STROKE);
        p.setPathEffect(dashPathEffect2);
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

}


