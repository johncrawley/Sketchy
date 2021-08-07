package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;

public class RectangleBrush extends AbstractBrush implements Brush {

    private float touchDownX, touchDownY;

    public RectangleBrush() {
        super(BrushShape.DRAG_RECTANGLE);
        drawerType = DrawerFactory.Type.DRAG_RECT;
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        touchDownX = p.x;
        touchDownY = p.y;
    }


    @Override
    public void onTouchMove(float x2, float y2, Paint paint) {
        float width = Math.abs(x2 - touchDownX);
        float height =  Math.abs(y2 - touchDownY );
        float halfWidth = width /2;
        float halfHeight =  height /2;
        paintView.getPaintHelperManager().getGradientHelper().recalculateGradientLengthForRectangle(width, height);
        canvas.drawRect(-halfWidth, -halfHeight, halfWidth, halfHeight, paint);
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint){
        onTouchMove(x,y, paint);
    }

}