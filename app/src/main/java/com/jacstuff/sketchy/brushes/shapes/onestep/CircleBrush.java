package com.jacstuff.sketchy.brushes.shapes.onestep;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractShape;

public class CircleBrush extends AbstractShape{


    public CircleBrush(){
       super(BrushShape.CIRCLE);
    }


    @Override
    public void draw(PointF p, Canvas canvas, Paint paint){
        canvas.drawCircle(0,0, halfBrushSize, paint);
    }
}
