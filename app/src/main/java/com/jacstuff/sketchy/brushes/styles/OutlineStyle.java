package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class OutlineStyle  extends AbstractStyle implements Style {


    @Override
    public void init(PaintGroup paint, int brushSize) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(null);
    }

}
