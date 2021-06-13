package com.jacstuff.sketchy;

import android.graphics.Bitmap;

import java.util.ArrayDeque;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public int lastClickedColorButtonId;
    public ArrayDeque<Bitmap> bitmapHistory;
    public Bitmap bitmap;
    public long time;
    public String mostRecentColorButtonKey;
    public String mostRecentShadeButtonKey;
    public boolean isMostRecentClickAShade;
    public List<String> selectedShadeButtonKeys;

}
