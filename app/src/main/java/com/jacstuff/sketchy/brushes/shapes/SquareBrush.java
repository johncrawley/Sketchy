package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class SquareBrush extends AbstractBrush implements Brush {


   public SquareBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.SQUARE);
    }


    @Override
    public void onTouchDown(float x, float y, Paint paint){
        currentStyle.onDraw(paintGroup);
        float left = x - halfBrushSize;
        float top = y - halfBrushSize;
        float right = left + brushSize;
        float bottom = top + brushSize;
        canvas.drawRect(left, top, right, bottom, paint);
    }

}
