package com.jacstuff.sketchy.brushes.shapes.threestep;

import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.drawer.AbstractShapeDrawer;
import com.jacstuff.sketchy.easel.Easel;
import com.jacstuff.sketchy.paintview.PaintView;

public class ThreeStepDrawer extends AbstractShapeDrawer {

    private ThreeStepShape threeStepShape;


    public ThreeStepDrawer(PaintView paintView){
        super(paintView);
    }

    public void setShape(ThreeStepShape threeStepShape){
        this.threeStepShape = threeStepShape;
    }


    @Override
    public void down(PointF p, Easel easel) {
        // paintView.enablePreviewLayer();
        threeStepShape.place(p);
    }


    @Override
    public void move(PointF p, Easel easel) {
        paintView.enablePreviewLayer();
        releaseAndDraw(p, easel);
    }


    @Override
    public void up(PointF p, Easel easel) {
        paintView.disablePreviewLayer();
        releaseAndDraw(p, easel);
    }


    private void releaseAndDraw(PointF p, Easel easel){
        canvas = easel.getCanvas();
        canvas.save();
        canvas.translate(p.x, p.y);

        // canvas.rotate(paintHelperManager.getAngleHelper().getFineAngle());

        for(var paint : easel.getActivePaints()){
            threeStepShape.draw(p, canvas, paint);
        }
        canvas.restore();
    }


    void drawToCanvas(PointF p, Easel easel){
        brushShape.generatePath(p);
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




}
