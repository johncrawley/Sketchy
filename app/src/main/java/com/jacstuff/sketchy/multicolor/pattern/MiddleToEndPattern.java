package com.jacstuff.sketchy.multicolor.pattern;

public class MiddleToEndPattern extends AbstractMulticolorPattern implements MulticolorPattern {

    private int defaultIndex;


    public MiddleToEndPattern(String label){
        this.label = label;
    }


    public MiddleToEndPattern(){
    }


    @Override
    public int getNextIndex(int numberOfColors) {
        this.numberOfColors = numberOfColors;
        defaultIndex = numberOfColors / 2;
        if(currentIndex < defaultIndex){
            currentIndex = defaultIndex;
            return currentIndex;
        }
        currentIndex = currentIndex >= numberOfColors -1 ?  defaultIndex : currentIndex + 1;
        return currentIndex;
    }

    @Override
    public void resetIndex(){
        currentIndex = defaultIndex;
    }
}
