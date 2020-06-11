package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

public class OutlineStyle  extends AbstractStyle implements Style {


    @Override
    public void init(Paint paint, int brushSize) {
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(null);
    }

}
