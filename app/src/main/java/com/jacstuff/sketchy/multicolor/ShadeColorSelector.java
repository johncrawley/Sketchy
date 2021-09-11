package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;

import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.multicolor.pattern.RandomPattern;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ShadeColorSelector implements ColorSelector {

    private final MulticolorPattern pattern;
    private final Map<String, List<Integer>> shadesMap;
    private final List<String> ids;
    private final Random random;
    private final ColorSequenceControls colorSequenceControls;
    private final SequenceColorSelector sequenceColorSelector;

    private int currentIndex = 0;

    public ShadeColorSelector(MainViewModel viewModel){
        colorSequenceControls = viewModel.getColorSequenceControls();
        sequenceColorSelector = new SequenceColorSelector(viewModel);
        pattern = new RandomPattern();
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
        currentIndex = currentIndex == ids.size() -1 ? 0 : currentIndex +1;
        return getShade();
    }


    private int getNextShadeBackwards(){
        currentIndex =  currentIndex == 0 ? ids.size()-1 : currentIndex -1;
        return getShade();
    }


    private int getNextRandomShade(){
        int oldIndex = currentIndex;
        while(currentIndex == oldIndex){
            currentIndex = random.nextInt(ids.size());

        }
        return getRandomShade();
    }


    private int getRandomShade(){
        String shadeListKey = ids.get(currentIndex);
        List<Integer> shadesList =  shadesMap.get(shadeListKey);

        if(shadesList == null){
            return Color.BLACK;
        }
        int minIndex = getMinIndexOfSequence(colorSequenceControls.colorSequenceMinPercentage, shadesList);
        int maxIndex = getMaxIndexOfSequence(colorSequenceControls.colorSequenceMaxPercentage, shadesList);
        int randomIndex = minIndex + random.nextInt((maxIndex - minIndex) + 1);
        return shadesList.get(random.nextInt(randomIndex));
    }


    private int getShade(){
        List<Integer> shadesList =  shadesMap.get(ids.get(currentIndex));
        if(shadesList != null){
            int minIndex = getMinIndexOfSequence(colorSequenceControls.colorSequenceMinPercentage, shadesList);
            return shadesList.get(minIndex);
        }
        return Color.BLACK;
    }

    private int direction = 1;

    private int getNextStrobeShade(){
        return Color.RED;
    }


    public int getMinIndexOfSequence(int seekBarColorRangeMaximum, List<Integer> colorList){
        int lastIndex = colorList.size() -1;
        int minSequenceIndex = (int)((lastIndex / 100f) * seekBarColorRangeMaximum);
        return Math.min(colorList.size()-2, minSequenceIndex);
    }

    public int getMaxIndexOfSequence(int seekBarColorRangeMaximum, List<Integer> colorList){
        int lastIndex = colorList.size() -1;
        int maxSequenceIndex = (int)((lastIndex / 100f) * seekBarColorRangeMaximum);
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
    public String getCurrentPatternLabel(){
        return "";
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
    public void nextPattern(){
        //do nothing
    }

    @Override
    public void resetCurrentIndex(){
        //do nothing
    }


}
