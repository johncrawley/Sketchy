package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;

public class StraightLineBrush extends AbstractBrush implements Brush {


    public StraightLineBrush(){
        super(BrushShape.STRAIGHT_LINE);
        brushInitializer = new LineInitializer();
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){

        canvas.drawLine(-brushSize, 0, brushSize, 0, paint);
    }

}
