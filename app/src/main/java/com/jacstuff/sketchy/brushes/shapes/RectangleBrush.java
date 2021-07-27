package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;

public class RectangleBrush extends AbstractBrush implements Brush {

    private float x1, y1;

    public RectangleBrush() {
        super(BrushShape.DRAG_RECTANGLE);
        drawerType = DrawerFactory.Type.DRAG_RECT;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        x1 = x;
        y1 = y;
    }


    @Override
    public void onTouchMove(float x2, float y2, Paint paint) {
        float greaterX = Math.max(x1,x2);
        float lesserX = Math.min(x1,x2);
        float greaterY = Math.max(y1,y2);
        float lesserY = Math.min(y1, y2);

        float halfWidth = (greaterX - lesserX) / -2;
        float halfHeight = (greaterY - lesserY) / -2;

        canvas.drawRect(-halfWidth, -halfHeight, halfWidth, halfHeight, paint);
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint){
        onTouchMove(x,y, paint);
    }

}