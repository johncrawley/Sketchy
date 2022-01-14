package com.jacstuff.sketchy.utils;

import android.app.Activity;
import android.content.res.Configuration;

public class ActivityUtils {

    public static boolean isInLandscapeOrientation(Activity activity){
        return Configuration.ORIENTATION_LANDSCAPE == activity.getResources().getConfiguration().orientation;
    }
}
