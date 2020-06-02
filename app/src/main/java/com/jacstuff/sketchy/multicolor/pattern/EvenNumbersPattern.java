package com.jacstuff.sketchy.multicolor.pattern;

public class EvenNumbersPattern extends  AbstractMulticolorPattern implements MulticolorPattern {


    public EvenNumbersPattern(String label){
        this.label = label;
    }


    public EvenNumbersPattern(){
    }


    @Override
    public int getNextIndex(int numberOfColors) {
        currentIndex = currentIndex >= numberOfColors -2 ? 0 : currentIndex + 2;
        return currentIndex;
    }




    public void resetIndex(){
        currentIndex = 0;
    }
}