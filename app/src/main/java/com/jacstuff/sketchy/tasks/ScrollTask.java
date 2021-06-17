package com.jacstuff.sketchy.tasks;

import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ScrollTask implements Runnable {
    private HorizontalScrollView horizontalScrollView;
    private ScrollView verticalScrollView;
    private int position;

    public ScrollTask(HorizontalScrollView horizontalScrollView, ScrollView verticalScrollView, int position){
        this.horizontalScrollView = horizontalScrollView;
        this.verticalScrollView = verticalScrollView;
        this.position = position;
    }

    public void run() {
        if(horizontalScrollView != null) {
            horizontalScrollView.smoothScrollTo(position, 0);
        }
        if(verticalScrollView != null){
            verticalScrollView.smoothScrollTo(position, 0);
        }
    }
}
