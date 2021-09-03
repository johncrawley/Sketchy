package com.jacstuff.sketchy.paintview.helpers.size;

public interface SizeSequence {

    int getNextBrushSize();
    void init();
    boolean hasSizeChanged();
    void reset();
}