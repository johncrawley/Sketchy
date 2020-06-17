package com.jacstuff.sketchy.brushes.shapes;

import com.jacstuff.sketchy.brushes.BrushStyle;

public interface Brush {

    void onTouchDown(float x, float y);
    void onTouchMove(float x, float y);
    void onTouchUp(float x, float y);
    void setBrushSize(int brushSize);
    void setStyle(BrushStyle style);
}
