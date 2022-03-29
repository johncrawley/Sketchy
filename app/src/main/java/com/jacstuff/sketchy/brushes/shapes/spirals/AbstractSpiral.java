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


    void saveStrokeWidth(Paint paint) {
        savedStrokeWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(1+ (savedStrokeWidth / 10));
    }


    void recallStrokeWidth(Paint paint){
        paint.setStrokeWidth(savedStrokeWidth);
    }


    @Override
    public void onDeallocate(){
        super.onDeallocate();
        paintGroup.setStrokeWidth(savedStrokeWidth);
        paintGroup.setStyle(savedStyle);
    }


    @Override
    public void reinitialize(){
        super.reinitialize();
        savedStyle = Paint.Style.valueOf(paintGroup.getStyle());
        paintGroup.setStyle(Paint.Style.STROKE);
        savedStrokeWidth = paintGroup.getLineWidth();
        paintGroup.setStrokeWidth( 1 + (savedStrokeWidth/20));
    }

}
