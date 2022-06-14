package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.AngleHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DragRectDrawer extends BasicDrawer{

    private float downX, downY;
    private int angleOnTouchDown;
    private final RectCalc rectCalc;
    private float kaleidoscopeTranslationX, kaleidoscopeTranslationY;


    public DragRectDrawer(PaintView paintView, MainViewModel viewModel) {
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


    @Override
    public void move(float x, float y, Paint paint) {
        float xMove = snapToUpperBounds(x, canvas.getWidth());
        float yMove = snapToUpperBounds(y, canvas.getHeight());
        paintView.enablePreviewLayer();
        rotateAndDrawMove(xMove, yMove, paint);
        paintView.invalidate();
    }


    @Override
    public void up(float x, float y, Paint paint) {
        float xUp = snapToUpperBounds(x, canvas.getWidth());
        float yUp = snapToUpperBounds(y, canvas.getHeight());

        paintView.disablePreviewLayer();
        if(kaleidoscopeHelper.isEnabled()){
            assignKaleidoscopeTranslations(xUp, yUp);
            kaleidoscopeDrawer.drawKaleidoscope(xUp, yUp, paint);
        }
        else{
            rotateAndDrawMove(xUp , yUp, paint);
        }
        paintView.pushHistory();
        paintView.invalidate();
    }


    private void assignKaleidoscopeTranslations(float xUp, float yUp){
        kaleidoscopeTranslationX = getKaleidoscopeTranslation(xUp, downX, kaleidoscopeHelper.getCenterX());
        kaleidoscopeTranslationY = getKaleidoscopeTranslation(yUp, downY, kaleidoscopeHelper.getCenterY());
    }


    private void draw(float x, float y, Paint paint){
        canvas.save();
        downX = x;
        downY = y;
        canvas.translate(x, y);
        brush.onTouchDown(new Point((int)x,(int)y), canvas, paint);
        canvas.restore();
    }


    private void rotateAndDrawMove(float x, float y, Paint paint){
        canvas.save();
        translateToTopCornerOfRect();
        rotateThenDrawShadowAndObject(x, y, paint);
        canvas.restore();
    }


    @Override
    public void drawKaleidoscopeSegment(float x, float y, Paint paint){
        canvas.save();
        translateForKaleidoscope();
        rotateThenDrawShadowAndObject(x, y, paint);
        canvas.restore();
    }


    private void translateForKaleidoscope(){
        canvas.translate(kaleidoscopeTranslationX, kaleidoscopeTranslationY);
    }


    private void rotateThenDrawShadowAndObject(float x, float y, Paint paint){
        rotateToAngle();
        PointF bottomCorner =  calculateRect(downX, downY, x, y, paintHelperManager.getAngleHelper().getAngle());
        assignGradient(x,y, bottomCorner);
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchMove(bottomCorner.x, bottomCorner.y, paintView.getShadowPaint());
        }
        brush.onTouchMove(bottomCorner.x, bottomCorner.y, paint);
    }


    private PointF calculateRect(float x1, float y1, float x2, float y2, int angle){
        PointF p1 = new PointF();
        p1.x = x1;
        p1.y = y1;
        PointF p2 = new PointF();
        p2.x = x2;
        p2.y = y2;
        return rectCalc.calculateRect(p1,p2,angle);
    }


    private float snapToUpperBounds(float coordinate, float bound){
        return viewModel.isRectangleSnappedToEdges
                && coordinate > bound - viewModel.rectangleSnapBounds
                && brush.getBrushShape() == BrushShape.DRAG_RECTANGLE ? bound : coordinate;
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


    //where 'a' is an x or y coordinate
    private float getKaleidoscopeTranslation(float aUp, float aDown, float kCenterA){
        float downKa = aDown - kCenterA;
        float ka = Math.max(aUp, aDown) - kCenterA;
        float rotationCorrection = Math.abs(aDown - Math.abs(aUp)) / 2;
        return downKa + ((ka-downKa) /2f) - rotationCorrection + calculateReverseDirectionCorrection(aDown, aUp);
    }


    private float calculateReverseDirectionCorrection(float downCoordinate, float upCoordinate){
        return upCoordinate < downCoordinate ? ((Math.abs(downCoordinate) - Math.abs(upCoordinate)) / 2f) : 0;
    }


    private void assignGradient(float x, float y, PointF bottomCorner){
        PointF down = createCoordinatePoint(0, 0);
        PointF up = createCoordinatePoint(x - downX ,y - downY);
        PointF shapeMidpoint = getShapeMidpoint(bottomCorner);

        paintHelperManager.getGradientHelper().assignGradientForDragShape(down, up, shapeMidpoint, false);
    }



    private PointF getShapeMidpoint(PointF corner){
        PointF mid = new PointF();
        mid.x = (corner.x - downX) / 2;
        mid.y = (corner.y - downY) / 2;
        return mid;
    }


    private PointF createCoordinatePoint(float x, float y){
        PointF point = new PointF();
        point.x = x;
        point.y = y;
        return point;
    }

}

