package com.jacstuff.sketchy.ui;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserColorStore {

    private static final String PREFS_NAME = "Sketchty_prefs";
    private static final String PREF_SAVED_COLORS_LIST = "saved_colors_list";
    private static final String PREF_SAVED_COLOR = "saved_color_";
    private static final String PREF_DEFAULT_COLOR = "default_color_";
    private static final String PREF_ARE_PROPS_INITIALIZED = "default_color_";
    private static final String PREF_COLOR_STORE_VERSION = "color_store_version";
    private static final String PREF_NUMBER_OF_COLORS = "number_of_colors";
    private static final String DELIMITER =",";


    public static List<Integer> get(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
        String savedColorsStr =  prefs.getString(PREF_SAVED_COLORS_LIST, "");
        if(savedColorsStr== null || savedColorsStr.isEmpty()){
            return Collections.emptyList();
        }
        List<String> colors = Arrays.asList(savedColorsStr.split(DELIMITER));
        return convertToIntList(colors);
    }


    public static List<Integer> getAllColors(Context context){
        List<Integer> colors = new ArrayList<>(40);
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
        int numberOfColors = prefs.getInt(PREF_NUMBER_OF_COLORS, 0);
        for(int i=0; i< numberOfColors; i++){
            colors.add(prefs.getInt(getColorPref(i), 0));
        }
        return colors;
    }


    public static boolean arePropertiesInitialized(Context context, int colorStoreVersion){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
        return prefs.getBoolean(PREF_ARE_PROPS_INITIALIZED, false)
                && prefs.getInt(PREF_COLOR_STORE_VERSION, -1) == colorStoreVersion;
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
        String savedColorsStr = prefs.getString(PREF_SAVED_COLORS_LIST,"");

        if (savedColorsStr!= null && !savedColorsStr.contains(String.valueOf(color))) {
            if(!savedColorsStr.isEmpty()){
                savedColorsStr += ",";
            }
            savedColorsStr += color;
            prefs.edit().putString(PREF_SAVED_COLORS_LIST, savedColorsStr).apply();
            return true;
        }
        return false;
    }


    public static void initStore(List<Integer> colors, Context context, int version){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        wipeAllPropsIfExistingVersionIsNot(version, context);

        if(prefs.contains(PREF_ARE_PROPS_INITIALIZED)) {
            return;
        }
        addColorProps(colors, prefs.edit());
    }


    private static void wipeAllPropsIfExistingVersionIsNot(int version, Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int existingColorStoreVersion =  prefs.getInt(PREF_COLOR_STORE_VERSION, -1);
        if(version == existingColorStoreVersion){
            return;
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(PREF_ARE_PROPS_INITIALIZED);
        int numberOfExistingColors = prefs.getInt(PREF_NUMBER_OF_COLORS, 0);
        for(int i = 0; i < numberOfExistingColors; i++){
            editor.remove(PREF_SAVED_COLOR + i);
            editor.remove(PREF_DEFAULT_COLOR + i);
        }
        editor.apply();
    }


    private static void addColorProps(List<Integer> colors, SharedPreferences.Editor editor){
        for (int i = 0; i < colors.size(); i++) {
            editor.putInt(PREF_SAVED_COLOR + i, colors.get(i));
            editor.putInt(PREF_DEFAULT_COLOR + i, colors.get(i));
        }
        editor.putBoolean(PREF_ARE_PROPS_INITIALIZED, true);
        editor.putInt(PREF_NUMBER_OF_COLORS, colors.size());
        editor.apply();
    }


    public static void editColor(int editedColor, int index, Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getColorPref(index), editedColor);
        editor.apply();
    }


    public static void resetColorsToDefault(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        int numberOfColors = prefs.getInt(PREF_NUMBER_OF_COLORS, 0);
        for (int i = 0; i < numberOfColors; i++) {
            int defaultColor = prefs.getInt(PREF_DEFAULT_COLOR + i, 0);
            editor.putInt(getColorPref(i), defaultColor);
        }
        editor.apply();
    }


    private static String getColorPref(int index){
        return PREF_SAVED_COLOR + index;
    }


}
