package com.jacstuff.sketchy.brushes.shapes.line;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.CompositeCanvas;

public class DefaultLineDrawer implements LineDrawer {

    private Canvas canvas;
    private Paint paint;


    public DefaultLineDrawer(Canvas canvas, Paint paint){
        this.canvas = canvas;
        this.paint = paint;
    }


    @Override
    public void draw(float x1, float y1, float x2, float y2, int brushSize){
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

}
