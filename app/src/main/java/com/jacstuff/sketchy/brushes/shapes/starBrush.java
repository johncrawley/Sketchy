package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class starBrush extends AbstractShape implements Brushable{


    public starBrush(){
       super(BrushShape.CIRCLE);
    }


    @Override
    public void generatePath(PointF p){
        //do nothing
    }


    @Override
    public void draw(PointF p, Canvas canvas, Paint paint){
        canvas.drawCircle(0,0, halfBrushSize, paint);
    }
}
