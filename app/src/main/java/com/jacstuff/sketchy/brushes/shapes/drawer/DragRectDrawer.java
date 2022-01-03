package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.AngleHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DragRectDrawer extends BasicDrawer{

    private float downX, downY;
    private int angleOnTouchDown;
    private final RectCalc rectCalc;


    public DragRectDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
        isColorChangedOnDown = false;
        rectCalc = new RectCalc();
    }


    @Override
    public void down(float x, float y, Paint paint) {
        updateColorGradientAndAngle(x,y);
        paintHelperManager.getKaleidoscopeHelper().setCenter(x,y);
        angleOnTouchDown = paintHelperManager.getAngleHelper().getAngle();
        paintView.enablePreviewLayer();
        draw(snapToLowerBounds(x), snapToLowerBounds(y), paint);
    }


    public void draw(float x, float y, Paint paint){
        canvas.save();
        downX = x;
        downY = y;
        canvas.translate(x, y);
        brush.onTouchDown(new Point((int)x,(int)y), canvas, paint);
        canvas.restore();
    }


    @Override
    public void move(float x, float y, Paint paint) {
        float xMove = snapToUpperBounds(x, canvas.getWidth());
        float yMove = snapToUpperBounds(y, canvas.getHeight());
        paintView.enablePreviewLayer();
        rotateAndDrawMove(xMove, yMove,paint);
        paintView.invalidate();
    }


    @Override
    public void up(float x, float y, Paint paint) {
        float xUp = snapToUpperBounds(x, canvas.getWidth());
        float yUp = snapToUpperBounds(y, canvas.getHeight());
        paintView.disablePreviewLayer();
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(xUp, yUp, paint);
        }
        else{
            rotateAndDrawMove(xUp , yUp, paint);
        }
        paintView.pushHistory();
        paintView.invalidate();
    }



    public void rotateAndDrawMove(float x, float y, Paint paint){
        canvas.save();
        paintHelperManager.getGradientHelper().assignGradient((x - downX) /2, (y - downY)/2, viewModel.color, false);
        translateToTopCornerOfRect();
        rotateThenDrawShadowAndObject(x, y, paint);
        canvas.restore();
    }


    public PointF calculateRect(float x1, float y1, float x2, float y2, int angle){
        PointF p1 = new PointF();
        p1.x = x1;
        p1.y = y1;
        PointF p2 = new PointF();
        p2.x = x2;
        p2.y = y2;
        return rectCalc.calculateRect(p1,p2,angle);
    }


    @Override
    public void drawKaleidoscopeSegment(float x, float y, Paint paint){
        canvas.save();
        translateForKaleidoscope(x, y);
        rotateThenDrawShadowAndObject(x, y, paint);
        canvas.restore();
    }


    private void rotateThenDrawShadowAndObject(float x, float y, Paint paint){
        rotateToAngle();
        PointF bottomCorner =  calculateRect(downX, downY, x, y, paintHelperManager.getAngleHelper().getAngle());
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchMove(bottomCorner.x, bottomCorner.y, paintView.getShadowPaint());
        }
        brush.onTouchMove(bottomCorner.x, bottomCorner.y, paint);
    }


    private float snapToUpperBounds(float coordinate, float bound){
        return viewModel.isRectangleSnappedToEdges && coordinate > bound - viewModel.rectangleSnapBounds ? bound : coordinate;
    }


    private float snapToLowerBounds(float coordinate){
        return viewModel.isRectangleSnappedToEdges && coordinate < viewModel.rectangleSnapBounds? 0 : coordinate;
    }

    private void translateToTopCornerOfRect(){
        canvas.translate(downX, downY);
    }


    private void rotateToAngle(){
        AngleHelper angleHelper = paintHelperManager.getAngleHelper();
        if(angleHelper.getAngleType() == AngleType.RANDOM) {
            canvas.rotate(angleOnTouchDown);
        }
        else{
            canvas.rotate(angleHelper.getAngle());
        }
    }


    private void translateForKaleidoscope(float x, float y){
        float translateX = getKaleidoscopeTranslation(x, downX, kaleidoscopeHelper.getCenterX());
        float translateY = getKaleidoscopeTranslation(y, downY, kaleidoscopeHelper.getCenterY());
        canvas.translate(translateX, translateY);
    }


    private float getKaleidoscopeTranslation(float a, float aDown, float kCenterA){
        float downKa = aDown - kCenterA;
        float ka = a - kCenterA;
        float rotationCorrection = Math.abs(aDown - a) / 2;
        return downKa + ((ka-downKa) /2f) - rotationCorrection;
    }


}

