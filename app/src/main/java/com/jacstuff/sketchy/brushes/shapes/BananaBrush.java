package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;

public class BananaBrush extends AbstractBrush implements Brush {

    private final Path path;
    private final Point bottomPoint;
    private final Point topPoint;
    private final Point upperDipPoint;
    private final Point lowerDipPoint;
    private boolean hasSizeChanged = false;

    public BananaBrush(){
        super(BrushShape.BANANA);
        path = new Path();
        bottomPoint = new Point(0,0);
        topPoint = new Point(0,0);
        upperDipPoint = new Point(0,0);
        lowerDipPoint = new Point(0,0);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        readjustPointsOnSizeChanged();
        path.reset();
        path.moveTo(bottomPoint.x, bottomPoint.y);
        path.quadTo(upperDipPoint.x, upperDipPoint.y, topPoint.x, topPoint.y);
        path.quadTo(lowerDipPoint.x, lowerDipPoint.y, bottomPoint.x, bottomPoint.y);
        path.close();
        canvas.drawPath(path, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(0,0, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        hasSizeChanged = true;
    }

    private void readjustPointsOnSizeChanged(){
        if(!hasSizeChanged){
            return;
        }
        hasSizeChanged = false;
        int adjustment = brushSize /8;
        int third = halfBrushSize /3;
        bottomPoint.x = -halfBrushSize + adjustment ;
        bottomPoint.y = halfBrushSize;
        topPoint.x = 0 ;
        topPoint.y = -halfBrushSize + adjustment;
        upperDipPoint.x = -halfBrushSize /6;
        upperDipPoint.y = -halfBrushSize / 6;
        upperDipPoint.x = halfBrushSize/3;
        upperDipPoint.y = third;
        lowerDipPoint.x = halfBrushSize - adjustment;
        lowerDipPoint.y = halfBrushSize - adjustment;
    }





}