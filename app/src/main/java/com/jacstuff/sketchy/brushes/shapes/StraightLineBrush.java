package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.initializer.StraightLineInitializer;


public class StraightLineBrush extends AbstractBrush implements Brush {

    private int adjustedHalfLength;
    private final float maxDimensionFactor;
    private final float adjustedBrushFactor;


    public StraightLineBrush(int maxBrushSize, int maxDimension){
        super(BrushShape.STRAIGHT_LINE);
        this.adjustedBrushFactor = 100f/ maxBrushSize;
        this.maxDimensionFactor = maxDimension / 170f; // dividing by 200 would cover half the max dimension
                                                        // but dividing by a little less to cover diagonal
        brushInitializer = new StraightLineInitializer();
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        canvas.drawLine(-adjustedHalfLength, 0, adjustedHalfLength, 0, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        adjustedHalfLength = (int)(maxDimensionFactor * adjustedBrushFactor * brushSize);
    }

}
