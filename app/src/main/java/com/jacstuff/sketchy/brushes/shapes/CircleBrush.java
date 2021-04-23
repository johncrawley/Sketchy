package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;

public class CircleBrush extends AbstractBrush implements Brush {


    public CircleBrush(Canvas canvas, Paint paint){
       super(canvas, paint, BrushShape.CIRCLE);
    }


    @Override
    public void onTouchDown(float x, float y){
        onTouchDown(x,y, paint);
    }

    @Override
    public void onTouchDown(float x, float y, Paint paint){
        canvas.drawCircle(x,y, halfBrushSize, paint);
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
