package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class CrescentBrush extends AbstractBrush implements Brush {

    private final Path path;
    private boolean hasSizeChanged = false;
    private float outerLeft, innerLeft, top, bottom, outerRight, innerRight;
    private  int quarterBrushSize;
    private final float offsetX;// paint.getStrokeWidth();
    private final int repeats;

    public CrescentBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.CRESENT);
        path = new Path();
        offsetX = 1;
        repeats = 10;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        readjustPointsOnSizeChanged();
        path.reset();
        path.addArc( outerLeft, top, outerRight, bottom, 270, 180);
        path.addArc( innerLeft, top, innerRight, bottom, 90, -180);
        canvas.drawPath(path, paint);

        // if we close the above path, it draws a semicircle,
        // so we offset the path a few times and draw it to try to fill up the gap
        if(paint.getStyle() == Paint.Style.FILL_AND_STROKE){
            for(int i=0; i < repeats; i++){
                path.offset(-offsetX,0);
                canvas.drawPath(path, paint);
            }
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
        if(!hasSizeChanged){
            return;
        }
        hasSizeChanged = false;
        float displacement = quarterBrushSize /1.3f;
        outerLeft = -halfBrushSize - quarterBrushSize;
        innerLeft  = outerLeft + displacement;
        top = - halfBrushSize;
        bottom = halfBrushSize;
        outerRight = halfBrushSize - quarterBrushSize + 2;
        innerRight = outerRight - displacement;
    }





}