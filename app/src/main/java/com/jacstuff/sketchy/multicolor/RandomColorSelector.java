package com.jacstuff.sketchy.multicolor;


import android.util.Log;

import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.multicolor.pattern.RandomPattern;

import java.util.List;

public class RandomColorSelector implements ColorSelector {

    private MulticolorPattern pattern;
    private List<Integer> colors;

    public RandomColorSelector(){
        pattern = new RandomPattern();
    }

    @Override
    public void reset(){
        //do nothing
    }
    @Override
    public void set(int color){
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
    public void set(List<Integer> colors){
        this.colors = colors;
    }



    @Override
    public int getNextColor(){
        int index = pattern.getNextIndex(colors.size());
        return colors.get(index);

    }


    @Override
    public void resetCurrentIndex(){
     //do nothing
    }


    private void log(String msg){
        Log.i("RandomColorSelector", msg);
    }

}
