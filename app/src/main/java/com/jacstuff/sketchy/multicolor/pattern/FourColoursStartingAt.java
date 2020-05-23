package com.jacstuff.sketchy.multicolor.pattern;

import java.util.ArrayList;
import java.util.List;

public class FourColoursStartingAt extends  AbstractMulticolorPattern implements MulticolorPattern {

    private int startingIndex;
    private List<Integer> indexes;
    private final int MAX_INCREMENTS = 4;
    private int listIndex;

    public FourColoursStartingAt(int startingIndex){

        this.startingIndex = startingIndex;
        currentIndex = startingIndex;
        indexes = new ArrayList<>(MAX_INCREMENTS);
    }

    private void fillList(int startingIndex, int numberOfColors){

        int increment = numberOfColors / MAX_INCREMENTS;
        int index = startingIndex;
        indexes.add(startingIndex);
        while(indexes.size() <= MAX_INCREMENTS){
            indexes.add(index);
            index = (index + increment) % (numberOfColors -1);
        }
    }


    @Override
    public int getNextIndex(int numberOfColors) {

        if(indexes.isEmpty()){
            fillList(startingIndex, numberOfColors);
        }
        if (listIndex >= indexes.size()) {
            listIndex = 0;
        }
        currentIndex = indexes.get(listIndex);
        listIndex++;
        return currentIndex;
    }


    public void resetIndex(){
        currentIndex = startingIndex;
        listIndex = 0;
    }
}