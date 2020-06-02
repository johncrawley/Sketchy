package com.jacstuff.sketchy.multicolor.pattern;

public class OddNumbersPattern extends  AbstractMulticolorPattern implements MulticolorPattern {


    public OddNumbersPattern(String label){
        this.label = label;
    }


    public OddNumbersPattern(){

    }


    @Override
    public int getNextIndex(int numberOfColors) {
        currentIndex = currentIndex >= numberOfColors -2 ? 1 : currentIndex + 2;
        return currentIndex;
    }


    public void resetIndex(){
        currentIndex = 1;
    }
}