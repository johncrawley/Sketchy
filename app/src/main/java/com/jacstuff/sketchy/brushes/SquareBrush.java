package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;
import android.graphics.Paint;

public class SquareBrush extends AbstractBrush implements Brush {


    SquareBrush(Canvas canvas, Paint paint, int brushSize){
        super(canvas, paint, brushSize);
    }

    @Override
    public void onTouchDown(float x, float y){
        float left = x - halfBrushSize;
        float top = y - halfBrushSize;
        float right = left + brushSize;
        float bottom = top + brushSize;
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public void reset(){
        paint.setStrokeWidth(1);
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
