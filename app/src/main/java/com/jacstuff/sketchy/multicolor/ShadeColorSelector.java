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
    private final SequenceColorSelector colorSelectorForSingleColorMultiShades;
    private final StrobeCalculator strobeCalculatorForMultiColor;
    private int currentIndex = 0;
    private boolean isAtLastIndex;
    private final BlendCalculator blendCalculator;
    private final MainViewModel viewModel;


    public ShadeColorSelector(MainViewModel viewModel){
        this.viewModel = viewModel;
        colorSequenceControls = viewModel.getColorSequenceControls();
        strobeCalculatorForMultiColor = new StrobeCalculator(viewModel);
        blendCalculator = new BlendCalculator(viewModel);
        this.colorSelectorForSingleColorMultiShades = new SequenceColorSelector(viewModel, true, true);
        int initialCapacity = 30;
        shadesMap = new HashMap<>(initialCapacity);
        ids = new ArrayList<>(initialCapacity);
        random = new Random(System.currentTimeMillis());
    }


    @Override
    public int getNextColor(){
        if(ids.size() == 1){
            return colorSelectorForSingleColorMultiShades.getNextColor();
        }
        if(viewModel.isRandomColorDisabled()){
            return calculateNextBlendColor();
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
            case BLEND:
                return calculateNextBlendColor();
        }
        return 0;
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
        resetCurrentIndex();
    }


    @Override
    public void remove(int id){
        String idStr = Integer.toString(id);
        shadesMap.remove(idStr);
        ids.remove(idStr);
        assignOnlyColorToSequenceColorSelector();
        resetCurrentIndex();
    }


    private int getNextBlendTarget(){
        return getNextShadeForwards();
    }


    private int calculateNextBlendColor(){
        int nextBlendColor = blendCalculator.getNextShade();
        if(blendCalculator.hasReachedTargetShade()){
            blendCalculator.setTargetShade(getNextBlendTarget());
        }
        return nextBlendColor;
    }


    @Override
    public void updateRangeIndexes(){
        colorSelectorForSingleColorMultiShades.updateRangeIndexes();
    }


    @Override
    public void resetCurrentIndex(){
        if(colorSequenceControls.isResetOnRelease || isAtLastIndex){
            colorSelectorForSingleColorMultiShades.resetCurrentIndex();
            currentIndex = 0;
            if(ids.size() > 0) {
                blendCalculator.reset(getShadeFromCurrentIndexAtFixedBrightness(), getNextBlendTarget());
            }
        }
    }


    private int getNextShadeForwards(){
        int firstIndex = 0;
        int lastIndex = ids.size() -1;
        currentIndex = currentIndex >= lastIndex ? firstIndex : currentIndex + 1;
        return getShadeFromCurrentIndexAtFixedBrightness();
    }


    private int getNextShadeBackwards(){
        int lastIndex = 0;
        int firstIndex = ids.size() -1;
        currentIndex =  currentIndex <= lastIndex ? firstIndex : currentIndex - 1;
        return getShadeFromCurrentIndexAtFixedBrightness();
    }


    private int getNextRandomShade(){
        int oldIndex = currentIndex;
        while(currentIndex == oldIndex){
            currentIndex = random.nextInt(ids.size());
            isAtLastIndex = true;
        }
        return getShadeFromCurrentIndexAtFixedBrightness();
    }


    private int getNextStrobeShade(){
        currentIndex = strobeCalculatorForMultiColor.getNextStrobeIndex(currentIndex, ids.size()-1);
        return getShadeFromCurrentIndexAtFixedBrightness();
    }


    private int getShadeFromCurrentIndexAtFixedBrightness(){
        if(currentIndex >= ids.size()){
            return Color.BLACK;
        }
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
        int lastIndex = list.size() -1;
        int index = (int)((lastIndex / 100f)* percentage);
        return Math.min(lastIndex, index);
    }


    private void assignOnlyColorToSequenceColorSelector(){
        if(ids.size() == 1){
            colorSelectorForSingleColorMultiShades.setColorList(shadesMap.get(ids.get(0)));
        }
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


    /*
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
    */


}
