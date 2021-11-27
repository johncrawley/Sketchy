package com.jacstuff.sketchy.utils;

import android.graphics.Color;

import java.util.Random;

public class ColorUtils {

    public enum Rgb {
        RED(16),
        GREEN(8),
        BLUE(0);

        private final int bitShift;

        Rgb(int bitShift){
            this.bitShift = bitShift;
        }

        int getBitShift(){
            return bitShift;
        }
    }


    public static int getComponentFrom(int color, Rgb rgb){
        return (color >> rgb.getBitShift()) & 0xff;
    }


    public static String getRgbOf(int color){
        return "" + getComponentFrom(color, Rgb.RED) + ","
                + getComponentFrom(color, Rgb.GREEN) + ","
                + getComponentFrom(color, Rgb.BLUE);

    }

    public static int getRandomColor(Random random){
        int r = getRandomComponent(random);
        int g = getRandomComponent(random);
        int b = getRandomComponent(random);
        return Color.argb(255,r,g,b);
    }

    public static int getRandomComponent(Random random){
        return random.nextInt(255);
    }


    public static boolean haveEqualRgb(int color1, int color2){
        return areEqual(color1, color2, Rgb.RED)
                && areEqual(color1, color2, Rgb.GREEN)
                && areEqual(color1, color2, Rgb.BLUE);
    }

    public static boolean areEqual(int color1, int color2, Rgb rgb){
        return getComponentFrom(color1, rgb) == getComponentFrom(color2, rgb);
    }


}
