package com.jacstuff.sketchy.brushes.shapes;

import com.jacstuff.sketchy.brushes.Easel;

public interface Brushable {


    void onTouchDown(float x, float y, Easel easel);
    void onTouchMove(float x, float y, Easel easel);
    void onTouchUp(float x, float y, Easel easel);

    void setBrushSize(int brushSize);
}
