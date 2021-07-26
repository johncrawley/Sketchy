
package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Paint;
import android.graphics.RectF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class RoundedRectangleBrush extends AbstractBrush implements Brush {

    private final RectF rect;
    private int rounding;

    public RoundedRectangleBrush(){
        super(BrushShape.ROUNDED_RECTANGLE);
        rect = new RectF(1,1,1,1);
    }

    @Override
    public void setBrushSize(int brushSize){
        final int ROUNDING_FACTOR = 5;
        super.setBrushSize(brushSize);
        rounding = brushSize < 5 ? 1 : brushSize / ROUNDING_FACTOR;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        int left =  - halfBrushSize;
        int top =   - halfBrushSize;

        rect.left = left;
        rect.top = top;
        rect.right = left + brushSize;
        rect.bottom = top + brushSize;

        canvas.drawRoundRect(rect, rounding, rounding, paint);
    }


}
