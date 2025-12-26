package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class SemicircleBrush extends AbstractPathShape {

    private int arcHeight;

    public SemicircleBrush(){
        super(BrushShape.SEMICIRCLE);
    }


    @Override
    public void generatePath(PointF p){
        path.reset();
        path.addArc(-halfBrushSize, -quarterBrushSize, halfBrushSize, arcHeight - quarterBrushSize, 200, 140);
        path.close();
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        arcHeight = (int)(halfBrushSize * 2.25);
    }
}
