package com.jacstuff.sketchy.brushes.shapes.onestep.pathshape;

import android.graphics.Path;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class DiamondBrush extends AbstractPathShape {

    private float width;

    public DiamondBrush() {
        super(BrushShape.DIAMOND);
    }


    @Override
    public void generatePath(PointF p) {
        path = new Path();
        path.moveTo(0, -halfBrushSize);
        path.lineTo(width, 0);
        path.lineTo(0, halfBrushSize);
        path.lineTo(-width, 0);
        path.close();
    }


    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
        width = halfBrushSize / 1.6f;
    }
}