package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

public class PaintFactory {

    public static Paint createPaint(int color){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setElegantTextHeight(true);
        paint.setColor(color);
        return paint;
    }
}
