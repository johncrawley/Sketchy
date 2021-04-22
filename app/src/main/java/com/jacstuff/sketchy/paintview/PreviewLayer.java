package com.jacstuff.sketchy.paintview;

public interface PreviewLayer {
    void updateOnMove(float x, float y);
    void onTouchUp();
    void setColor(int color);
}
