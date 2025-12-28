package com.jacstuff.sketchy.brushes.shapes.threestep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

// A shape that is drawn by 1. place, 2.adjust for size, and 3. release
public interface ThreeStepShape {

    void place(PointF p);
    void adjust(PointF p);
    void releaseAndDraw(PointF p, Canvas canvas, Paint paint);

}
