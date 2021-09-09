package com.jacstuff.sketchy.multicolor;

import com.jacstuff.sketchy.viewmodel.ControlsHolder;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceColorSelector implements ColorSelector {

    private List<Integer> colors;
    private int currentPatternIndex = 0;
    private int lastIndex;
    private ControlsHolder viewModel;
    private ColorSequenceControls colorSequenceControls;
    private int currentIndex;
    private int resetIndex;
    private Random random;


    int sequenceMaxIndex;
    int sequenceMinIndex;



    int direction = 1;
    int changedDirectionCount = 0;



    public SequenceColorSelector(ControlsHolder viewModel){
        this.colorSequenceControls = viewModel.getColorSequenceControls();
      this.viewModel = viewModel;
      random = new Random(System.currentTimeMillis());
    }

    public void initSequence(){
    }


    public int getNextColor(){
        log("entered getNextColor(), colors size : " + colors.size());
        switch (colorSequenceControls.colorSequenceType){
            case FORWARDS:
                calculateNextForwardIndex();
                break;
            case BACKWARDS:
                calculateNextBackwardsIndex();
                break;
            case STROBE:
                calculateNextStrobeIndex();
                break;
            case RANDOM:
                calculateNextRandomIndex();
                break;
        }
        return colors.get(currentIndex);
    }


    private void log(String msg){
        System.out.println("SequenceColorSelector: " + msg);
    }


    public void calculateNextForwardIndex(){
        if(isAtEndOfForwardsSequence()){
            currentIndex = currentIndex == sequenceMinIndex ?
                    sequenceMaxIndex :
                    colorSequenceControls.doesRepeat ? sequenceMinIndex : sequenceMaxIndex;
            return;
        }
       currentIndex += colorSequenceControls.skippedShades;
    }


    private void calculateNextBackwardsIndex(){
        if(isAtEndOfBackwardsSequence()){
            currentIndex = currentIndex == sequenceMaxIndex ?
                    sequenceMinIndex :
                    colorSequenceControls.doesRepeat ? sequenceMaxIndex : currentIndex;
        }
        currentIndex -= colorSequenceControls.skippedShades;
    }


    private void calculateNextStrobeIndex(){
        if((direction == 1 && isAtEndOfForwardsSequence())
                || (direction == -1 && isAtEndOfBackwardsSequence())){
            direction *= -1;
            changedDirectionCount++;
        }

        if(!colorSequenceControls.doesRepeat
                && direction == 1
                && changedDirectionCount > 1
                && currentIndex + colorSequenceControls.skippedShades > resetIndex){
            currentIndex = resetIndex;
            return;
        }
        currentIndex += direction * colorSequenceControls.skippedShades;
    }


    private void calculateNextRandomIndex(){
        currentIndex = sequenceMinIndex + random.nextInt(sequenceMaxIndex - sequenceMinIndex);
    }


    private boolean isAtEndOfForwardsSequence(){
        return currentIndex + colorSequenceControls.skippedShades > sequenceMaxIndex;
    }


    private boolean isAtEndOfBackwardsSequence(){
        return currentIndex - colorSequenceControls.skippedShades < sequenceMinIndex;
    }


    private boolean isMaxedOut(int index){
        //return index >= multicolorPatterns.size() -1;
        return false;
    }


    @Override
    public void resetCurrentIndex(){

    }


    @Override
    public void reset(){
        currentPatternIndex = resetIndex;
        changedDirectionCount = 0;
        direction = 1;
    }


    public void setColorList(List<Integer> inputList){
        colors = new ArrayList<>(inputList);
        lastIndex = colors.size()-1;
        updateRangeIndexes();
    }



    public void setResetIndex(int resetValue){
        this.resetIndex = resetValue;
    }


    private void updateRangeIndexes(){
        log("Entered updateRangeIndexes()");
        sequenceMaxIndex = getMaxIndexOfSequence(colorSequenceControls.colorSequenceMaxPercentage, colors);
        int minIndex = getMinIndexOfSequence(colorSequenceControls.colorSequenceMinPercentage, colors);
        sequenceMinIndex = minIndex < sequenceMaxIndex ? minIndex : sequenceMaxIndex -1;

        resetIndex = Math.max(resetIndex, sequenceMinIndex);
        resetIndex = Math.min(resetIndex, sequenceMaxIndex);
        currentIndex = resetIndex;
        log("updateRangeIndexes() min and max Indexes: " + sequenceMinIndex + "," +  sequenceMaxIndex);
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


    public int getCurrentIndex(){
        return currentIndex;
    }



    @Override
    public void nextPattern(){
        currentPatternIndex = isMaxedOut(currentPatternIndex) ? 0 : currentPatternIndex + 1;
        //currentMulticolorPattern = multicolorPatterns.get(currentPatternIndex);
    }


    @Override
    public String getCurrentPatternLabel(){
        return (currentPatternIndex + 1) + ": " ;
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
    public void setColorList(int color){
        // do nothing
    }

}