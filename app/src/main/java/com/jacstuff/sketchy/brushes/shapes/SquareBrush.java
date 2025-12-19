package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class SquareBrush extends AbstractShape implements Brushable{


   public SquareBrush(){
        super(BrushShape.SQUARE);
    }

    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        float left = - halfBrushSize;
        float top = - halfBrushSize;
        float right = left + brushSize;
        float bottom = top + brushSize;

        var strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
      //  drawShape(canvas, paint, strokePaint, (can,pt) -> can.drawRect(left, top, right, bottom, paint));
    }



    @Override
    public void generatePath(PointF p){
       //do nothing
    }


    @Override
    public void draw(PointF p, Canvas canvas, Paint paint){
       canvas.drawRect(-halfBrushSize, -halfBrushSize, halfBrushSize, halfBrushSize, paint);
    }
}
