package com.jacstuff.sketchy.multicolor.pattern;

import java.util.concurrent.ThreadLocalRandom;

public class RandomPattern extends AbstractMulticolorPattern implements MulticolorPattern {


    public RandomPattern(String label){
        this.label = label;
    }

    public RandomPattern(){
    }

    @Override
    public int getNextIndex(int numberOfColors) {
        return ThreadLocalRandom.current().nextInt(numberOfColors -1);
    }

    @Override
    public void resetIndex() {
        // do nothing
    }
}
