package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.AbstractBrush;
import com.jacstuff.sketchy.brushes.Brush;

public class CircleBrush extends AbstractBrush implements Brush {



    public CircleBrush(Canvas canvas, Paint paint, int brushSize){
       super(canvas, paint, brushSize);
    }


    @Override
    public void onTouchDown(float x, float y){
        canvas.drawCircle(x,y, halfBrushSize, paint);
    }

    @Override
    public void reset(){
        paint.setStrokeWidth(1);
    }



    @Override
    public void onTouchMove(float x, float y){
        onTouchDown(x, y);
    }

    @Override
    public void onTouchUp(float x, float y){
        //do nothing
    }

}
