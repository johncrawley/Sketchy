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

}
