package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

public class FillStyleForLines extends AbstractStyle implements Style {


    @Override
    public void init(Paint paint, int brushSize) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setPathEffect(null);
        paint.setStrokeWidth(getAdjusted(brushSize));
    }

    @Override
    public void setBrushSize(Paint paint, int brushSize) {
        paint.setStrokeWidth(getAdjusted(brushSize) );
    }

    private float getAdjusted(int brushSize){
        return brushSize / 2f;
    }
}
