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
    public void onTouchDown(float x, float y, Paint paint){
        currentStyle.onDraw(paintGroup);
        canvas.drawCircle(x,y, halfBrushSize, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x, y, paint);
    }


}
