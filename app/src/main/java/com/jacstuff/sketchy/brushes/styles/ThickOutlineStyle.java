package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

public class ThickOutlineStyle  extends AbstractStyle implements Style {



    @Override
    public void init(Paint paint, int brushSize) {
        setBrushSize(paint, brushSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(null);
    }

    @Override
    public void setBrushSize(Paint paint, int brushSize) {
    }

}
