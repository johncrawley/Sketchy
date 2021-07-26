package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;

public class StraightLineBrush extends AbstractBrush implements Brush {


    public StraightLineBrush(){
        super(BrushShape.STRAIGHT_LINE);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){

        canvas.drawLine(-brushSize, 0, brushSize, 0, paint);
    }

}
