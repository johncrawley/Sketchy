package com.jacstuff.sketchy.multicolor.pattern;

public class MiddleToStartPattern extends AbstractMulticolorPattern implements MulticolorPattern {

    private int defaultIndex;


    public MiddleToStartPattern(String label){
        this.label = label;
    }


    public MiddleToStartPattern(){

    }

    @Override
    public int getNextIndex(int numberOfColors) {
        this.numberOfColors = numberOfColors;
        defaultIndex = numberOfColors / 2;
        currentIndex = currentIndex == 0 ?  defaultIndex : currentIndex - 1;
        return currentIndex;
    }

    @Override
    public void resetIndex(){
        currentIndex = defaultIndex;
    }
}