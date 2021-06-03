package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class WaveyLineBrush extends AbstractBrush implements Brush {


    public WaveyLineBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.WAVEY_LINE);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        float x1 = x - halfBrushSize;
        float x2 = x + halfBrushSize;
        float thirdPointOffset = ((float)halfBrushSize /2 * 3);
        float x3 = x - thirdPointOffset;
        float y3 = y - thirdPointOffset;

        Path path = new Path();

        path.moveTo(x - halfBrushSize,y);
        path.cubicTo(x, y- halfBrushSize, x,y + halfBrushSize, x + halfBrushSize, y);

        canvas.drawPath(path, paint);
    }

}
