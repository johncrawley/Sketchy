package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.paintview.PaintView;


public class BasicDrawer extends AbstractDrawer implements Drawer {


    private float x,y;

    public BasicDrawer(PaintView paintView){
        super(paintView);
    }


    public void init(PaintView paintView){
        this.paintView = paintView;
    }


    @Override
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


    private void log(String msg){
        System.out.println("^^^ BasicDrawer: " + msg);
    }


    @Override
    public void move(float x1, float y1, Paint paint) {
        PointF point = placementHelper.calculatePoint(x1,y1);
        float x = point.x;
        float y = point.y;
        if(!isDrawOnMoveModeEnabled){
            paintView.enablePreviewLayer();
            drawToCanvas(x,y, paint);
            return;
        }

        log("move() about to update Color Gradient and Angle paint color: " + paint.getColor());
        updateColorGradientAndAngle(x,y);
        log("move() paint color after color update: " + paint.getColor());
        paintView.disablePreviewLayer();
      //  paintHelperManager.getSizeHelper().assignNextBrushSize(x,y);
        drawToCanvas(x,y, paint);
        paintView.enablePreviewLayer();
        drawPreviewWhenInfinityModeOff(x, y);
    }


    @Override
    public void up(float x1, float y1, Paint paint) {
        calculateXYFrom(x1, y1);
        if(!isDrawOnMoveModeEnabled){
            drawOnUp(paint);
            return;
        }
        paintHelperManager.getColorHelper().resetCurrentIndex();
        paintView.disablePreviewLayer();
        paintView.invalidate();
        paintView.pushHistory();
        paintHelperManager.getSizeHelper().assignNextBrushSize(x,y);
    }


    private void calculateXYFrom(float x1, float y1){
        PointF point = placementHelper.calculatePoint(x1,y1);
        x = point.x;
        y = point.y;
    }


    private void drawOnUp(Paint paint){
        paintView.disablePreviewLayer();
        drawToCanvas(x,y, paint);
        paintView.enablePreviewLayer();
        drawPreviewWhenInfinityModeOff(x, y);
        paintView.disablePreviewLayer();
        paintView.invalidate();
        paintView.pushHistory();
        paintHelperManager.getSizeHelper().assignNextBrushSize(x,y);
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
        canvas.rotate(paintHelperManager.getAngleHelper().getFineAngle());
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
