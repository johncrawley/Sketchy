package com.jacstuff.sketchy.paintview.helpers.size;

public interface SizeSequence {

    int getNextBrushSize();
    int getBrushSize();
    void init();
    boolean hasSizeChanged();
    void reset();
}
