package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public interface Brushable {


    void onTouchDown(float x, float y, PaintGroup paintGroup);
    void onTouchMove(float x, float y, PaintGroup paintGroup);
    void onTouchUp(float x, float y, PaintGroup paintGroup);

    void setBrushSize(int brushSize);
}
