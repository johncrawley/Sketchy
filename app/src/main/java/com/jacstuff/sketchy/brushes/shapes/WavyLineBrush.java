package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class WavyLineBrush extends AbstractPathShape{

    public WavyLineBrush(){
        super(BrushShape.WAVY_LINE);
    }


    @Override
    public void generatePath(PointF p){
        path.reset();
        path.moveTo( - halfBrushSize, 0);
        path.cubicTo(0, -halfBrushSize, 0, halfBrushSize, halfBrushSize, 0);
        float y0 = halfBrushSize / 2f;
        path.lineTo( halfBrushSize, y0);
        float y1 = halfBrushSize + y0;
        float y2 = - halfBrushSize + y0;
        path.cubicTo(0, y1, 0, y2, - halfBrushSize, y0);
        path.close();
    }

}
