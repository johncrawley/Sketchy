package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushDrawer;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class RectangleBrush extends AbstractBrush implements Brush {

    private float centerX, centerY;


    public RectangleBrush(Canvas canvas, PaintGroup paintGroup) {
        super(canvas, paintGroup, BrushShape.DRAG_RECTANGLE);
        brushDrawer = BrushDrawer.DRAG;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        centerX = x;
        centerY = y;
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        float diffX = (Math.abs(centerX) - Math.abs(x));
        float diffY = (Math.abs(centerY) - Math.abs(y));
        float left = centerX - diffX;
        float top = centerY - diffY;
        float right = centerX + diffX;
        float bottom = centerY + diffY;
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint){
        onTouchMove(x,y, paint);
    }


}