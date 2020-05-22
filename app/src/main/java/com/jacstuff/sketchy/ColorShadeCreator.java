package com.jacstuff.sketchy;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

class ColorShadeCreator {


    List<Color> generateShadesFrom(Color color){
        List<Color> shades = getDarkShadesFrom(color);
        List<Color> lightShades= createShades(this::createIncrementedColor, color);
        shades.addAll(lightShades);
        return shades;
    }


    private Color createIncrementedColor(Color currentColor){
        return modifyColor(this::incIfWithinLimit, currentColor);
    }



    private List<Color> getDarkShadesFrom(Color color){
        List<Color> darkShades = createShades(this::createDecrementedColor, color);
        Collections.reverse(darkShades);
        darkShades.remove(darkShades.size()-1);
        return darkShades;
    }


    private Color createDecrementedColor(Color currentColor){
        return modifyColor(this::decIfAboveZero, currentColor);
    }


    private List<Color> createShades(Function<Color, Color> colorCreator, Color baseColor){
        final int NUMBER_OF_SHADES = 10;
        List<Color> shades = new ArrayList<>();
        Color current = baseColor;
        Color previous = null;
        for(int i = 0; i < NUMBER_OF_SHADES; i++){
            if(areTheSame(current, previous)){
                break;
            }
            previous = current;
            shades.add(current);
            current = colorCreator.apply(current);
        }
        return shades;
    }


    private boolean areTheSame(Color color, Color otherColor){
        return color == null || color.equals(otherColor);
    }



    private Color modifyColor(Function<Float, Float> valueFunction, Color currentColor){
        float r = valueFunction.apply(currentColor.red());
        float g = valueFunction.apply(currentColor.green());
        float b = valueFunction.apply(currentColor.blue());
        return Color.valueOf(r,g,b);
    }


    private final float SHADE_INCREMENT = 0.08f;

    private float incIfWithinLimit(float currentValue) {
        currentValue += SHADE_INCREMENT;
        return Math.min(1.0f, currentValue);
    }


    private float decIfAboveZero(float currentValue) {
        currentValue -= SHADE_INCREMENT;
        return Math.max(0.0f, currentValue);
    }

}
