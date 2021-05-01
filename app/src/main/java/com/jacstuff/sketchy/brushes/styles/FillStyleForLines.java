package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class FillStyleForLines extends AbstractStyle implements Style {


    @Override
    public void init(PaintGroup paint, int brushSize) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setPathEffect(null);
    }

    @Override
    public void setBrushSize(PaintGroup paint, int brushSize) {
      //  paint.setStrokeWidth(getAdjusted(brushSize) );
    }

    private float getAdjusted(int brushSize){
        return brushSize / 2f;
    }
}
