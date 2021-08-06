package com.jacstuff.sketchy.multicolor.pattern;

import java.util.Random;

public class RandomPattern extends AbstractMulticolorPattern implements MulticolorPattern {

    private final Random random;

    public RandomPattern(){
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public int getNextIndex(int numberOfColors) {
        return random.nextInt(numberOfColors);
    }

    @Override
    public void resetIndex() {
        // do nothing
    }
}
