package com.jacstuff.sketchy.brushes.shapes.spirals;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.Brush;

public class CrazySpiralBrush extends AbstractSpiral implements Brush {

    private float normalMultiplier, altMultiplier;
    private final Path path;
    private int quarterBrushSize;

    public CrazySpiralBrush(){
        super(BrushShape.CRAZY_SPIRAL);
        path = new Path();
    }


    public void setBrushSize(int size){
        super.setBrushSize(size);
        //float spiralProgression = this.brushSize /20f;
        float spiralProgression = mainViewModel.crazySpiralType * 3;
        normalMultiplier = 0.2f * spiralProgression;
        altMultiplier = 0.1f + spiralProgression;
        quarterBrushSize = 1 + (size / 4);
    }


    @Override
    public void recalculateDimensions(){
        setBrushSize(brushSize);
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        drawSpiral(paint);
    }


    private void drawSpiral(Paint paint){
        saveStrokeWidth(paint);
        path.reset();
        for (int i=0; i< getNumberOfSections(); i++) {
            drawSpiralSection(getMultiplier(), i);
        }
        canvas.drawPath(path, paint);
        recallStrokeWidth(paint);
    }


    private int getNumberOfSections(){
        return mainViewModel.isCrazySpiralAltModeEnabled ? quarterBrushSize / 4 : quarterBrushSize;
    }


    private float getMultiplier(){
        return mainViewModel.isCrazySpiralAltModeEnabled ? altMultiplier : normalMultiplier;
    }


    private void drawSpiralSection(float multiplier, int i){
        float angle = multiplier * i;
        path.lineTo((float)((1 + angle) * Math.cos(angle))
                ,(float)((1 + angle) * Math.sin(angle)));
    }
}