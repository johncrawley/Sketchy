package com.jacstuff.sketchy.paint;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.TouchPoint;
import java.util.List;

public interface PaintView {

    void paint(Canvas canvas, Paint paint, List<TouchPoint> touchPoints);
}
