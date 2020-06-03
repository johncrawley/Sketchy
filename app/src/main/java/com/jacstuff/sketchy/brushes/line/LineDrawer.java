package com.jacstuff.sketchy.brushes.line;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface LineDrawer {

    void draw(float x1, float y1, float x2, float y2, int brushSize);
    void initStrokeWidth(int brushSize);
}
