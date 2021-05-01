package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class FillStyle  extends AbstractStyle implements Style {

    @Override
    public void init(PaintGroup p, int brushSize) {
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setPathEffect(null);
    }

}
