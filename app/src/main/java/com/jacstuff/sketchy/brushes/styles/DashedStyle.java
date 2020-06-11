package com.jacstuff.sketchy.brushes.styles;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class DashedStyle extends AbstractStyle implements Style {

    DashPathEffect dashPathEffect;

    public DashedStyle(){
        float onStroke = 15;
        float offStroke = 36;
        dashPathEffect = new DashPathEffect(new float[] {onStroke, offStroke}, 0);
    }

    @Override
    public void init(Paint p, int brushSize) {
        p.setStrokeWidth(1);
        p.setStyle(Paint.Style.STROKE);
        p.setPathEffect(dashPathEffect);
    }

}


