package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.graphics.Color;

import com.jacstuff.sketchy.ui.UserColorStore;

import java.util.ArrayList;
import java.util.List;


public class ColorCreator {

    // to prevent instantiation
    private ColorCreator(){}


    public static void generateMainColorsAndAddTo(List<Integer> emptyList, Context context){
        if(!emptyList.isEmpty()){
            return;
        }
        emptyList.addAll(generate(context));
    }

    public static void loadUserColorsAndAddTo(List<Integer> list, Context context){
        generate(context);
        if(!list.isEmpty()){
            return;
        }
        list.addAll(UserColorStore.getAllColors(context));
        printList(list);
    }

    private static void log(String msg){
        System.out.println("^^^ ColorCreator: "+ msg);
    }

    private static void printList(List<Integer> colors){
        StringBuilder str =  new StringBuilder();
        for(int color: colors){
            str.append(color);
            str.append(",");
        }
        log(str.toString());
    }


    public static List<Integer> generate(Context context){

        List<Integer> colors = new ArrayList<>();
        if(UserColorStore.arePropertiesInitialized(context)){
            log("Color Preferences are initialized, won't be adding again!");
            return colors;
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
        add(colors,"gray", Color.GRAY);
        add(colors,"blue", Color.BLUE);
        add(colors,"white", Color.WHITE);
        add(colors,"olive"    , 128,128,0);
        add(colors,"purple"   , 140,40,255);
        UserColorStore.initStore(colors, context);
        return colors;
    }


    private static void add(List<Integer> list, String key, int r, int g, int b){
        int color = ColorConverter.getIntFrom(r,g,b);
        add(list, key, color);
    }


    private static void add(List<Integer> list, String key, int colorCode){
        list.add(colorCode);
    }



}
