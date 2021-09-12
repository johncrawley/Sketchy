package com.jacstuff.sketchy.multicolor.legacy;


import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;

import java.util.ArrayList;
import java.util.List;

public class LegacyColorSequenceSelector implements ColorSelector {

    private List<Integer> colors;
    private MulticolorPattern currentMulticolorPattern;
    private List<MulticolorPattern> multicolorPatterns;
    private int currentPatternIndex = 0;
    private int lastIndex;


    public LegacyColorSequenceSelector(List<MulticolorPattern> patterns){
        initPatterns(patterns);
    }


    private void initPatterns(List<MulticolorPattern> patterns){
        multicolorPatterns = patterns;
        currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
    }


    public int getNextColor(){
        int index = currentMulticolorPattern.getNextIndex(colors.size());
        int currentIndex = Math.min(index, lastIndex);
        return colors.get(currentIndex);
    }


    private boolean isMaxedOut(int index){
        return index >= multicolorPatterns.size() -1;
    }


    @Override
    public void resetCurrentIndex(){
        currentMulticolorPattern.resetIndex();
    }


    @Override
    public void reset(){
        currentPatternIndex = 0;
        currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
    }


    public void setColorList(List<Integer> inputList){
        colors = new ArrayList<>(inputList);
        lastIndex = colors.size()-1;
    }


    @Override
    public void nextPattern(){
        currentPatternIndex = isMaxedOut(currentPatternIndex) ? 0 : currentPatternIndex + 1;
        currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
        currentMulticolorPattern.resetIndex();
    }


    @Override
    public String getCurrentPatternLabel(){
        return (currentPatternIndex + 1) + ": "  +currentMulticolorPattern.getLabel();
    }


    @Override
    public void add(int id, List<Integer> shades){
        //do nothing
    }


    @Override
    public void remove(int id){
       //do nothing
    }


    @Override
    public void setColorList(int color){
        // do nothing
    }

}
