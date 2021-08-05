package com.jacstuff.sketchy.paintview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;


public class KaleidoscopePathDrawer extends KaleidoscopeDrawer{

    private final Canvas kaleidoscopeCanvas;
    private final Paint paint;
    private Paint flickerGuardPaint;
    private Bitmap segmentBitmap;
    private final Point previousPoint = new Point();
    private boolean isPreviousPointReset = true;
    private int segmentWidth, halfSegmentWidth;


    public KaleidoscopePathDrawer(PaintView paintView, MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        super(paintView, viewModel, kaleidoscopeHelper);
        kaleidoscopeCanvas = paintView.getKaleidoscopeSegmentCanvas();
        paint = paintView.getPaint();
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
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
        paintView.enablePreviewLayer();
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle+= kaleidoscopeHelper.getDegreeIncrement()){
            canvas.rotate(kaleidoscopeHelper.getDegreeIncrement());
            canvas.drawCircle(p.x, p.y, paint.getStrokeWidth()/2, flickerGuardPaint);
        }
        canvas.rotate(0);
        canvas.translate(0,0);
    }


    private void rotateAndDrawSegmentBitmapAroundAxis(Point p, float halfSegmentWidth){
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
        float kx = p.x -halfSegmentWidth;
        float ky = p.y -halfSegmentWidth;
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()){
            canvas.save();
            canvas.rotate(angle);
            canvas.drawBitmap(segmentBitmap, kx, ky, paint);
            canvas.restore();
        }
    }


    private void drawPathOnSegmentBitmap(Point originalPoint, int segmentDimension){
        Point diffPoint = new Point();
        diffPoint.x = isPreviousPointReset ? 0 : previousPoint.x - originalPoint.x;
        diffPoint.y = isPreviousPointReset ? 0 : previousPoint.y - originalPoint.y;
        float halfSegmentWidth = segmentDimension /2f;
        segmentBitmap = Bitmap.createBitmap(segmentDimension, segmentDimension, Bitmap.Config.ARGB_8888);
        segmentBitmap.eraseColor(Color.TRANSPARENT);
        kaleidoscopeCanvas.setBitmap(segmentBitmap);
        kaleidoscopeCanvas.save();

        kaleidoscopeCanvas.translate(halfSegmentWidth, halfSegmentWidth);
        Path tempPath = new Path();
        tempPath.moveTo(diffPoint.x,diffPoint.y);
        tempPath.lineTo(0, 0);
        kaleidoscopeCanvas.drawPath(tempPath, paint);
        isPreviousPointReset = false;
    }

}
