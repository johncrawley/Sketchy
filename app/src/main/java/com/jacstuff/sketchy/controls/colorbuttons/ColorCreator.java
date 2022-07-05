package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.graphics.Color;

import com.jacstuff.sketchy.ui.UserColorStore;

import java.util.ArrayList;
import java.util.List;


public class ColorCreator {

    private ColorCreator(){}


    public static void loadUserColorsAndAddTo(List<Integer> list, Context context){
        generate(context);
        if(!list.isEmpty()){
            return;
        }
        list.addAll(UserColorStore.getAllColors(context));
    }

    //NB Update the COLOR_STORE_VERSION every time a color is added or amended here
    public static void generate(Context context){
        final int COLOR_STORE_VERSION = 0;
        List<Integer> colors = new ArrayList<>();
        if(UserColorStore.arePropertiesInitialized(context, COLOR_STORE_VERSION)){
            return;
        }
        add(colors,"magenta", Color.MAGENTA);
        add(colors,"green", Color.GREEN);
        add(colors,"yellow", Color.YELLOW);
        add(colors,"black", Color.BLACK);
        add(colors,"peach"    , 255,229,180);
        add(colors,"light_blue",0,  130,255);
        add(colors,"off-teal"  , 9,  128,78);
        add(colors,"brown"    , 127,51,0);
        add(colors,"cyan", Color.CYAN);
        add(colors,"red", Color.RED);
        add(colors,"slime_green", 0,  255,144);
        add(colors,"orange"   , 255,106,0);
        add(colors,"gold"     , 255,215,0);
        add(colors,"beige"    , 245,245,220);
        add(colors,"blue", Color.BLUE);
        add(colors,"white", Color.WHITE);
        add(colors,"olive"    , 128,128,0);
        add(colors,"purple"   , 140,40,255);
        add(colors,"gray", Color.GRAY);
        add(colors,"lime", 215,255,0);
        add(colors,"lilac", 215,214,255);
        add(colors,"neon blue", 0,206,255);
        add(colors,"light pink", 255,201,255);
        add(colors,"green off-mint", 221,255,186);
        add(colors,"grey blue", 220,230,255);
        add(colors,"off peach", 239,197,184);
        add(colors,"baby blue", 137,207,240);
        add(colors,"light red-orange", 255,90,73);
        add(colors,"dark maroon", 70,3,49);
        add(colors,"navy with green", 0,30,70);
        add(colors,"user1", 77,64,111);
        add(colors,"user2", 40,100,50);
        add(colors,"user3", 150,100,140);
        add(colors,"user4", 100,150,100);
        add(colors,"user5", 155,129,108);
        UserColorStore.initStore(colors, context, COLOR_STORE_VERSION);
    }


    private static void add(List<Integer> list, String key, int r, int g, int b){
        int color = ColorConverter.getIntFrom(r,g,b);
        add(list, key, color);
    }


    private static void add(List<Integer> list, String key, int colorCode){
        list.add(colorCode);
    }


}
