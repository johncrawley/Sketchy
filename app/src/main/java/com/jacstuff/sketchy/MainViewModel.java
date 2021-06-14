package com.jacstuff.sketchy;

import android.graphics.Bitmap;

import com.jacstuff.sketchy.controls.ButtonCategory;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public int lastClickedColorButtonId;
    public ArrayDeque<Bitmap> bitmapHistory;
    public String mostRecentColorButtonKey;
    public String mostRecentShadeButtonKey;
    public boolean isMostRecentClickAShade;
    public List<String> selectedShadeButtonKeys;

    public int menuButtonId;
    public Map<ButtonCategory, Integer> settingsButtonsClickMap;

}
