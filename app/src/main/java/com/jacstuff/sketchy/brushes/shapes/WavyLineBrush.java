package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class WavyLineBrush extends AbstractBrush implements Brush {


    public WavyLineBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.WAVY_LINE);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){


        float width = halfBrushSize / 2f;

        Path path = new Path();

        path.moveTo(x - halfBrushSize, y);
        path.cubicTo(x, y- halfBrushSize, x,y + halfBrushSize, x + halfBrushSize, y);
        path.lineTo(x + halfBrushSize, y + width);
        float y1 = y + halfBrushSize + width;
        float y2 = y - halfBrushSize + width;
        path.cubicTo(x,y1, x, y2, x - halfBrushSize, y + width);
        path.close();

        canvas.drawPath(path, paint);
    }

}
