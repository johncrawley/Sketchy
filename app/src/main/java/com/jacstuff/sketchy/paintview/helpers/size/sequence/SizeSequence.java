package com.jacstuff.sketchy.paintview.helpers.size.sequence;

public interface SizeSequence {

    int getNextBrushSize(float x, float y);
    void onTouchDown(float x, float y);
    int getBrushSize();
    void init();
    boolean hasSizeChanged();
    void reset();
}
