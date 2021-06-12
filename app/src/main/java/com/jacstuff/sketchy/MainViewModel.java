package com.jacstuff.sketchy;

import android.graphics.Bitmap;

import java.util.ArrayDeque;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public int lastClickedColorButtonId;
    public ArrayDeque<Bitmap> bitmapHistory;
    public Bitmap bitmap;
    public long time;

}
