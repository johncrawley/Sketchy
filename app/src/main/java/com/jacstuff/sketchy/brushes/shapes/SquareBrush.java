package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;

public class SquareBrush extends AbstractBrush implements Brush {


   public SquareBrush(){
        super(BrushShape.SQUARE);
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        float left =  - halfBrushSize;
        float top = - halfBrushSize;
        float right = left + brushSize;
        float bottom = top + brushSize;
        canvas.drawRect(left, top, right, bottom, paint);
    }

}
