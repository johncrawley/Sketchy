package com.jacstuff.sketchy.tasks;

import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ScrollTask implements Runnable {
    private final HorizontalScrollView horizontalScrollView;
    private final int position;

    public ScrollTask(HorizontalScrollView horizontalScrollView, int position){
        this.horizontalScrollView = horizontalScrollView;
        this.position = position;
    }

    public void run() {
        if(horizontalScrollView != null) {
            horizontalScrollView.smoothScrollTo(position, 0);
        }
    }
}
