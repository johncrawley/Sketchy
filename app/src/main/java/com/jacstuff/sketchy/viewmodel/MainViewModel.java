package com.jacstuff.sketchy.viewmodel;

import android.graphics.Color;

import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.history.HistoryItem;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public boolean isFirstExecution = true;
    public int lastClickedColorButtonId;
    public ArrayDeque<HistoryItem> bitmapHistoryItems;
    public String mostRecentColorButtonKey;
    public String mostRecentShadeButtonKey;
    public boolean isMostRecentClickAShade;
    public List<String> selectedShadeButtonKeys;
    public Map<Integer, Integer> seekBarValue;

    public Map<ButtonCategory, Integer> settingsButtonsClickMap;

    public boolean isKaleidoscopeCentred = true;

    public int brushSize;
    public int brushSizeSetBySeekBar;
    public int sizeSequenceMin = 5;
    public int sizeSequenceMax;
    public int sizeSequenceIncrement = 2;
    public boolean isSizeSequenceRepeated = true;
    public boolean isSizeSequenceResetOnTouchUp = true;
    public int halfBrushSize = 10;
    public int radialGradientRadius=1;
    public int clampRadialGradientRadius=10;
    public int linearGradientLength=100;
    public boolean snapRectangleToEdge =true;

    public int colorTransparency;

    public String textBrushText = "";

    public int color = Color.BLACK;
    public int previousColor = Color.WHITE;

    public boolean isInfinityModeEnabled = false;

    public boolean useSeekBarAngle = false;
    public int textSkewSeekBarProgress = 100;
    public int textSpacingSeekBarProgress = 1;
    public int angle = 0;

    public int gradient;
    public int gradientMaxLength;


    public MainViewModel(){
        seekBarValue = new HashMap<>();
    }
}
