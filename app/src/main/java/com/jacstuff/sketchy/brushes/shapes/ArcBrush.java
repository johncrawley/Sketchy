package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class ArcBrush extends AbstractBrush implements Brush {


    private int arcHeight;

    public ArcBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.TEXT);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        // left, top, right, bottom
        boolean useCenter = paint.getStyle() != Paint.Style.STROKE;
        canvas.drawArc(x - halfBrushSize, y, x + halfBrushSize, y + arcHeight, 200, 140, false, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x, y, paint);
    }



    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        arcHeight = (int)(halfBrushSize * 2.25);
    }
}
