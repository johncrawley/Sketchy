package com.jacstuff.sketchy.multicolor.pattern;

public interface MulticolorPattern {
    int getNextIndex(int numberOfColors);
    void resetIndex();
    String getLabel();

}
