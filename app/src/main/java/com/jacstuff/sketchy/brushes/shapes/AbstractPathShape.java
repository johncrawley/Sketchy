package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public abstract class AbstractPathShape extends AbstractShape{

    Path path;

    @Override
    public abstract void generatePath(PointF p);
    }

    @Override
    public void draw(PointF point, Canvas canvas, Paint paint) {

    }

}
