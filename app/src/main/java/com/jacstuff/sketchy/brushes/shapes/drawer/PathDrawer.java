package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class PathDrawer extends BasicDrawer{

    public PathDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
    }



    @Override
    public void down(float x, float y, Paint paint) {
        paintHelperManager.getKaleidoscopeHelper().setCenter(x,y);
        drawToCanvas(x,y, paint);
    }


    @Override
    public void move(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        drawToCanvasMove(x,y, paint);
        paintView.enablePreviewLayer();
        drawPreviewWhenInfinityModeOff(x, y);
    }


    public void rotateAndDraw(float x, float y, Paint paint){
        canvas.save();
        canvas.translate(x, y);
        //canvas.rotate(paintHelperManager.getAngleHelper().getAngle());
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchDown(x,y, paintView.getShadowPaint());
        }
        brush.onTouchDown(x, y, paint);
        canvas.restore();
    }


    public void rotateAndDrawMove(float x, float y, Paint paint){
        canvas.save();
        canvas.translate(x, y);
        brush.onTouchMove(x, y, paint);
        canvas.restore();
    }


    private void drawPreviewWhenInfinityModeOff(float x, float y){
        if(!kaleidoscopeHelper.isInfinityModeEnabled()) {
            drawToCanvas(x, y, paintView.getPreviewPaint());
        }
    }


    @Override
    public void up(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        brush.onTouchUp(x, y, paint);
        paintView.invalidate();
        paintView.pushHistory();
    }


    void drawToCanvas(float x, float y, Paint paint){
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(x,y, paint);
        }
        else{
            rotateAndDraw(x,y, paint);
        }
        paintView.invalidate();
    }

    void drawToCanvasMove(float x, float y, Paint paint){
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(x,y, paint);
        }
        else{
            rotateAndDrawMove(x,y, paint);
        }
        paintView.invalidate();
    }



    @Override
    public void drawKaleidoscopeSegment(float x, float y, float angle, Paint paint){
        rotateAndDraw(x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY(), paint);
    }


}
