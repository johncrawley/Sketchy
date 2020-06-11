package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;

public interface Style {


    void init(Paint p, int brushSize);
    void setBrushSize(Paint paint, int brushSize);
    void onDraw(Paint paint);

}
