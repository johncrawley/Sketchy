package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;
import android.util.Log;

import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.multicolor.pattern.RandomPattern;

import java.util.List;

public class RandomColorSelector implements ColorSelector {

    private MulticolorPattern pattern;
    private List<Color> colors;

    public RandomColorSelector(){
        pattern = new RandomPattern();
    }

    @Override
    public void reset(){
        //do nothing
    }
    @Override
    public void set(Color color){
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
    public void set(List<Color> colors){
        this.colors = colors;
    }



    @Override
    public int getNextColor(){
        int index = pattern.getNextIndex(colors.size());
        return colors.get(index).toArgb();

    }


    @Override
    public void resetCurrentIndex(){
     //do nothing
    }


    private void log(String msg){
        Log.i("RandomColorSelector", msg);
    }

}
