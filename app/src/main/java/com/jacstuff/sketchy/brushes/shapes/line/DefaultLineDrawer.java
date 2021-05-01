package com.jacstuff.sketchy.brushes.shapes.line;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class DefaultLineDrawer implements LineDrawer {

    private Canvas canvas;

    public DefaultLineDrawer(Canvas canvas){
        this.canvas = canvas;
    }


    @Override
    public void draw(float x1, float y1, float x2, float y2, int brushSize, Paint paint){
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

}
