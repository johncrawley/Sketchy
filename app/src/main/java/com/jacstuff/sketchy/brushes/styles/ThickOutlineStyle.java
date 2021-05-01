package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class ThickOutlineStyle  extends AbstractStyle implements Style {


    @Override
    public void init(PaintGroup paint, int brushSize) {
        setBrushSize(paint, brushSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(null);
    }

    @Override
    public void setBrushSize(PaintGroup paint, int brushSize) {
    }

}
