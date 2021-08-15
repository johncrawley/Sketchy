package com.jacstuff.sketchy.multicolor.pattern;

public class StrobePattern extends AbstractMulticolorPattern implements MulticolorPattern {


    private enum Direction { FORWARDS, BACKWARDS}
    private Direction indexDirection = Direction.FORWARDS;


    public StrobePattern(String label){
        this();
        this.label = label;
    }


    public StrobePattern(){
        currentIndex = getStartingIndex();
    }

    private int getStartingIndex(){
        return numberOfColors / 2;
    }


    public int getNextIndex(int numberOfColors){
        this.numberOfColors = numberOfColors;
        if(isIndexAtEdgeOfBounds()){
            changeDirection();
        }
        updateIndex();
        return currentIndex;
    }


    public void resetIndex(){
        indexDirection = Direction.FORWARDS;
        currentIndex = getStartingIndex();
    }


    private boolean isIndexAtEdgeOfBounds(){
        return (currentIndex == 0 && indexDirection == Direction.BACKWARDS) ||
                (currentIndex == numberOfColors -1 && indexDirection == Direction.FORWARDS);
    }


    private void changeDirection(){
        if(indexDirection == Direction.FORWARDS){
            indexDirection = Direction.BACKWARDS;
            return;
        }
        indexDirection = Direction.FORWARDS;
    }


    private void updateIndex(){
        if (indexDirection == Direction.FORWARDS) {
            currentIndex++;
            return;
        }
        currentIndex--;
    }

}



