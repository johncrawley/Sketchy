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
        if(viewModel.snapRectangleToEdge){
            x = x <= 25 ? 0 : x;
            y = y <= 25 ? 0 : y;
        }
        draw(x,y, paint);
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
        paintView.enablePreviewLayer();
        rotateAndDrawMove2(x,y,paint);
        paintView.invalidate();
    }


    @Override
    public void up(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(x, y, paint);
        }
        else{
            rotateAndDrawMove2(x,y,paint);
        }
        paintView.pushHistory();
        paintView.invalidate();
    }


    public void rotateAndDrawMove(float x, float y, Paint paint){
        canvas.save();
        translateToMiddleOfRect(x,y);
        rotateThenDrawShadowAndObject(x, y, paint);
        canvas.restore();
    }

    public void rotateAndDrawMove2(float x, float y, Paint paint){
        canvas.save();
        translateToTopCornerOfRect();
        rotateThenDrawShadowAndObject2(x, y, paint);
        canvas.restore();
    }


    private void translateToTopCornerOfRect(){
        canvas.translate(downX, downY);
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


    private void translateToMiddleOfRect(float x2, float y2){
        float middleOfRectX = downX + ((x2-downX)/2f);
        float middleOfRectY = downY + ((y2-downY)/2f);
        canvas.translate(middleOfRectX, middleOfRectY);
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
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchMove(x, y, paintView.getShadowPaint());
        }
        brush.onTouchMove(x,y, paint);
    }


    private void rotateThenDrawShadowAndObject2(float x, float y, Paint paint){
        rotateToAngle();
        PointF bottomCorner =  calculateRect(downX, downY, x, y, paintHelperManager.getAngleHelper().getAngle());
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
           // brush.onTouchMove(bottomCorner.x, bottomCorner.y, paintView.getShadowPaint());

            brush.onTouchMove(x,y, paintView.getShadowPaint());
        }
        brush.onTouchMove(bottomCorner.x, bottomCorner.y, paint);
        //brush.onTouchMove(x,y, paint);
    }


    private void rotateToAngle(){
        AngleHelper angleHelper = paintHelperManager.getAngleHelper();
        if(angleHelper.getAngleType() == AngleType.RANDOM) {
            canvas.rotate(angleOnTouchDown);
        }
        else{
            int angle = angleHelper.getAngle();
            canvas.rotate(angleHelper.getAngle());
        }
    }


    private void translateForKaleidoscope(float x, float y){
        float downKx = downX - kaleidoscopeHelper.getCenterX();
        float downKy = downY - kaleidoscopeHelper.getCenterY();
        float kx = x - kaleidoscopeHelper.getCenterX();
        float ky = y - kaleidoscopeHelper.getCenterY();
        canvas.translate(downKx + ((kx-downKx) /2f), downKy + ((ky-downKy)/2f));
    }

}

