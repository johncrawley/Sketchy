package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;

import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ShadeColorSelector implements ColorSelector {

    private final Map<String, List<Integer>> shadesMap;
    private final List<String> ids;
    private final Random random;
    private final ColorSequenceControls colorSequenceControls;
    private final SequenceColorSelector sequenceColorSelector;
    private final StrobeCalculator strobeCalculator;
    private int currentIndex = 0;


    public ShadeColorSelector(MainViewModel viewModel){
        colorSequenceControls = viewModel.getColorSequenceControls();
        strobeCalculator = new StrobeCalculator(viewModel);
        this. sequenceColorSelector = new SequenceColorSelector(viewModel, true);
        shadesMap = new HashMap<>(30);
        ids = new ArrayList<>(30);
        random = new Random(System.currentTimeMillis());
    }


    @Override
    public int getNextColor(){
        if(ids.size() == 1){
            return sequenceColorSelector.getNextColor();
        }
        switch(colorSequenceControls.colorSequenceType){
            case FORWARDS:
                return getNextShadeForwards();
            case BACKWARDS:
                return getNextShadeBackwards();
            case RANDOM:
                return getNextRandomShade();
            case STROBE:
                return getNextStrobeShade();
        }
        return 0;
    }


    private int getNextShadeForwards(){
        currentIndex = currentIndex >= ids.size() -1 ? 0 : currentIndex +1;
        return getShade();
    }


    private int getNextShadeBackwards(){
        currentIndex =  currentIndex <= 0 ? ids.size()-1 : currentIndex -1;
        return getShade();
    }


    private int getNextRandomShade(){
        int oldIndex = currentIndex;
        while(currentIndex == oldIndex){
            currentIndex = random.nextInt(ids.size());

        }

        return getShadeFromCurrentIndexAtFixedBrightness();
       // return getRandomShade();
    }


    private int getRandomShade(){

        String shadeListKey = ids.get(currentIndex);
        List<Integer> shadesList =  shadesMap.get(shadeListKey);
        if(shadesList == null){
            return Color.BLACK;
        }
        // if we wanted to control multi-shade brightness with range controls then use this
        //int minIndex = getMinIndexOfSequence(colorSequenceControls.colorSequenceMinPercentage, shadesList);
        //int maxIndex = getMaxIndexOfSequence(colorSequenceControls.colorSequenceMaxPercentage, shadesList);
        int minIndex = 0;
        int maxIndex = shadesList.size() -1;
        int bound = Math.max(0, (maxIndex - minIndex)) + 1;
        int randomIndex = minIndex + random.nextInt(bound);
        return shadesList.get(randomIndex);
    }


    private int getShade(){
        List<Integer> shadesList =  shadesMap.get(ids.get(currentIndex));
        if(shadesList != null){

            int shadeIndex = getIndexFromPercentage(shadesList, colorSequenceControls.multiShadeBrightnessPercentage);
            return shadesList.get(shadeIndex);
        }
        return Color.BLACK;
    }


    private int getNextStrobeShade(){
        currentIndex = strobeCalculator.getNextStrobeIndex(currentIndex,
                getMinIndexOfSequence(colorSequenceControls.colorSequenceMinPercentage, ids),
                getMaxIndexOfSequence(colorSequenceControls.colorSequenceMaxPercentage, ids));

        String key = ids.get(currentIndex);
        List<Integer> shadesList =  shadesMap.get(key);
        return getShadeFromCurrentIndexAtFixedBrightness();
    }


    private int getShadeFromCurrentIndexAtFixedBrightness(){
        String shadeListKey = ids.get(currentIndex);
        List<Integer> shadesList =  shadesMap.get(shadeListKey);
        if(shadesList == null || doesKeyEqual(shadeListKey, Color.BLACK)){
            return Color.BLACK;
        }
        if(doesKeyEqual(shadeListKey, Color.WHITE)){
            return Color.WHITE;
        }
        int shadeIndex = getIndexFromPercentage(shadesList, colorSequenceControls.multiShadeBrightnessPercentage);
        return shadesList.get(shadeIndex);
    }


    private boolean doesKeyEqual(String key, int color){
        if(colorSequenceControls.isBlackAndWhitePreserved) {
            return Integer.parseInt(key) == color;
        }
        return false;
    }


    private int getIndexFromPercentage(List<?> list, int percentage){
        return (int)(((list.size()-1) / 100f)* percentage);
    }


    public int getMinIndexOfSequence(int seekBarColorRangeMinimum, List<?> colorList){
        int minSequenceIndex = getIndexFromPercentage(colorList, seekBarColorRangeMinimum);
        return Math.min(colorList.size()-2, minSequenceIndex);
    }


    public int getMaxIndexOfSequence(int seekBarColorRangeMaximum, List<?> colorList){
        int maxSequenceIndex = getIndexFromPercentage(colorList, seekBarColorRangeMaximum);
        return Math.max(1,maxSequenceIndex);
    }


    @Override
    public void add(int id, List<Integer> shades){
        String idStr = Integer.toString(id);
        shadesMap.put(idStr, shades);
        ids.add(idStr);
        assignOnlyColorToSequenceColorSelector();
        if(ids.size() == 1){
            currentIndex = 0;
        }
    }


    @Override
    public void remove(int id){
        String idStr = Integer.toString(id);
        shadesMap.remove(idStr);
        ids.remove(idStr);
        assignOnlyColorToSequenceColorSelector();
    }


    private void assignOnlyColorToSequenceColorSelector(){
        if(ids.size() == 1){
            sequenceColorSelector.setColorList(shadesMap.get(ids.get(0)));
        }
    }


    @Override
    public void updateRangeIndexes(){
       sequenceColorSelector.updateRangeIndexes();
    }


    @Override
    public void setColorList(List<Integer> colors){
        //do nothing
    }
    @Override
    public void reset(){
        //do nothing
    }
    @Override
    public void setColorList(int color){
        //do nothing
    }


    @Override
    public void resetCurrentIndex(){
        //do nothing
    }


}
