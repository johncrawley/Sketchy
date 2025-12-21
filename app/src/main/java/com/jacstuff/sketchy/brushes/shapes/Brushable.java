package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;

public interface Brushable {

    BrushShape getBrushShape();
    void generatePath(PointF point);
    void draw(PointF point, Canvas canvas, Paint paint);
    void setBrushSize(int brushSize);
    DrawerFactory.Type getDrawerType();
}
