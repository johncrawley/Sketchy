package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.BlurMaskFilter;

public class RefreshableBlurFilter {
    private boolean isRefreshNeeded;
    private BlurMaskFilter blur;
    private final BlurMaskFilter.Blur blurStyle;
    private int size;
    private final int offset;


    public RefreshableBlurFilter(BlurMaskFilter.Blur blurStyle, int initialSize, int offset){
        this.blurStyle = blurStyle;
        this.offset = offset;
        blur = new BlurMaskFilter(offset + initialSize, blurStyle);
    }


    public void setSize(int size){
        this.size = offset + size;
        isRefreshNeeded = true;
    }


    public BlurMaskFilter getBlur(){
        if(isRefreshNeeded){
            blur = new BlurMaskFilter(size, blurStyle);
        }
        isRefreshNeeded = false;
        return blur;
    }
}
