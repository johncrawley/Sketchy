package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class StraightLineBrush extends AbstractBrush implements Brush {


    public StraightLineBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.STRAIGHT_LINE);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){

        canvas.drawLine(-brushSize, 0, brushSize, 0, paint);
    }

}
