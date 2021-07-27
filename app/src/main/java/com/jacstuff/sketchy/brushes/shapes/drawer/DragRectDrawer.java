package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DragRectDrawer extends BasicDrawer{

    private float downX, downY;

    public DragRectDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
        isColorChangedOnDown = false;
    }


    @Override
    public void down(float x, float y, Paint paint) {
        paintHelperManager.getKaleidoscopeHelper().setCenter(x,y);
        paintView.enablePreviewLayer();
        rotateAndDraw(x,y, paint);
    }


    public void rotateAndDraw(float x, float y, Paint paint){
        canvas.save();
        downX = x;
        downY = y;
        canvas.translate(x, y);
        canvas.rotate(paintHelperManager.getAngleHelper().getAngle());
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchDown(x,y, paintView.getShadowPaint());
        }
        brush.onTouchDown(x, y, paint);
        canvas.restore();
    }


    public void rotateAndDrawMove(float x, float y, Paint paint){
        canvas.save();
        canvas.translate(downX + ((x-downX) /2f), downY + ((y-downY)/2f));
        canvas.rotate(paintHelperManager.getAngleHelper().getAngle());
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchMove(x,y, paintView.getShadowPaint());
        }
        brush.onTouchMove(x,y, paint);
        canvas.restore();
    }


    @Override
    public void move(float x, float y, Paint paint) {
        paintView.enablePreviewLayer();
        rotateAndDrawMove(x,y,paint);
        paintView.invalidate();
    }


    @Override
    public void up(float x, float y, Paint paint) {
        paintView.disablePreviewLayer();
        if(kaleidoscopeHelper.isEnabled()){
            kaleidoscopeDrawer.drawKaleidoscope(x, y, paint);
        }
        else{
            rotateAndDrawMove(x,y,paint);
        }
        paintView.pushHistory();
        paintView.invalidate();
    }


    // good attempt but still not drawing a proper kaleidoscope - rectangles are of incorrect position and dimensions!
    @Override
    public void drawKaleidoscopeSegment(float x, float y, float angle, Paint paint){
        rotateAndDrawMove( x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY(),paint);
    }

}

