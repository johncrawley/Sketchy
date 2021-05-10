package com.jacstuff.sketchy.multicolor;


import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import java.util.List;

public class MulticolorSelector implements ColorSelector {

    private List<Integer> colors;
    private MulticolorPattern currentMulticolorPattern;
    private List<MulticolorPattern> multicolorPatterns;
    private int currentPatternIndex = 0;

    public MulticolorSelector(List<MulticolorPattern> patterns){
        initPatterns(patterns);
    }


    private void initPatterns(List<MulticolorPattern> patterns){
        multicolorPatterns = patterns;
        currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
    }


    public int getNextColor(){
        int currentIndex = currentMulticolorPattern.getNextIndex(colors.size());
        return colors.get(currentIndex);
    }


    private boolean isMaxedOut(int index){
        return index >= multicolorPatterns.size() -1;
    }


    @Override
    public void resetCurrentIndex(){
        currentMulticolorPattern.resetIndex();
    }


    public void reset(){
        currentPatternIndex = 0;
        currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
    }


    public void set(List<Integer> inputList){
        colors = inputList;
    }

    @Override
    public void set(int color){
        // do nothing
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
}
