package com.jacstuff.sketchy.multicolor.pattern;

import java.util.concurrent.ThreadLocalRandom;

public class FirstToLastPattern extends  AbstractMulticolorPattern implements MulticolorPattern {

    @Override
    public int getNextIndex(int numberOfColors) {
        currentIndex = currentIndex >= numberOfColors -1 ? 0 : currentIndex + 1;
        return currentIndex;

    }


    public void resetIndex(){
        currentIndex = 0;
    }
}
