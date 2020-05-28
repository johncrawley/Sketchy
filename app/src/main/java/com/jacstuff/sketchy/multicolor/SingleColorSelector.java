package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;

import java.util.List;

public class SingleColorSelector implements ColorSelector {

    private  int color;
    @Override
    public void reset() {

    }

    @Override
    public void set(List<Color> colorList) {

    }


    @Override
    public void set(Color color){
        this.color = color.toArgb();
    }

    @Override
    public void resetCurrentIndex(){
        //do nothing
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
