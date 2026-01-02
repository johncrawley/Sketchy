package com.jacstuff.sketchy.brushes.shapes.onestep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractShape;

public class OvalBrush extends AbstractShape {

    private final RectF ovalRect;

    public OvalBrush(){
        super(BrushShape.OVAL);
        ovalRect = new RectF(-1,1,1,-1);
    }


    @Override
    public void draw(PointF point, Canvas canvas, Paint paint) {
        canvas.drawOval(ovalRect, paint);
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