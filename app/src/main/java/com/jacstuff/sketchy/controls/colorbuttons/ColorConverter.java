package com.jacstuff.sketchy.controls.colorbuttons;

import com.jacstuff.sketchy.utils.ColorUtils;

import static com.jacstuff.sketchy.utils.ColorUtils.Rgb.BLUE;
import static com.jacstuff.sketchy.utils.ColorUtils.Rgb.GREEN;
import static com.jacstuff.sketchy.utils.ColorUtils.Rgb.RED;

public class ColorConverter {

    public static int getNextShadeOfColor(int currentColor, int targetColor){
        final int SHADE_INCREMENT = 8;
        return getNextShadeOfColor(currentColor, targetColor, SHADE_INCREMENT);
    }


    public static int getNextShadeOfColor(int currentColor, int targetColor, int increment){
        int r = getNextColorComponent(currentColor, targetColor, RED, increment);
        int g = getNextColorComponent(currentColor, targetColor, GREEN, increment);
        int b = getNextColorComponent(currentColor, targetColor, BLUE, increment);
        return ColorConverter.getIntFrom(r,g,b);
    }


    private static int getNextColorComponent(int currentColor, int targetColor, ColorUtils.Rgb rgb, int shadeIncrement){

        int source = ColorUtils.getComponentFrom(currentColor, rgb);
        int target = ColorUtils.getComponentFrom(targetColor, rgb);

        if(Math.abs(source - target) <= shadeIncrement){
            return target;
        }
        return source > target ?
                source - shadeIncrement
                : source + shadeIncrement;
    }




    public static int getIntFrom(int r, int g, int b){
        int a = 255;
        return (a & 0xff) << 24 | (r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff);

    }

}
