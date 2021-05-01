package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public interface Style {


    void init(PaintGroup p, int brushSize);
    void setBrushSize(PaintGroup paintGroup, int brushSize);
    void onDraw(PaintGroup paintGroup);

}
