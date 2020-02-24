package com.jacstuff.sketchy.state;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.TouchPoint;

import java.util.List;

public interface StateManager {

    public void handleTouchPoints(List<TouchPoint> touchPoints);
    public void update();
    public void draw(Canvas canvas, Paint paint);
}
