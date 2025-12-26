package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;

public abstract class AbstractPathShape extends AbstractShape{

    Path path;

    public AbstractPathShape(BrushShape brushShape){
        super(brushShape);
        path = new Path();
    }


    @Override
    public abstract void generatePath(PointF p);


    @Override
    public void draw(PointF point, Canvas canvas, Paint paint) {
        canvas.drawPath(path, paint);
    }

}
