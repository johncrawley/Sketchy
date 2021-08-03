package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;

public class CrescentBrush extends AbstractBrush implements Brush {

    private final Path path;
    private boolean hasSizeChanged = false;
    private float outerLeft, innerLeft, top, bottom, outerRight, innerRight;
    private  int quarterBrushSize;
    private float previousStrokeWidth;
    private float crescentStrokeAdjustment;

    public CrescentBrush(){
        super(BrushShape.CRESCENT);
        path = new Path();
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        readjustPointsOnSizeChanged();
        path.reset();
        assignStrokeWidth(paint);
        path.addArc( outerLeft, top, outerRight, bottom, 270, 180);
        path.addArc( innerLeft, top, innerRight, bottom, 90, -180);
        canvas.drawPath(path, paint);
        restoreStrokeWidth(paint);
    }


    private void assignStrokeWidth(Paint paint){
        if( paint.getStyle() == Paint.Style.FILL_AND_STROKE){
            previousStrokeWidth = paint.getStrokeWidth();
            paint.setStrokeWidth(1);
        }
    }


    private void restoreStrokeWidth(Paint paint){
        if(paint.getStyle() == Paint.Style.FILL_AND_STROKE){
            paint.setStrokeWidth(previousStrokeWidth);
        }
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(0,0, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        hasSizeChanged = true;
        quarterBrushSize = halfBrushSize / 2;
    }


    private void readjustPointsOnSizeChanged(){
        if(hasSizeChanged){
            hasSizeChanged = false;
            readjustPoints();
        }
    }


    private void readjustPoints(){
        float displacement = quarterBrushSize /1.3f;
        outerLeft = -halfBrushSize - quarterBrushSize;
        innerLeft  = outerLeft + displacement + crescentStrokeAdjustment;
        top = - halfBrushSize;
        bottom = halfBrushSize;
        outerRight = halfBrushSize - quarterBrushSize + 2;
        innerRight = outerRight - displacement - crescentStrokeAdjustment;
    }


    @Override
    public void notifyStrokeWidthChanged(){
        super.notifyStrokeWidthChanged();
        previousStrokeWidth = paintGroup.getLineWidth();
        crescentStrokeAdjustment = previousStrokeWidth / 1.7f;
        readjustPoints();
    }

}