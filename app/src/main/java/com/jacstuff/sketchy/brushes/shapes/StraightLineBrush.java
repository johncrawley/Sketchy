package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;
import com.jacstuff.sketchy.brushes.shapes.initializer.StraightLineInitializer;

public class StraightLineBrush extends AbstractBrush implements Brush {


    public StraightLineBrush(){
        super(BrushShape.STRAIGHT_LINE);
        brushInitializer = new StraightLineInitializer();
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        int halfLength = (int)(brushSize * 2.2);
        canvas.drawLine(-halfLength, 0, halfLength, 0, paint);
    }

}
