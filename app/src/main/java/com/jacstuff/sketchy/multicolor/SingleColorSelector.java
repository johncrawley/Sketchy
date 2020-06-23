package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;

import java.util.List;

public class SingleColorSelector implements ColorSelector {

    private  int color;
    @Override
    public void reset() {

    }

    @Override
    public void set(List<Integer> colorList) {

    }


    @Override
    public void set(int color){
        this.color = color;
    }

    @Override
    public void resetCurrentIndex(){
        //do nothing
    }

    @Override
    public String getCurrentPatternLabel(){
        return "";
    }

    @Override
    public int getNextColor() {
        return color;
    }


    @Override
    public void nextPattern(){
        //do nothing
    }

}
