package com.jacstuff.sketchy.paintview.history;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.jacstuff.sketchy.R;

public class HistoryMemoryHelper {

    public static boolean isLowMemoryFor(Bitmap bitmap, Context context, int historySize){
        boolean isHistorySizeAtLimit = isHistorySizeAtLimit(context, historySize);
        boolean isMemPercentageLow = getFreeMemoryPercentage(context) < 10;
        boolean hasNotEnoughSpaceForBitmap = hasNoSpaceFor(bitmap);

        log("history at limit: " + isHistorySizeAtLimit + " isMemLow: " + isMemPercentageLow + " hasNotEnoughSpaceForBitmap: " + hasNotEnoughSpaceForBitmap);


        return isHistorySizeAtLimit(context, historySize)
                || getFreeMemoryPercentage(context) < 10
                || hasNoSpaceFor(bitmap);
    }


    private static void log(String msg){
     //   System.out.println("^^^ HistoryMemoryHelper: " + msg);
    }


    private static double getFreeMemoryPercentage(Context context){
        var mi = new ActivityManager.MemoryInfo();
        var activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double percentageFree = mi.availMem / (double)mi.totalMem * 100.0;
        log("getFreeMemoryPercentage() available: " + mi.availMem + " total: " + mi.totalMem + " percentage free: " + percentageFree);
        return mi.availMem / (double)mi.totalMem * 100.0;
    }


    private static boolean hasNoSpaceFor(Bitmap bitmap){
        int bytesPerBitmap = bitmap.getAllocationByteCount();
        return !(bytesPerBitmap * 3f < getAvailableMemoryBytes());
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
