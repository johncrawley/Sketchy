package com.jacstuff.sketchy.controls.colorbuttons;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class ColorCreator {

    // to prevent instantiation
    private ColorCreator(){}


    public static Map<String, Color> generate(){
        Map<String, Color> colors = new HashMap<>();
        add(colors,"blue", Color.BLUE);
        add(colors,"red", Color.RED);
        add(colors,"yellow", Color.YELLOW);
        add(colors,"gray", Color.GRAY);
        add(colors,"black", Color.BLACK);
        add(colors,"white", Color.WHITE);
        add(colors,"green", Color.GREEN);
        add(colors,"magenta", Color.MAGENTA);
        add(colors,"cyan", Color.CYAN);
        add(colors,"slime_green", 0,  255,144);
        add(colors,"light_blue",0,  130,255);
        add(colors,"orange"   , 255,106,0);
        add(colors,"purple"   , 178,0,255);
        add(colors,"brown"    , 127,51,0);
        add(colors,"gold"     , 255,215,0);
        add(colors,"peach"    , 255,229,180);
        add(colors,"beige"    , 245,245,220);
        add(colors,"teal"     , 0,  128,128);
        add(colors,"olive"    , 128,128,0);
        return colors;
    }

    private static void add(Map<String, Color> map, String key, int r, int g, int b){
        add(map, key, Color.argb(255, r,g,b));
    }


    private static void add(Map<String, Color> map, String key, int colorCode){
        map.put(key, Color.valueOf(colorCode));
    }



}
