package com.jacstuff.sketchy.brushes.shapes.threestep;

import static com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType.USE_SET_VALUE;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerType;
import com.jacstuff.sketchy.utils.MathUtils;

public class VariableCircleBrush extends AbstractShape implements ThreeStepShape{

    private PointF touchDownPoint;
    private float radius = 1;


    public VariableCircleBrush() {
        super(BrushShape.VARIABLE_CIRCLE);
        drawerType = DrawerType.DRAG_RECT;
        //isDrawnFromCenter = true;
        usesBrushSizeControl = false;
        shadowOffsetType = USE_SET_VALUE;
    }


    @Override
    public void place(PointF p) {
        this.touchDownPoint = p;
    }


    @Override
    public void adjust(PointF p, Canvas canvas, Paint paint) {
        radius = MathUtils.getDistance(touchDownPoint.x, touchDownPoint.y, p.x, p.y);
        canvas.drawCircle(0, 0, radius, paint);
        float calculatedSize = ((touchDownPoint.x - p.x) + (touchDownPoint.y - p.y)) * 2;
        setBrushSize( (int)calculatedSize);
    }


    @Override
    public void draw(PointF point, Canvas canvas, Paint paint) {
        canvas.drawCircle(0, 0, radius, paint);
    }


    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){

    }


    public void onTouchMove(float x2, float y2, Paint paint) {

    }


    public void onTouchUp(float x, float y, Paint paint){
        onTouchMove(x,y, paint);
    }


    public boolean isUsingPlacementHelper(){
        return false;
    }

}