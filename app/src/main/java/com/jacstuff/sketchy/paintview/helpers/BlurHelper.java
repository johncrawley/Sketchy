package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.BlurMaskFilter;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BlurType;

public class BlurHelper {

    private BlurType blurType;
    private Paint paint;
    private int blurRadius = 1;

    public BlurHelper(Paint paint){
        this.paint = paint;
    }


    public void setBlurType(BlurType blurType){
        this.blurType = blurType;
    }


    public void setBlurRadius(int blurRadius){
        this.blurRadius = blurRadius;
    }


    public void assignBlur(){
        switch (blurType){
            case NONE:
                paint.setMaskFilter(null);
                break;
            case OUTER:
                paint.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.OUTER));
                break;
            case NORMAL:
                paint.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL));
                break;
            case SOLID:
                paint.setMaskFilter(new BlurMaskFilter(10 + blurRadius, BlurMaskFilter.Blur.SOLID));
                break;
            case INNER:
                paint.setMaskFilter(new BlurMaskFilter(10 + blurRadius, BlurMaskFilter.Blur.INNER));

        }
    }

}
