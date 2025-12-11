package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.Brushable;
import com.jacstuff.sketchy.paintview.PaintView;

public class SimpleShapeDrawer extends AbstractShapeDrawer{


    public SimpleShapeDrawer(PaintView paintView){
        super(paintView);
    }


    @Override
    public void down(PointF point, Paint paint) {
        drawToCanvas(point.x, point.y, paint);
    }


    @Override
    public void move(PointF point, Paint paint) {

    }


    @Override
    public void up(PointF point, Paint paint) {

    }


    @Override
    public void setBrushShape(Brushable brushShape) {

    }


    void drawToCanvas(float x, float y, Paint paint){
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(x,y, paint);
        }
        else if(paintHelperManager.getTileHelper().isEnabled()){
            paintHelperManager.getTileHelper().draw(x,y, this);
        }
        else{
            rotateAndDraw(x,y, paint);
        }
        paintView.invalidate();
    }


    public void rotateAndDraw(float x, float y, Paint paint){
        canvas.save();
        canvas.translate(x, y);
        Point p = new Point((int)x, (int)y);
        canvas.rotate(paintHelperManager.getAngleHelper().getFineAngle());
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchDown(p, canvas, paintView.getShadowPaint());
        }
        brush.onTouchDown(p, canvas, paint);
        canvas.restore();
    }


    public void drawKaleidoscopeSegment(float x, float y, Paint paint){
        rotateAndDraw(x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY(), paint);
    }
}
