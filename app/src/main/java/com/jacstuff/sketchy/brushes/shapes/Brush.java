package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.styles.Style;

public interface Brush {

    void onTouchDown(float x, float y, Paint paint);
    void onTouchMove(float x, float y, Paint paint);
    void onTouchUp(float x, float y, Paint paint);
    void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint);
    void setBrushSize(int brushSize);
    void setStyle(BrushStyle style);
    void add(BrushStyle brushStyle, Style style);
    void notifyStrokeWidthChanged();
    BrushShape getBrushShape();
}
