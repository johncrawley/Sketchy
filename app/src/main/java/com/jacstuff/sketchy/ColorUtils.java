package com.jacstuff.sketchy;

public class ColorUtils {

    public enum Rgb {
        RED(16),
        GREEN(8),
        BLUE(0);


        private final int bitShift;

        Rgb(int bitShift){
            this.bitShift = bitShift;
        }

        int getBitshift(){
            return bitShift;
        }

    }

    public static int getComponentFrom(int color, Rgb rgb){
        return (color >> rgb.getBitshift()) & 0xff;
    }

}
