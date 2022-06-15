package com.jacstuff.sketchy.paintview.history;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class HistoryItem {
    private final Bitmap bitmap;
    private PointF connectedLinePreviousDown;
    private final int savedOrientation;

    public HistoryItem(Bitmap bitmap, int savedOrientation, PointF connectedLinePreviousDown){
        this.bitmap = bitmap;
        this.savedOrientation = savedOrientation;
        this.connectedLinePreviousDown = connectedLinePreviousDown;
    }


    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public int getOrientation(){
        return this.savedOrientation;
    }

    public PointF getConnectedLinePreviousDown(){return connectedLinePreviousDown;}
}
