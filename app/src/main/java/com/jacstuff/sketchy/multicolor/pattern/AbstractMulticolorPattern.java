package com.jacstuff.sketchy.multicolor.pattern;

abstract class AbstractMulticolorPattern {
    int numberOfColors, currentIndex;
    String label = "";

    public String getLabel(){
        return this.label;
    }
}
