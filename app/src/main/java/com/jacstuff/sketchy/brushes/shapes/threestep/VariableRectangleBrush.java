package com.jacstuff.sketchy.brushes.shapes.threestep;


import static com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType.USE_SET_VALUE;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerType;

public class VariableRectangleBrush extends AbstractShape implements ThreeStepShape {

    private PointF touchDownPoint;


    public VariableRectangleBrush() {
        super(BrushShape.DRAG_RECTANGLE);
        drawerType = DrawerType.DRAG_RECT;
        isDrawnFromCenter = false;
        usesBrushSizeControl = false;
        shadowOffsetType = USE_SET_VALUE;
    }


    @Override
    public void place(PointF p) {
        this.touchDownPoint = new PointF(p.x, p.y);
    }


    @Override
    public void adjust(PointF p, Canvas canvas, Paint paint) {
    }


    @Override
    public void draw(PointF p, Canvas canvas, Paint paint) {
        float width = p.x - touchDownPoint.x;
        float height = p.y - touchDownPoint.y;
        canvas.drawRect(0, 0, width, height, paint);
        float calculatedSize = ((touchDownPoint.x - p.x) + (touchDownPoint.y - p.y)) * 2;
        setBrushSize( (int)calculatedSize);
    }


    public boolean isUsingPlacementHelper(){
        return false;
    }

}