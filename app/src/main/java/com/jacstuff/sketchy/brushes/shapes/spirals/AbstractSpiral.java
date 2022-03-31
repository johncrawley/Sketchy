package com.jacstuff.sketchy.brushes.shapes.spirals;

import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractBrush;

public abstract class AbstractSpiral extends AbstractBrush {

    Paint.Style savedStyle;
    private float savedStrokeWidth;


    public AbstractSpiral(BrushShape brushShape){
        super(brushShape);
    }


    void saveSettings(Paint paint) {
        savedStrokeWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(1+ (savedStrokeWidth / 10));
        savedStyle = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);
    }


    void recallSettings(Paint paint){
        paint.setStyle(savedStyle);
        paint.setStrokeWidth(savedStrokeWidth);
    }

    @Override
    public void reinitialize(){
        super.reinitialize();
        mainViewModel.isUsingDangerousBrush = true;
    }


    @Override
    public void onDeallocate(){
        super.onDeallocate();
        mainViewModel.isUsingDangerousBrush = false;
    }
}
