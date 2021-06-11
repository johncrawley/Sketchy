package com.jacstuff.sketchy.paintview;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayDeque;

import static android.content.Context.ACTIVITY_SERVICE;

public class BitmapHistory {

    private Context context;
    private ArrayDeque<Bitmap> history;

    public BitmapHistory(Context context){
        this.context = context;
        history = new ArrayDeque<>(50);
    }


    private double getFreeMemoryPercentage(){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        return mi.availMem / (double)mi.totalMem * 100.0;
    }


    public void push(Bitmap bitmap){
        if(getFreeMemoryPercentage() < 15){
            history.removeLast();
        }
        history.addFirst(Bitmap.createBitmap(bitmap));
    }


    public Bitmap getPreviousBitmap(){
        if(history.size() == 0){
            return null;
        }
        if(history.size() > 1) {
            history.removeFirst(); // this is the bitmap of what we currently see, so just get rid of it
        }
        return history.peekFirst();
    }

}
