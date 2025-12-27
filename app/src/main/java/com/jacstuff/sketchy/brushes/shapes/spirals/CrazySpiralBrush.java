package com.jacstuff.sketchy.brushes.shapes.spirals;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class CrazySpiralBrush extends AbstractSpiral {

    private float normalMultiplier, altMultiplier;
    private final Path path;
    private int quarterBrushSize;
    private final float[] normalProgressionArray, altProgressionArray;

    public CrazySpiralBrush(){
        super(BrushShape.CRAZY_SPIRAL);
        path = new Path();
        normalProgressionArray = new float[]{9, 11, 16,18, 22, 27, 31, 33};
        altProgressionArray = new float[]{9, 12, 13.4f, 15, 16.93f, 20, 21, 22.95f};
    }


    public void setBrushSize(int size){
        updateMultipliers();
        quarterBrushSize = 1 + (size / 4);
    }


    private void updateMultipliers(){
      // normalMultiplier = 0.2f * normalProgressionArray[viewModel.crazySpiralType];
        normalMultiplier = 0.2f * 3;
      //  altMultiplier = 0.1f + altProgressionArray[viewModel.crazySpiralType];
    }


    @Override
    public void generatePath(PointF p) {
        for (int i=0; i< getNumberOfSections(); i++) {
            generateSpiralSection(getMultiplier(), i);
        }
    }


    @Override
    public void draw(PointF p, Canvas canvas, Paint paint){
        saveSettings(paint);
        path.reset();

        canvas.drawPath(path, paint);
        recallSettings(paint);
    }


    private int getNumberOfSections(){
        return  quarterBrushSize; //viewModel.isCrazySpiralAltModeEnabled ? quarterBrushSize / 4 : quarterBrushSize;
    }


    private float getMultiplier(){
        return normalMultiplier; //viewModel.isCrazySpiralAltModeEnabled ? altMultiplier : normalMultiplier;
    }


    private void generateSpiralSection(float multiplier, int i){
        float angle = multiplier * i;
        path.lineTo((float)((1 + angle) * Math.cos(angle))
                ,(float)((1 + angle) * Math.sin(angle)));
    }

}