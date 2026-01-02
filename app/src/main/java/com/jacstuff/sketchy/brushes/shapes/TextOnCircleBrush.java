package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.onestep.TextBrush;


public class TextOnCircleBrush extends TextBrush {



    public TextOnCircleBrush(){
        super(BrushShape.TEXT_ON_CIRCLE);
    }


    @Override
    public void draw(PointF p, Canvas canvas, Paint paint) {
        path = new Path();
        path.moveTo(0,0);
        path.addCircle(0,0, brushSize * 2, Path.Direction.CW);

        paint.setLinearText(true);
        float offset = 0f;
        canvas.drawTextOnPath(text, path, offset, offset, paint);
    }


}