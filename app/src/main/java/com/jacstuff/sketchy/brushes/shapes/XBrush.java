package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class XBrush extends AbstractPathShape {

    private float halfLength;

    public XBrush() {
        super(BrushShape.X);
    }


    @Override
    public void generatePath(PointF p){
        path = new Path();
        path.moveTo(-halfLength, -halfLength);
        path.lineTo(halfLength, halfLength);

        path.moveTo(halfLength, -halfLength);
        path.lineTo(-halfLength, halfLength);
    }


    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
        halfLength = halfBrushSize;
    }
}