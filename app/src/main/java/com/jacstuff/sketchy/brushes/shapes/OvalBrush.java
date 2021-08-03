package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class OvalBrush extends AbstractBrush implements Brush {

    private final RectF ovalRect;

    public OvalBrush(){
        super(BrushShape.OVAL);
        ovalRect = new RectF(-1,1,1,-1);
    }

    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
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