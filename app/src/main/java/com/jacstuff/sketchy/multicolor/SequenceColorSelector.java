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


    public SequenceColorSelector(List<MulticolorPattern> patterns, MainViewModel viewModel){
      this.viewModel = viewModel;
    }

    public void initSequence(){
        viewModel.colorSequenceEndingShadeIndex = 0;
    }


    public int getNextColor(){
        //int index = currentMulticolorPattern.getNextIndex(colors.size());
       // int currentIndex = Math.min(index, viewModel.colorSequenceEndingShadeIndex );
       // return colors.get(currentIndex);
        return 0;
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

      //  sequenceMaxIndex = lastIndex / viewModel.

    }

    public int setMaxIndexOfSequence(int seekBarColorRangeMaximum, List<Integer> colorList){
        int lastIndex = colorList.size() -1;
        int maxSequenceIndex = (int)((lastIndex / 100f) * seekBarColorRangeMaximum);
        return Math.max(1,maxSequenceIndex);
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