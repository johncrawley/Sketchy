package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;


public class KaleidoscopePathDrawer extends KaleidoscopeDrawer {

    private final Canvas kaleidoscopeCanvas;
    private final Paint paint;
    private Paint flickerGuardPaint;
    private Bitmap segmentBitmap;
    private final Point previousPoint = new Point();
    private boolean isPreviousPointReset = true;
    private int segmentWidth, halfSegmentWidth;
    private ShadowHelper shadowHelper;


    public KaleidoscopePathDrawer(PaintView paintView, MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        super(paintView, viewModel, kaleidoscopeHelper);
        kaleidoscopeCanvas = new Canvas(Bitmap.createBitmap(10,10, Bitmap.Config.ARGB_8888));
        paint = paintView.getPaint();
    }


    public void setShadowHelper(ShadowHelper shadowHelper){
        this.shadowHelper = shadowHelper;
    }


    public void setFlickerGuardPaint(Paint flickerGuardPaint){
        this.flickerGuardPaint = flickerGuardPaint;
    }


    public void drawKaleidoscope(Point p, Point originalPoint){
        setSegmentBitmapSize();
        createInfinityBitmap();
        drawPathOnSegmentBitmap(originalPoint, segmentWidth);
        canvas.save();
        rotateAndDrawSegmentBitmapAroundAxis(p, halfSegmentWidth);
        drawInfinityModeSegments(p.x, p.y);
        canvas.restore();
        savePoint(originalPoint);
        drawFlickerGuardCircles(p);
    }


    private void setSegmentBitmapSize(){
        final int MIN_DIMENSION = 50;
        segmentWidth = MIN_DIMENSION + (int)paint.getStrokeWidth();
        halfSegmentWidth = segmentWidth /2;
    }


    private void savePoint(Point p){
        previousPoint.x = p.x;
        previousPoint.y = p.y;
    }


    public void resetPreviousPoint(){
        isPreviousPointReset = true;
    }


    private void drawFlickerGuardCircles(Point p){
        if(kaleidoscopeHelper.isInfinityModeEnabled()){
            return;
        }
        paintView.enablePreviewLayer();
        translateCanvasToCenter();
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle+= kaleidoscopeHelper.getDegreeIncrement()){
            drawFlickerGuardCircle(p);
        }
        resetCanvasProperties(); //instead of using canvas save() and restore(), because they have problems in preview mode
    }


    private void drawFlickerGuardCircle(Point p){
        canvas.rotate(kaleidoscopeHelper.getDegreeIncrement());
        canvas.drawCircle(p.x, p.y, paint.getStrokeWidth()/2, flickerGuardPaint);
    }


    private void resetCanvasProperties(){
        canvas.rotate(0);
        canvas.translate(0,0);
    }


    private void rotateAndDrawSegmentBitmapAroundAxis(Point p, float halfSegmentWidth){
        translateCanvasToCenter();
        float kx = p.x -halfSegmentWidth;
        float ky = p.y -halfSegmentWidth;
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()){
           drawSegmentBitmapAtAngle(kx, ky, angle);
        }
    }


    private void translateCanvasToCenter(){
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
    }


    private void drawSegmentBitmapAtAngle(float kx, float ky, float angle){
        canvas.save();
        canvas.rotate(angle);
        canvas.drawBitmap(segmentBitmap, kx, ky, paint);
        canvas.restore();
    }


    private void drawPathOnSegmentBitmap(Point originalPoint, int segmentWidth){
        Point diffPoint = createDiffPoint(originalPoint);
        kaleidoscopeCanvas.save();
        configureSegmentBitmapAndCanvas(segmentWidth);
        drawPath(diffPoint);
        isPreviousPointReset = false;
    }


    private Point createDiffPoint(Point originalPoint){
        Point diffPoint = new Point();
        diffPoint.x = isPreviousPointReset ? 0 : previousPoint.x - originalPoint.x;
        diffPoint.y = isPreviousPointReset ? 0 : previousPoint.y - originalPoint.y;
        return diffPoint;
    }


    private void drawPath(Point diffPoint){
        Path path = new Path();
        path.moveTo(diffPoint.x,diffPoint.y);
        path.lineTo(0, 0);

        if(shadowHelper.isShadowEnabled()){
            kaleidoscopeCanvas.drawPath(path, paintView.getShadowPaint());
        }
        kaleidoscopeCanvas.drawPath(path, paint);
    }


    private void configureSegmentBitmapAndCanvas(int segmentLength){
        int bitmapSide = segmentLength + (int)viewModel.shadowOffsetX;
        segmentBitmap = Bitmap.createBitmap(bitmapSide, bitmapSide, Bitmap.Config.ARGB_8888);
        segmentBitmap.eraseColor(Color.TRANSPARENT);
        kaleidoscopeCanvas.setBitmap(segmentBitmap);
        float halfSegmentWidth = segmentLength /2f;
        kaleidoscopeCanvas.translate(halfSegmentWidth, halfSegmentWidth);
    }

}
