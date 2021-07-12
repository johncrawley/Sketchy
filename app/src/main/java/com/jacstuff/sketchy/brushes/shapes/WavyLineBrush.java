package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class WavyLineBrush extends AbstractBrush implements Brush {


    Path path;

    public WavyLineBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.WAVY_LINE);
        path = new Path();
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        path.reset();
        path.moveTo( - halfBrushSize, 0);
        path.cubicTo(0, -halfBrushSize, 0, halfBrushSize, halfBrushSize, 0);
        float y0 = halfBrushSize / 2f;
        path.lineTo( halfBrushSize, y0);
        float y1 = halfBrushSize + y0;
        float y2 = - halfBrushSize + y0;
        path.cubicTo(0, y1, 0, y2, - halfBrushSize, y0);
        path.close();

        canvas.drawPath(path, paint);
    }

}
