package com.jacstuff.sketchy.multicolor.pattern;

public class FourColoursStartingAt extends  AbstractMulticolorPattern implements MulticolorPattern {

    private int startingIndex;
    private int currentIncCount;

    public FourColoursStartingAt(int startingIndex){
        this.startingIndex = startingIndex;
    }

    @Override
    public int getNextIndex(int numberOfColors) {
        final int MAX_INCREMENTS = 4;
        int increment = numberOfColors / MAX_INCREMENTS;
        if(currentIncCount == MAX_INCREMENTS){
            resetIndex();
        }
        else {
            currentIndex = (currentIndex + increment) % (numberOfColors - 1);
        }
        currentIncCount++;
        return currentIndex;
    }


    public void resetIndex(){
        currentIndex = startingIndex;
        currentIncCount = 0;
    }
}