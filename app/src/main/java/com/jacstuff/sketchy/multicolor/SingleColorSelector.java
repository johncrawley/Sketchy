package com.jacstuff.sketchy.multicolor;


import java.util.List;

public class SingleColorSelector implements ColorSelector {

    private  int color;


    @Override
    public void setColorList(int color){
        this.color = color;
    }


    @Override
    public int getNextColor() {
        return color;
    }



    @Override
    public void resetCurrentIndex(){
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
