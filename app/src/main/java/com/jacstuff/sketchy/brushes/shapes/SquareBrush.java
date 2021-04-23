package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;

public class SquareBrush extends AbstractBrush implements Brush {


   public SquareBrush(Canvas canvas, Paint paint){
        super(canvas, paint, BrushShape.SQUARE);
    }


    @Override
    public void onTouchDown(float x, float y){
        onTouchDown(x,y, paint);
    }

    @Override
    public void onTouchDown(float x, float y, Paint paint){
        float left = x - halfBrushSize;
        float top = y - halfBrushSize;
        float right = left + brushSize;
        float bottom = top + brushSize;
        canvas.drawRect(left, top, right, bottom, paint);
    }


    @Override
    public void onTouchMove(float x, float y){
        onTouchDown(x ,y);
    }


    @Override
    public void onTouchUp(float x, float y){
        // do nothing
    }

}
