package com.jacstuff.sketchy.tasks;

import android.widget.HorizontalScrollView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ColorAutoScroller {

    private ScheduledExecutorService executor;
    private ScrollTask firstScroll, scrollToNearStart;

    public ColorAutoScroller(HorizontalScrollView colorScrollView){
        firstScroll = new ScrollTask(colorScrollView, 300);
        scrollToNearStart = new ScrollTask(colorScrollView, 95);
        executor = Executors.newSingleThreadScheduledExecutor();
        engageAutoScroll();
    }


    void engageAutoScroll() {
        executor.schedule(firstScroll, 300, TimeUnit.MILLISECONDS);
        executor.schedule(scrollToNearStart, 1000, TimeUnit.MILLISECONDS);
    }
}