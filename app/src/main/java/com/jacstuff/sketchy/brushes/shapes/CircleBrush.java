package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;

public class CircleBrush extends AbstractBrush implements Brush {


    public CircleBrush(){
       super(BrushShape.CIRCLE);
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
            canvas.drawCircle(0,0, halfBrushSize, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(0,0, paint);
    }


}
