package com.jacstuff.sketchy.paintview.history;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayDeque;

import static android.content.Context.ACTIVITY_SERVICE;


public class BitmapHistory {

    private final Context context;
    private ArrayDeque<HistoryItem> history;


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


    public boolean isEmpty(){
        return history.isEmpty();
    }


    public void push(Bitmap bitmap, int orientation){
        if(getFreeMemoryPercentage() < 15){
            history.removeLast();
        }
        history.addFirst(new HistoryItem(Bitmap.createBitmap(bitmap), orientation));
    }


    public HistoryItem getPrevious(){
        if(history.size() == 0){
            return null;
        }
        if(history.size() > 1) {
            history.removeFirst(); // this is the bitmap of what we currently see, so just get rid of it
        }
        return history.peekFirst();
    }


    public HistoryItem getCurrent(){
        if(null != history && history.size() > 0) {
            return history.peekFirst();
        }
        return null;
    }


    public ArrayDeque<HistoryItem> getAll(){
        return history;
    }


    public void setAll(ArrayDeque<HistoryItem> history){
        this.history = history;
    }
}
