package com.jacstuff.sketchy.tasks;

import android.widget.HorizontalScrollView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ColorAutoScroller {

    private ScheduledExecutorService executor;
    private ScrollTask scrollToEnd, scrollToNearStart;

    public ColorAutoScroller(HorizontalScrollView colorScrollView){
        scrollToEnd = new ScrollTask(colorScrollView, 1500);
        scrollToNearStart = new ScrollTask(colorScrollView, 95);
        executor = Executors.newSingleThreadScheduledExecutor();
        engageAutoScroll();
    }


    void engageAutoScroll() {
        executor.schedule(scrollToEnd, 300, TimeUnit.MILLISECONDS);
        executor.schedule(scrollToNearStart, 1700, TimeUnit.MILLISECONDS);
    }
}