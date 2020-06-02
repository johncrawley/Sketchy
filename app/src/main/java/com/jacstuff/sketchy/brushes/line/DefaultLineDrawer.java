package com.jacstuff.sketchy.brushes.line;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DefaultLineDrawer implements LineDrawer {

    private Canvas canvas;
    private Paint paint;

    public DefaultLineDrawer(Canvas canvas, Paint paint){
        this.canvas = canvas;
        this.paint = paint;
    }
    public void draw(float x1, float y1, float x2, float y2, int brushSize){
        canvas.drawLine(x1, y1, x2, y2, paint);
    }
}
