package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;

import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.multicolor.pattern.RandomPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomMultiColorSelector implements ColorSelector {

    private final MulticolorPattern pattern;
    private final Map<String, List<Integer>> shadesMap;
    private final List<String> ids;
    private final Random random;

    public RandomMultiColorSelector(){
        pattern = new RandomPattern();
        shadesMap = new HashMap<>(30);
        ids = new ArrayList<>(30);
        random = new Random(System.currentTimeMillis());
    }


    @Override
    public int getNextColor(){
        String randomId = getRandomId();
        List<Integer> shades = shadesMap.get(randomId);
        if(shades == null){
            return Color.BLACK;
        }
        int randomShadeIndex = pattern.getNextIndex(shades.size());
        return shades.get(randomShadeIndex);
    }


    private String getRandomId(){
        return ids.get(random.nextInt(ids.size()));
    }


    @Override
    public void add(int id, List<Integer> shades){
        String idStr = Integer.toString(id);
        shadesMap.put(idStr, shades);
        ids.add(idStr);
    }

    @Override
    public void remove(int id){
        String idStr = Integer.toString(id);
        shadesMap.remove(idStr);
        ids.remove(idStr);
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
