package com.jacstuff.sketchy.brushes.shapes;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.shapes.initializer.DragRectInitializer;

public class RectangleBrush extends AbstractBrush implements Brush {

    private Point touchDownPoint;


    public RectangleBrush() {
        super(BrushShape.DRAG_RECTANGLE);
        brushInitializer = new DragRectInitializer();
        drawerType = DrawerFactory.Type.DRAG_RECT;
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        this.touchDownPoint = p;
    }


    @Override
    public void onTouchMove(float x2, float y2, Paint paint) {
        float width = Math.abs(x2 - touchDownPoint.x);
        float height =  Math.abs(y2 - touchDownPoint.y );
        float halfWidth = width /2;
        float halfHeight =  height /2;
        paintView.getPaintHelperManager().getGradientHelper().recalculateGradientLengthForRectangle(touchDownPoint, width, height);
        canvas.drawRect(-halfWidth, -halfHeight, halfWidth, halfHeight, paint);
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint){
        onTouchMove(x,y, paint);
    }

}