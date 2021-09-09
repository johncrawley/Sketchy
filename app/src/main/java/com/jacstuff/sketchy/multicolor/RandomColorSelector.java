package com.jacstuff.sketchy.multicolor;


import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.multicolor.pattern.RandomPattern;

import java.util.List;

public class RandomColorSelector implements ColorSelector {

    private final MulticolorPattern pattern;
    private List<Integer> colors;

    public RandomColorSelector(){
        pattern = new RandomPattern();
    }


    @Override
    public void setColorList(List<Integer> colors){
        this.colors = colors;
    }


    @Override
    public int getNextColor(){
        int index = pattern.getNextIndex(colors.size());
        return colors.get(index);
    }


    @Override
    public void reset(){
        //do nothing
    }
    @Override
    public void setColorList(int color){
        //do nothing
    }

    @Override
    public void nextPattern(){
        //do nothing
    }

    @Override
    public String getCurrentPatternLabel(){
        return "";
    }


    @Override
    public void resetCurrentIndex(){
     //do nothing
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
