package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.easel.Easel;
import com.jacstuff.sketchy.brushes.shapes.Brushable;
import com.jacstuff.sketchy.paintview.PaintView;

public class SimpleShapeDrawer extends AbstractShapeDrawer{


    public SimpleShapeDrawer(PaintView paintView){
        super(paintView);
    }


    @Override
    public void down(PointF p, Easel easel) {
        drawToCanvas(p, easel);
    }


    @Override
    public void move(PointF p, Easel easel) {
        drawToCanvas(p, easel);
    }


    @Override
    public void setBrushShape(Brushable brushShape) {

    }


    void drawToCanvas(PointF p, Easel easel){
        brushShape.generatePath(p);
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(p.x, p.y, paint);
        }
        else{
            rotateAndDraw(p, easel);
        }
        paintView.invalidate();
    }


    public void rotateAndDraw(PointF p, Easel easel){
        canvas.save();
        canvas.translate(p.x, p.y);

        canvas.rotate(paintHelperManager.getAngleHelper().getFineAngle());

        for(var paint : easel.getActivePaints()){
            brushShape.draw(p, canvas, paint);
        }
        canvas.restore();
    }


    public void drawKaleidoscopeSegment(float x, float y, Paint paint){
        var p = new PointF(x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY());
        //rotateAndDraw(p, paint);
    }
}
