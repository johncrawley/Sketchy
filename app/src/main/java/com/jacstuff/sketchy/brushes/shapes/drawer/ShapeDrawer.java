package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.Brushable;

public interface ShapeDrawer {

    void down(PointF point, Paint paint);
    void move(PointF point, Paint paint);
    void up(PointF point, Paint paint);
    void setBrushShape(Brushable brushShape);
}
