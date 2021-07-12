package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class PathBrush extends AbstractBrush implements Brush {

    private float previousX, previousY;

    /*

        Will need a special case for shadows:
            so, disable shadows on brush down and brush move
            - but save and append to path, with each brush move
            - on brush up, draw the whole shadow path, then the whole normal path again.
            - then clear the saved path

     */

    public PathBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.PATH);
    }


    public void onBrushTouchDown(float x, float y, Paint paint){
        previousX = x;
        previousY = y;
        Path path = new Path();
        path.moveTo(0,0);
        canvas.drawPath(path, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        currentStyle.onDraw();
        Path path = new Path();
        path.moveTo(previousX -x, previousY -y);
        path.lineTo(0,0);
        canvas.drawPath(path, paint);
        previousX = x;
        previousY = y;
    }


}