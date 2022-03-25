package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;

public class CrazySpiralBrush extends AbstractBrush implements Brush {

    private float normalMultiplier, altMultiplier;
    private final Path path;

    public CrazySpiralBrush(){
        super(BrushShape.CRAZY_SPIRAL);
        path = new Path();
    }


    public void setBrushSize(int size){
        super.setBrushSize(size);
        float spiralProgression = this.brushSize /20f;
        normalMultiplier = 0.2f * spiralProgression;
        altMultiplier = 0.1f + spiralProgression;

    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        drawSpiral(paint);
    }


    private void drawSpiral(Paint paint){
        path.reset();
        float multiplier = mainViewModel.isCrazySpiralAltModeEnabled ? altMultiplier : normalMultiplier;
        for (int i=0; i< halfBrushSize; i++) {
            drawSpiralSection(multiplier, i);
        }
        canvas.drawPath(path, paint);
    }


    private void drawSpiralSection(float multiplier, int i){
        float angle = multiplier * i;
        path.lineTo((float)((1 + angle) * Math.cos(angle))
                ,(float)((1 + angle) * Math.sin(angle)));
    }
}