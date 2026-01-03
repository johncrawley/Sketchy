package com.jacstuff.sketchy.brushes.shapes.onestep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractShape;

public class SquareBrush extends AbstractShape{


   public SquareBrush(){
        super(BrushShape.SQUARE);
    }

    @Override
    public void draw(PointF p, Canvas canvas, Paint paint){
       System.out.println("halfbrushsize: " + halfBrushSize);
       canvas.drawRect(-halfBrushSize, -halfBrushSize, halfBrushSize, halfBrushSize, paint);
    }
}
