package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;

import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import java.util.List;

public class MulticolorSelector implements ColorSelector {

    private List<Color> colors;
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
        return colors.get(currentIndex).toArgb();
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


    public void set(List<Color> inputList){
        colors = inputList;
    }

    @Override
    public void set(Color color){
        // do nothing
    }

    @Override
    public void nextPattern(){
        currentPatternIndex = isMaxedOut(currentPatternIndex) ? 0 : currentPatternIndex + 1;
        currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
        currentMulticolorPattern.resetIndex();
    }



    private int getFirstColorInList(List<Color> colorList){
        if(colorList == null){
            return 0;
        }
        return colorList.get(0).toArgb();
    }



}
