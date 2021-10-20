package com.jacstuff.sketchy.utils;

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


    public static boolean haveEqualRgb(int color1, int color2){
        return areEqual(color1, color2, Rgb.RED)
                && areEqual(color1, color2, Rgb.GREEN)
                && areEqual(color1, color2, Rgb.BLUE);
    }

    public static boolean areEqual(int color1, int color2, Rgb rgb){
        return getComponentFrom(color1, rgb) == getComponentFrom(color2, rgb);
    }


}
