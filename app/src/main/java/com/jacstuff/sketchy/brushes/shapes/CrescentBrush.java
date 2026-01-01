package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class CrescentBrush extends AbstractPathShape {

    private boolean hasSizeChanged = false;
    private float outerLeft, innerLeft, top, bottom, outerRight, innerRight;
    private  int quarterBrushSize;
    private float previousStrokeWidth;
    private float crescentStrokeAdjustment;
    private float previousLineWidth;

    public CrescentBrush(){
        super(BrushShape.CRESCENT);
        path = new Path();
    }


    @Override
    public void generatePath(PointF p){
        readjustPointsOnSizeChanged();
        path.reset();
        path.addArc( outerLeft, top, outerRight, bottom, 270, 180);
        path.addArc( innerLeft, top, innerRight, bottom, 90, -180);

    }


    @Override
    public void draw(PointF p, Canvas canvas, Paint paint){
        assignStrokeWidth(paint);
        canvas.drawPath(path, paint);
        restoreStrokeWidth(paint);
        previousLineWidth = paint.getStrokeWidth();
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


    public void notifyStrokeWidthChanged(){
       // super.notifyStrokeWidthChanged();
        crescentStrokeAdjustment = previousStrokeWidth / 1.7f;
        readjustPoints();
    }

}