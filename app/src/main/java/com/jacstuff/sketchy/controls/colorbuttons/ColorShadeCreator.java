package com.jacstuff.sketchy.controls.colorbuttons;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ColorShadeCreator {

    private final int NUMBER_OF_SHADES;
    private final int SHADE_INCREMENT;

    ColorShadeCreator(int numberOfShades, int shadeIncrement){
        NUMBER_OF_SHADES = numberOfShades;
        SHADE_INCREMENT = shadeIncrement;
    }


    List<Integer> generateShadesFrom(Integer color){
        List<Integer> shades = getDarkShadesFrom(color);
        List<Integer> lightShades= createShadesOf(color, true);
        shades.addAll(lightShades);
        return shades;
    }


    private List<Integer> getDarkShadesFrom(Integer color){
        List<Integer> darkShades = createShadesOf(color, false);
        Collections.reverse(darkShades);
        darkShades.remove(darkShades.size()-1);
        return darkShades;
    }


    private List<Integer> createShadesOf(int baseColor, boolean isLighter){
        List<Integer> shades = new ArrayList<>();
        int current = baseColor;
        int previous = 0;

        for(int i = 0; i < NUMBER_OF_SHADES; i++){
            if(current == previous){
                break;
            }
            previous = current;
            shades.add(current);
            current = getNextShadeOf(current, isLighter);
        }
        return shades;
    }


    private int getNextShadeOf(int currentColor, boolean isIncremented){

        int r1 = (currentColor >> 16) & 0xff;
        int g1 = (currentColor >> 8) & 0xff;
        int b1 = (currentColor      ) & 0xff;

        int r = adjust(r1, isIncremented);
        int g = adjust(g1, isIncremented);
        int b = adjust(b1, isIncremented);

        return ColorConverter.getIntFrom(r,g,b);
    }


    private int adjust(float currentValue, boolean isValueIncremented){
        if(isValueIncremented){
            currentValue += SHADE_INCREMENT;
            return (int)Math.min(255, currentValue);
        }
        currentValue -= SHADE_INCREMENT;
        return (int)Math.max(0, currentValue);
    }

}
