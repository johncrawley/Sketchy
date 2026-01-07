package com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.spirals;

import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.onestep.pathshape.AbstractPathShape;

public abstract class AbstractSpiral extends AbstractPathShape {

    Paint.Style savedStyle;
    private float savedStrokeWidth;


    public AbstractSpiral(BrushShape brushShape){
        super(brushShape);
        isBrushLiableToFlicker = true;
    }


    void saveSettings(Paint paint) {
        savedStrokeWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(1+ (savedStrokeWidth / 8));
        savedStyle = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);
    }


    void recallSettings(Paint paint){
        paint.setStyle(savedStyle);
        paint.setStrokeWidth(savedStrokeWidth);
    }

    public void reinitialize(){

        // maybe have a reinitialize method in AbstractShape??
    }

}
