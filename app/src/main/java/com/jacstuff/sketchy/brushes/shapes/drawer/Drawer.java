package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.shapes.Brush;

public interface Drawer {

    void down(float x, float y, Paint paint);
    void move(float x, float y, Paint paint);
    void up(float x, float y, Paint paint);
    void drawKaleidoscopeSegment(float x, float y, Paint paint);
    void init();
    void setBrush(Brush brush);
    boolean isColorChangedOnDown();

}
