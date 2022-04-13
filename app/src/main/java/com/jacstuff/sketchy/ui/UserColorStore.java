package com.jacstuff.sketchy.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.jacstuff.sketchy.R;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserColorStore {

    private static final String PREFS_NAME = "Sketchty_prefs";
    private static final String SAVED_COLORS_PREF_NAME = "saved_colors";

    public static Set<String> get(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
        Set<String> savedColors =  prefs.getStringSet(SAVED_COLORS_PREF_NAME, Collections.emptySet());
        assert savedColors != null;
        return new HashSet<>(savedColors);
    }


    public static boolean save(int color, Context context){
        String colorStr = String.valueOf(color);
        Set<String> savedColorSet = get(context);
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        if (!savedColorSet.contains(colorStr)) {
            savedColorSet.add(colorStr);
            prefs.edit().putStringSet(SAVED_COLORS_PREF_NAME, savedColorSet).apply();
            return true;
        }
        return false;
    }
}
