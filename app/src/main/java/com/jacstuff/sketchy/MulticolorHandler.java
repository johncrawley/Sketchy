package com.jacstuff.sketchy;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

class MulticolorHandler {

    private List<Color> colors;
    private boolean isEnabled = false;
    private int currentIndex;
    private enum Direction { FORWARDS, BACKWARDS}
    private Direction indexDirection = Direction.FORWARDS;


    int getNextColor(){
        update();
        return colors.get(currentIndex).toArgb();
    }

    boolean isEnabled(){
        return isEnabled;
    }

    void resetIndex(){
        currentIndex = 0;
    }

    void disable(){
        isEnabled = false;
    }

    void enable(List<Color> inputList){
        isEnabled = true;
        colors = new ArrayList<>(inputList);
    }

    private void update(){
        udpateIndex();
        if(indexExceedsBounds()){
            changeDirection();
            udpateIndex();
        }
    }

    private boolean indexExceedsBounds(){
        return currentIndex < 0 || currentIndex >= colors.size();
    }

    private void changeDirection(){
        if(indexDirection == Direction.FORWARDS){
            indexDirection = Direction.BACKWARDS;
            return;
        }
        indexDirection = Direction.FORWARDS;
    }


    private void udpateIndex(){
        if(indexDirection == Direction.FORWARDS){
            currentIndex++;
            return;
        }
        currentIndex--;
    }

}
