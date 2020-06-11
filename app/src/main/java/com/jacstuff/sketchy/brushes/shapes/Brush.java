package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.BrushStyle;

public interface Brush {

    void onTouchDown(float x, float y);
    void onTouchMove(float x, float y);
    void onTouchUp(float x, float y);
    void setBrushSize(int brushSize);
    void setStyle(BrushStyle style);
}
