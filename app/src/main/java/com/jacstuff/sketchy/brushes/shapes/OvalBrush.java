package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class OvalBrush extends AbstractBrush implements Brush {

    private final RectF ovalRect;

    public OvalBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.OVAL);
        ovalRect = new RectF(-1,1,1,-1);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        canvas.drawOval(ovalRect, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(0,0, paint);
    }



    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        float height = halfBrushSize / 1.5f;
        ovalRect.left = -halfBrushSize;
        ovalRect.right = halfBrushSize;
        ovalRect.top = height;
        ovalRect.bottom = -height;

    }



}