package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.easel.Easel;
import com.jacstuff.sketchy.paintview.PaintView;

public class SimpleShapeDrawer extends AbstractShapeDrawer{


    public SimpleShapeDrawer(PaintView paintView){
        super(paintView);
    }


    @Override
    public void down(PointF p, Easel easel) {
       // paintView.enablePreviewLayer();
        drawToCanvas(p, easel);
    }

    /*
    public void down(float x1, float y1, Paint paint) {
        placementHelper.registerTouchDown(x1, y1);
        calculateXYFrom(x1, y1);
        updateColorGradientAndAngle(x,y);
        kaleidoscopeHelper.setCenter(x,y);
        log("down() about to drawToCanvas x,y: " + x + "," + y);
        if(isDrawOnMoveModeEnabled){
            paintView.enablePreviewLayer();
            drawToCanvas(x,y, paint);
            return;
        }
        paintHelperManager.getSizeHelper().onTouchDown(x, y);
        drawToCanvas(x,y, paint);
    }
*/


    @Override
    public void move(PointF p, Easel easel) {
        drawToCanvas(p, easel);
    }


    void drawToCanvas(PointF p, Easel easel){
        brushShape.generatePath(p);
        /*
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(p.x, p.y, paint);
        }
        else{
            rotateAndDraw(p, easel);
        }

         */
        rotateAndDraw(p,easel);
        paintView.invalidate();
    }


    public void rotateAndDraw(PointF p, Easel easel){
        canvas = easel.getCanvas();
        canvas.save();
        canvas.translate(p.x, p.y);

       // canvas.rotate(paintHelperManager.getAngleHelper().getFineAngle());

        for(var paint : easel.getActivePaints()){

            brushShape.draw(p, canvas, paint);
        }
        canvas.restore();
    }








    public void drawKaleidoscopeSegment(float x, float y, Paint paint){
       // var p = new PointF(x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY());
        //rotateAndDraw(p, paint);
    }
}
