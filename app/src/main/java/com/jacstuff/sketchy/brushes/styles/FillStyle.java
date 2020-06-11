package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

public class FillStyle  extends AbstractStyle implements Style {


    @Override
    public void init(Paint p, int brushSize) {
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setPathEffect(null);
        p.setStrokeWidth(1);
    }

}
