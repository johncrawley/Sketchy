package com.jacstuff.sketchy.multicolor.pattern;


import java.util.ArrayList;
import java.util.List;

public class FourColoursStartingAt extends  AbstractMulticolorPattern implements MulticolorPattern {

    private int startingIndex;
    private List<Integer> savedIndexes;
    private final int MAX_INCREMENTS = 4;
    private int listIndex;


    public FourColoursStartingAt(int startingIndex){
        this.startingIndex = startingIndex;
        currentIndex = startingIndex;
        savedIndexes = new ArrayList<>(MAX_INCREMENTS);
    }


    private void calculateAndSaveIndexesIfEmpty(int startingIndex){
        if(!savedIndexes.isEmpty()){
            return;
        }

        int index = startingIndex;
        int increment = numberOfColors / MAX_INCREMENTS;
        while(savedIndexes.size() < MAX_INCREMENTS){
            savedIndexes.add(index);
            index = incrementBy(index,increment);
        }
    }


    private int incrementBy(int index, int increment){
        return (index + increment) % (numberOfColors -1);
    }

    private void resetIndexIfOutOfBounds(){
        if (listIndex >= savedIndexes.size()) {
            listIndex = 0;
        }
    }


    @Override
    public int getNextIndex(int numberOfColors) {
        this.numberOfColors = numberOfColors;
        calculateAndSaveIndexesIfEmpty(startingIndex);
        resetIndexIfOutOfBounds();
        currentIndex = savedIndexes.get(listIndex++);
        return currentIndex;
    }

    @Override
    public void resetIndex(){
        currentIndex = startingIndex;
        listIndex = 0;
    }
}