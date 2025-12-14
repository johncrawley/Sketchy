package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.Brushable;
import com.jacstuff.sketchy.easel.Easel;

public interface ShapeDrawer {

    void down(PointF point, Easel easel);
    void move(PointF point, Easel easel);
    void up(PointF point, Easel easel);
    void setBrushShape(Brushable brushShape);
}
