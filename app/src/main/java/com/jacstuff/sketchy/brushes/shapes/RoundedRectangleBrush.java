
package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class RoundedRectangleBrush extends AbstractBrush implements Brush {

    private RectF rect;
    private int rounding;

    public RoundedRectangleBrush(Canvas canvas, Paint paint, int brushSize){
        super(canvas, paint);
        rect = new RectF(1,1,1,1);
        setBrushSize(brushSize);
    }

    @Override
    public void setBrushSize(int brushSize){
        final int ROUNDING_FACTOR = 5;
        super.setBrushSize(brushSize);
        rounding = brushSize < 5 ? 1 : brushSize / ROUNDING_FACTOR;
    }

    @Override
    public void onTouchDown(float x, float y){
        int left = (int) x - halfBrushSize;
        int top =  (int)y - halfBrushSize;

        rect.left = left;
        rect.top = top;
        rect.right = left + brushSize;
        rect.bottom = top + brushSize;

        canvas.drawRoundRect(rect, rounding, rounding, paint);
    }


    @Override
    public void onTouchMove(float x, float y){
        onTouchDown(x ,y);
    }


    @Override
    public void onTouchUp(float x, float y){
        // do nothing
    }


}
