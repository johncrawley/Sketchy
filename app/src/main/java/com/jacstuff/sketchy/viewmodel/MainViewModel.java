package com.jacstuff.sketchy.viewmodel;

import android.graphics.Color;

import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.history.HistoryItem;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public int lastClickedColorButtonId;
    public ArrayDeque<HistoryItem> bitmapHistoryItems;
    public String mostRecentColorButtonKey;
    public String mostRecentShadeButtonKey;
    public boolean isMostRecentClickAShade;
    public List<String> selectedShadeButtonKeys;

    public Map<ButtonCategory, Integer> settingsButtonsClickMap;


    public boolean isKaleidoscopeCentred = true;

    public int halfBrushSize = 10;
    public int radialGradientRadius=1;
    public int clampRadialGradientRadius=10;
    public int linearGradientLength=100;

    public int color = Color.BLACK;
    public int previousColor = Color.WHITE;

    public boolean isGlitchModeEnabled = false;

    public boolean useSeekBarAngle = false;
    public int angle = 0;
}
