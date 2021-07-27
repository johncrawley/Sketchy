package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;

public class ArcBrush extends AbstractBrush implements Brush {


    private int arcHeight;
    private float quarterBrushSize;

    public ArcBrush(){
        super(BrushShape.ARC);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        Path path = new Path();
        path.addArc(-halfBrushSize, -quarterBrushSize, halfBrushSize, arcHeight - quarterBrushSize, 200, 140);
        path.close();
        canvas.drawPath(path, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x, y, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        arcHeight = (int)(halfBrushSize * 2.25);
        quarterBrushSize = halfBrushSize / 2f;
    }
}
