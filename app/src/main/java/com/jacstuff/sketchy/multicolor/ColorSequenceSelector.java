package com.jacstuff.sketchy.multicolor;


import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class ColorSequenceSelector implements ColorSelector {

    private List<Integer> colors;
    private MulticolorPattern currentMulticolorPattern;
    private List<MulticolorPattern> multicolorPatterns;
    private int currentPatternIndex = 0;
    private int lastIndex;


    public ColorSequenceSelector(List<MulticolorPattern> patterns){
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


    public void set(List<Integer> inputList){
        colors = new ArrayList<>(inputList);
        printColors();
        lastIndex = colors.size()-1;
    }

    private void printColors(){
        System.out.println("ColorSequenceSelector : print Colors: ********************");
        StringBuilder str = new StringBuilder();
        for(Integer color : colors){
            str.append(getColorStr(color));
            str.append( " ");
        }
        System.out.println(str.toString());
    }

    private String getColorStr(int color){
        String colorStr = "#";
        colorStr +=  ColorUtils.getComponentFrom(color, ColorUtils.Rgb.RED);
        colorStr += "_";
        colorStr +=  ColorUtils.getComponentFrom(color, ColorUtils.Rgb.GREEN);
        colorStr += "_";
        colorStr +=  ColorUtils.getComponentFrom(color, ColorUtils.Rgb.BLUE);
        return colorStr;
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
    public void set(int color){
        // do nothing
    }

}
