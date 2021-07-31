package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;

public class XBrush extends AbstractBrush implements Brush {


    private float halfLength;

    public XBrush() {
        super(BrushShape.X);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint) {

        Path path = new Path();
        path.moveTo(-halfLength, -halfLength);
        path.lineTo(halfLength, halfLength);

        path.moveTo(halfLength, -halfLength);
        path.lineTo(-halfLength, halfLength);
        canvas.drawPath(path, paint);
    }


    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
        halfLength = -halfBrushSize * 1.414213562f;
    }
}