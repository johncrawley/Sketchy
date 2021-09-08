package com.jacstuff.sketchy.multicolor;

import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class SequenceColorSelector implements ColorSelector {

    private List<Integer> colors;
    private MulticolorPattern currentMulticolorPattern;
    private int currentPatternIndex = 0;
    private int lastIndex;
    private MainViewModel viewModel;
    private int currentIndex;
    private int startingIndex;



    public SequenceColorSelector(List<MulticolorPattern> patterns, MainViewModel viewModel){
      this.viewModel = viewModel;
    }

    public void initSequence(){
        viewModel.colorSequenceMaxValue = 0;
    }


    public int getNextColor(){
        //int index = currentMulticolorPattern.getNextIndex(colors.size());
       // int currentIndex = Math.min(index, viewModel.colorSequenceEndingShadeIndex );
       // return colors.get(currentIndex);
        switch (viewModel.colorSequenceType){
            case FORWARDS:
                return colors.get(getNextForwardsIndex());
            case BACKWARDS:
                return colors.get(getNextBackwardsIndex());
        }
        return 0;
    }


    private int getNextForwardsIndex(){
        if(isAtEndOfForwardsSequence()){
            return currentIndex == sequenceMinIndex ?
                    sequenceMaxIndex :
                    viewModel.doesColorSequenceRepeat ? sequenceMinIndex : currentIndex;
        }
        return currentIndex + viewModel.colorSequenceSkippedShades;
    }


    private int getNextBackwardsIndex(){
        if(isAtEndOfForwardsSequence()){
            return currentIndex == sequenceMinIndex ?
                    sequenceMaxIndex :
                    viewModel.doesColorSequenceRepeat ? sequenceMinIndex : currentIndex;
        }
        return currentIndex + viewModel.colorSequenceSkippedShades;
    }




    private boolean isAtEndOfForwardsSequence(){
        return currentIndex + viewModel.colorSequenceSkippedShades > sequenceMaxIndex;
    }


    private boolean isMaxedOut(int index){

        //return index >= multicolorPatterns.size() -1;
        return false;
    }


    @Override
    public void resetCurrentIndex(){
        currentMulticolorPattern.resetIndex();
    }


    @Override
    public void reset(){
        currentPatternIndex = 0;
       // currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
    }

    int sequenceMaxIndex;
    int sequenceMinIndex;

    public void set(List<Integer> inputList){
        colors = new ArrayList<>(inputList);
        lastIndex = colors.size()-1;
        updateRangeIndexes();

    }


    private void updateRangeIndexes(){
        sequenceMaxIndex = getMaxIndexOfSequence(viewModel.colorSequenceMaxValue, colors);
        int minIndex = getMinIndexOfSequence(viewModel.colorSequenceMinValue, colors);
        sequenceMinIndex = minIndex < sequenceMaxIndex ? minIndex : sequenceMaxIndex -1;
    }


    public int getMaxIndexOfSequence(int seekBarColorRangeMaximum, List<Integer> colorList){
        int lastIndex = colorList.size() -1;
        int maxSequenceIndex = (int)((lastIndex / 100f) * seekBarColorRangeMaximum);
        return Math.max(1,maxSequenceIndex);
    }


    public int getMinIndexOfSequence(int seekBarColorRangeMaximum, List<Integer> colorList){
        int lastIndex = colorList.size() -1;
        int minSequenceIndex = (int)((lastIndex / 100f) * seekBarColorRangeMaximum);
        return Math.min(colorList.size()-2, minSequenceIndex);
    }



    @Override
    public void nextPattern(){
        currentPatternIndex = isMaxedOut(currentPatternIndex) ? 0 : currentPatternIndex + 1;
        //currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
        currentMulticolorPattern.resetIndex();
    }


    @Override
    public String getCurrentPatternLabel(){
        return (currentPatternIndex + 1) + ": "  +currentMulticolorPattern.getLabel();
    }


    @Override
    public void add(int id, List<Integer> shades){
        //do nothing
    }


    @Override
    public void remove(int id){
        //do nothing
    }


    @Override
    public void set(int color){
        // do nothing
    }

}