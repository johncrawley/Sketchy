package com.jacstuff.sketchy.brushes.shapes;


import static com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType.USE_SET_VALUE;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;

public class RectangleBrush extends AbstractBrush implements Brush {

    private Point touchDownPoint;


    public RectangleBrush() {
        super(BrushShape.DRAG_RECTANGLE);
        drawerType = DrawerFactory.Type.DRAG_RECT;
        isDrawnFromCenter = false;
        usesBrushSizeControl = false;
        shadowOffsetType = USE_SET_VALUE;
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        this.touchDownPoint = p;
    }


    @Override
    public void onTouchMove(float x2, float y2, Paint paint) {
        float width = x2 - touchDownPoint.x;
        float height =  y2 - touchDownPoint.y;
        canvas.drawRect(0, 0, width, height, paint);
        float calculatedSize = ((touchDownPoint.x - x2) + (touchDownPoint.y - y2))*2;
        setBrushSize( (int)calculatedSize);
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint){
        onTouchMove(x,y, paint);
    }


    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }

}