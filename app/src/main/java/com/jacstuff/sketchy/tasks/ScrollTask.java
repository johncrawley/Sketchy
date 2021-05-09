package com.jacstuff.sketchy.tasks;

import android.widget.HorizontalScrollView;

public class ScrollTask implements Runnable {
    private HorizontalScrollView horizontalScrollView;
    private int position;

    public ScrollTask(HorizontalScrollView horizontalScrollView, int position){
        this.horizontalScrollView = horizontalScrollView;
        this.position = position;
    }

    public void run() {
        horizontalScrollView.smoothScrollTo(position,0);
    }
}
