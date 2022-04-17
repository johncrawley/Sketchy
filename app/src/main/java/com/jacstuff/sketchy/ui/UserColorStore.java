package com.jacstuff.sketchy.ui;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserColorStore {

    private static final String PREFS_NAME = "Sketchty_prefs";
    private static final String SAVED_COLORS_PREF_NAME = "saved_colors_list";
    private static final String DELIMITER =",";


    public static List<Integer> get(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
        String savedColorsStr =  prefs.getString(SAVED_COLORS_PREF_NAME, "");
        if(savedColorsStr== null || savedColorsStr.isEmpty()){
            return Collections.emptyList();
        }
        List<String> colors = Arrays.asList(savedColorsStr.split(DELIMITER));
        return convertToIntList(colors);
    }


    private static List<Integer> convertToIntList(List<String> list){
        List<Integer> intList = new ArrayList<>(list.size());
        for(String str: list){
            if(str.trim().isEmpty()){
                continue;
            }
            intList.add(Integer.parseInt(str));
        }
        return intList;
    }


    public static boolean save(int color, Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String savedColorsStr = prefs.getString(SAVED_COLORS_PREF_NAME,"");

        if (savedColorsStr!= null && !savedColorsStr.contains(String.valueOf(color))) {
            if(!savedColorsStr.isEmpty()){
                savedColorsStr += ",";
            }
            savedColorsStr += color;
            prefs.edit().putString(SAVED_COLORS_PREF_NAME, savedColorsStr).apply();
            return true;
        }
        return false;
    }


    public static void delete(int color, Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String savedColorsStr = prefs.getString(SAVED_COLORS_PREF_NAME,"");
        String colorStr = String.valueOf(color);
        log("delete() : color to delete: " + color);
        log("delete() : existing colors: " + savedColorsStr);
        String[] colorArray = savedColorsStr.split(DELIMITER);
        StringBuilder str = new StringBuilder();
        for (String s : colorArray) {
            if (s.equals(colorStr)) {
                continue;
            }
            str.append(s);
            str.append(",");
        }
        String output = str.substring(0, str.length()-1);
        log("delete() amended color string: " + output);
        prefs.edit().putString(SAVED_COLORS_PREF_NAME, output).apply();
    }

    private static void log(String msg){
        System.out.println("UserColorStore: " + msg);
    }
}
