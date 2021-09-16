package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;


public class BasicDrawer extends AbstractDrawer implements Drawer {


    public BasicDrawer(PaintView paintView, MainViewModel viewModel){
        super(paintView, viewModel);
    }


    @Override
    public void down(float x, float y, Paint paint) {
        updateColorGradientAndAngle(x,y);
        kaleidoscopeHelper.setCenter(x,y);
        if(!viewModel.isDrawOnMoveModeEnabled){
            paintView.enablePreviewLayer();
            drawToCanvas(x,y, paint);
            return;
        }
        paintHelperManager.getSizeHelper().onTouchDown();
        drawToCanvas(x,y, paint);
    }


    @Override
    public void move(float x, float y, Paint paint) {
        if(!viewModel.isDrawOnMoveModeEnabled){
            paintView.enablePreviewLayer();
            drawToCanvas(x,y, paint);
            return;
        }
        updateColorGradientAndAngle(x,y);
        paintView.disablePreviewLayer();
        paintHelperManager.getSizeHelper().assignNextBrushSize();
        drawToCanvas(x,y, paint);
        paintView.enablePreviewLayer();
        drawPreviewWhenInfinityModeOff(x, y);
    }


    @Override
    public void up(float x, float y, Paint paint) {
        if(!viewModel.isDrawOnMoveModeEnabled){
            paintView.disablePreviewLayer();
            drawToCanvas(x,y, paint);
            paintView.enablePreviewLayer();
            drawPreviewWhenInfinityModeOff(x, y);
            paintView.disablePreviewLayer();
            paintView.invalidate();
            paintView.pushHistory();
            paintHelperManager.getSizeHelper().assignNextBrushSize();
            return;
        }
        paintHelperManager.getColorHelper().resetCurrentIndex();
        paintView.disablePreviewLayer();
        paintView.invalidate();
        paintView.pushHistory();
        paintHelperManager.getSizeHelper().assignNextBrushSize();
    }


    private void drawPreviewWhenInfinityModeOff(float x, float y){
        if(!kaleidoscopeHelper.isInfinityModeEnabled()) {
            if(tileHelper.isEnabled() && !kaleidoscopeHelper.isEnabled()){
                tileHelper.drawPreview(x,y,this);
                return;
            }
            drawToCanvas(x, y, paintView.getPreviewPaint());
        }
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
        canvas.rotate(paintHelperManager.getAngleHelper().getAngle());
        if(paintHelperManager.getShadowHelper().isShadowEnabled()){
            brush.onTouchDown(p, canvas, paintView.getShadowPaint());
        }
        brush.onTouchDown(p, canvas, paint);
        canvas.restore();
    }


    @Override
    public void drawKaleidoscopeSegment(float x, float y, Paint paint){
        rotateAndDraw(x - kaleidoscopeHelper.getCenterX(), y - kaleidoscopeHelper.getCenterY(), paint);
    }


}
