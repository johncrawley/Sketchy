package com.jacstuff.sketchy.multicolor;


import android.graphics.Color;

import java.util.List;

public class SingleColorSelector implements ColorSelector {

    private  int color = Color.BLACK;
    int counter = 0;
    int limit = 40;

    @Override
    public void setColorList(int color){
        this.color = color;
    }


    @Override
    public int getNextColor() {
        counter++;
        if(counter > limit){
            counter = 0;
            color = color == Color.BLACK ? Color.GREEN : Color.BLUE;
        }
        var colorName = color == Color.BLUE ? "BLUE" : color == Color.GREEN ? " GREEN " : "some other color";
        log("getNextColor() " + colorName);
        return color;
    }

    private void log(String msg){
        System.out.println("^^^ SingleColorSelector: " + msg);
    }


    @Override
    public void updateRangeIndexes() {
        //do nothing
    }


    @Override
    public void resetCurrentIndex(){
        //do nothing
    }


    @Override
    public void removeAllBut (int id){
        //do nothing
    }


    @Override
    public void reset() {
        //do nothing
    }


    @Override
    public void setColorList(List<Integer> colorList) {
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
