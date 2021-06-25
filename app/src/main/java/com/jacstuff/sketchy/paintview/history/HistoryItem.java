package com.jacstuff.sketchy.paintview.history;

import android.graphics.Bitmap;

public class HistoryItem {
    private final Bitmap bitmap;
    private final int savedOrientation;

    public HistoryItem(Bitmap bitmap, int savedOrientation){
        this.bitmap = bitmap;
        this.savedOrientation = savedOrientation;
    }


    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public int getOrientation(){
        return this.savedOrientation;
    }
}
