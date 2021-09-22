package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.BlurMaskFilter;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BlurType;

public class BlurHelper {

    private BlurType blurType;
    private Paint paint;
    private int blurRadius = 1;
    private final RefreshableBlurFilter outer, normal, solid, inner;

    public BlurHelper(){
        blurType = BlurType.NONE;
        outer = new RefreshableBlurFilter(BlurMaskFilter.Blur.OUTER, blurRadius, 0);
        inner = new RefreshableBlurFilter(BlurMaskFilter.Blur.INNER, blurRadius, 0);
        normal = new RefreshableBlurFilter(BlurMaskFilter.Blur.NORMAL, blurRadius, 2);
        solid = new RefreshableBlurFilter(BlurMaskFilter.Blur.SOLID, blurRadius, 10);
    }


    public void init(Paint p){
        this.paint = p;
    }


    public void setBlurType(BlurType blurType){
        this.blurType = blurType;
        assignBlur();
    }


    public void setBlurRadius(int radius){
        this.blurRadius = 1 + radius;
        outer.setSize(blurRadius);
        inner.setSize(blurRadius);
        normal.setSize(blurRadius);
        solid.setSize(blurRadius);
        assignBlur();
    }


    public void assignBlur(){
        BlurMaskFilter blur = null;
        switch (blurType){
            case NONE:
                break;
            case OUTER:
                blur = outer.getBlur();
                break;
            case NORMAL:
                blur = normal.getBlur();
                break;
            case SOLID:
                blur = solid.getBlur();
                break;
            case INNER:
                blur = inner.getBlur();
        }
        paint.setMaskFilter(blur);
    }

}
