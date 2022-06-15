package com.jacstuff.sketchy.paintview.history;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.ArrayDeque;

import static android.content.Context.ACTIVITY_SERVICE;


public class DrawHistory {

    private final Context context;
    private ArrayDeque<HistoryItem> history;
    private final MainViewModel viewModel;

    public DrawHistory(Context context, MainViewModel viewModel){
        this.context = context;
        this.viewModel = viewModel;
        history = new ArrayDeque<>(50);
    }


    private double getFreeMemoryPercentage(){
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        return mi.availMem / (double)mi.totalMem * 100.0;
    }


    private boolean hasSpaceFor(Bitmap bitmap){
        int bytesPerBitmap = bitmap.getAllocationByteCount();
        return bytesPerBitmap * 3f < getAvailableMemoryBytes();
    }


    public boolean isEmpty(){
        return history.isEmpty();
    }


    public void push(Bitmap bitmap){
        if(isHistorySizeAtLimit() || getFreeMemoryPercentage() < 20 || !hasSpaceFor(bitmap)){
            if(history.isEmpty()){
                //nothing to remove and not enough memory to save the latest bitmap
                return;
            }
            history.removeLast();
        }
        history.addFirst(new HistoryItem(Bitmap.createBitmap(bitmap),
                getCurrentScreenOrientation(),
                getPreviousLineDown()));
    }


    private PointF getPreviousLineDown(){
        PointF p = new PointF();
        p.x = viewModel.nextLineDownX;
        p.y = viewModel.nextLineDownY;
        return p;
    }


    private long getAvailableMemoryBytes(){
        final Runtime runtime = Runtime.getRuntime();
        final long usedMemInBytes=(runtime.totalMemory() - runtime.freeMemory());
        final long maxHeapSizeInBytes=runtime.maxMemory();
        return (maxHeapSizeInBytes - usedMemInBytes);
    }


    private boolean isHistorySizeAtLimit(){
        int limit = context.getResources().getInteger(R.integer.bitmap_history_maximum_size);
        return history.size() > limit;
    }

    private int getCurrentScreenOrientation(){
        return context.getResources().getConfiguration().orientation;
    }


    public HistoryItem getPrevious(){
        if(history.size() == 0){
            return null;
        }
        if(history.size() > 1) {
            history.removeFirst(); // this is the bitmap of what we currently see, so just get rid of it
        }
        return assignViewModelPropsAndGetFirst();
    }


    public HistoryItem getCurrent(){
        if(null != history && history.size() > 0) {
            return assignViewModelPropsAndGetFirst();
        }
        return null;
    }


    private HistoryItem assignViewModelPropsAndGetFirst(){
        HistoryItem historyItem = history.peekFirst();
        if(historyItem == null){
            return null;
        }
        PointF previousLineDown = historyItem.getConnectedLinePreviousDown();
        if(previousLineDown != null) {
            viewModel.nextLineDownX = previousLineDown.x;
            viewModel.nextLineDownY = previousLineDown.y;
        }
        return historyItem;
    }


    public ArrayDeque<HistoryItem> getAll(){
        return history;
    }


    public void setAll(ArrayDeque<HistoryItem> history){
        this.history = history;
    }
}
