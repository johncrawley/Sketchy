package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.Easel;

public interface Brushable {


    void onTouchDown(PointF point, Easel easel);
    void onTouchMove(PointF point, Easel easel);
    void onTouchUp(PointF point, Easel easel);

    void setBrushSize(int brushSize);
}
