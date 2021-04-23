package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;

public class StraightLineBrush extends AbstractBrush implements Brush {


    public StraightLineBrush(Canvas canvas, Paint paint){
        super(canvas, paint, BrushShape.STRAIGHT_LINE);
    }


    @Override
    public void onTouchDown(float x, float y){
        onTouchDown(x,y, paint);
    }

    @Override
    public void onTouchDown(float x, float y, Paint paint){
        float x1 = x - halfBrushSize;
        float x2 = x + brushSize;
        canvas.drawLine(x1, y, x2, y, paint);
    }


    @Override
    public void onTouchMove(float x, float y){
        onTouchDown(x ,y);
    }


    @Override
    public void onTouchUp(float x, float y){
        // do nothing
    }

    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
    }


}
