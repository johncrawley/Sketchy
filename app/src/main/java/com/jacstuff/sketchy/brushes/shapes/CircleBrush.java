package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class CircleBrush extends AbstractBrush implements Brush {


    public CircleBrush(Canvas canvas, PaintGroup paintGroup){
       super(canvas, paintGroup, BrushShape.CIRCLE);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        canvas.drawCircle(x,y, halfBrushSize, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x, y, paint);
    }


}
