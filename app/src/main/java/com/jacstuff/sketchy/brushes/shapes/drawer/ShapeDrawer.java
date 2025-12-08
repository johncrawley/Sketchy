package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.shapes.Brushable;

public interface ShapeDrawer {

    void down(float x, float y, Paint paint);
    void move(float x, float y, Paint paint);
    void up(float x, float y, Paint paint);
    void setBrushShape(Brushable brushShape);
}
