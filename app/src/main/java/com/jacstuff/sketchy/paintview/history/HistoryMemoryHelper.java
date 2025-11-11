package com.jacstuff.sketchy.paintview.history;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.jacstuff.sketchy.R;

public class HistoryMemoryHelper {

    public static boolean isLowMemoryFor(Bitmap bitmap, Context context, int historySize){
        return isHistorySizeAtLimit(context, historySize)
                || getFreeMemoryPercentage(context) < 20
                || !hasSpaceFor(bitmap);
    }


    private static double getFreeMemoryPercentage(Context context){
        var mi = new ActivityManager.MemoryInfo();
        var activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        return mi.availMem / (double)mi.totalMem * 100.0;
    }


    private static boolean hasSpaceFor(Bitmap bitmap){
        int bytesPerBitmap = bitmap.getAllocationByteCount();
        return bytesPerBitmap * 3f < getAvailableMemoryBytes();
    }


    private static long getAvailableMemoryBytes(){
        final var runtime = Runtime.getRuntime();
        final long usedMemInBytes = (runtime.totalMemory() - runtime.freeMemory());
        final long maxHeapSizeInBytes = runtime.maxMemory();
        return (maxHeapSizeInBytes - usedMemInBytes);
    }


    private static boolean isHistorySizeAtLimit(Context context, int historySize){
        int limit = context.getResources().getInteger(R.integer.bitmap_history_maximum_size);
        return historySize > limit;
    }
}
