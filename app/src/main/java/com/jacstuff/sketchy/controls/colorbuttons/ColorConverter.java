package com.jacstuff.sketchy.controls.colorbuttons;

public class ColorConverter {
    public static int getIntFrom(int r, int g, int b){
        int a = 255;
        return (a & 0xff) << 24 | (r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff);

    }
}
