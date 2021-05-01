package com.jacstuff.sketchy.brushes.shapes.line;

import android.graphics.Paint;

public interface LineDrawer {

    void draw(float x1, float y1, float x2, float y2, int brushSize, Paint paint);
}
