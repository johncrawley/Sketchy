package com.jacstuff.sketchy.paintview;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.drawer.Drawer;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import androidx.core.util.Consumer;


public class KaleidoscopeDrawer {

    private Canvas canvas;
    private final MainViewModel viewModel;
    private final KaleidoscopeHelper kaleidoscopeHelper;
    private final PaintView paintView;
    private final Paint infinityPaint;
    private Bitmap infinityImage;
    private final Canvas kaleidoscopeCanvas;
    private final Paint paint;
    private int kPaintStrokeWidth = 7;
    Paint kPaint = new Paint();
    public KaleidoscopeDrawer(PaintView paintView, MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        this.paintView = paintView;
        this.canvas = paintView.getCanvas();
        this.viewModel = viewModel;
        infinityPaint = new Paint();
        this.kaleidoscopeHelper = kaleidoscopeHelper;
        kaleidoscopeCanvas = paintView.getKaleidoscopeSegmentCanvas();
        paint = paintView.getPaint();
        kPaint.setStrokeWidth(kPaintStrokeWidth);
        kPaint.setStyle(Paint.Style.STROKE);
        kPaint.setColor(Color.MAGENTA);
    }


    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

    private Drawer parentDrawer;

    public void initParentDrawer(Drawer drawer){
        parentDrawer = drawer;
    }


    public void drawKaleidoscope(float x, float y, Paint paint){
        if(viewModel.isInfinityModeEnabled) {
            infinityImage = Bitmap.createScaledBitmap(paintView.getBitmap(), 500, 500, false);
        }
        canvas.save();
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());

        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()){
            canvas.save();
            canvas.rotate(angle);
            parentDrawer.drawKaleidoscopeSegment(x, y, paint);
            canvas.restore();
        }
        if(viewModel.isInfinityModeEnabled){
            drawGlitchSegments(x,y);
        }
        canvas.restore();
    }
    //TODO: let's try again to create a segment bitmap on each draw
    // -should be transparent
    // should draw the current brush in the middle
    // should be drawn at current translated 0,0 -segmentWidth/2, -segmentHeight/2
    private Bitmap segmentBitmap;


    public void drawKaleidoscope(Point p, int currentBrushSize, Consumer<Canvas> segmentDrawer){
        final int MIN_DIMENSION = 50;
        int segmentDimension = MIN_DIMENSION + currentBrushSize;
        float halfSegmentWidth = segmentDimension /2f;
        if(viewModel.isInfinityModeEnabled) {
            infinityImage = Bitmap.createScaledBitmap(paintView.getBitmap(), 500, 500, false);
        }
        else{
            segmentBitmap = Bitmap.createBitmap(segmentDimension, segmentDimension, Bitmap.Config.ARGB_8888);
            segmentBitmap.eraseColor(Color.TRANSPARENT);
            kaleidoscopeCanvas.setBitmap(segmentBitmap);
            kaleidoscopeCanvas.save();
            kaleidoscopeCanvas.translate(halfSegmentWidth, halfSegmentWidth);
            kaleidoscopeCanvas.drawRect(-halfSegmentWidth,-halfSegmentWidth,halfSegmentWidth, halfSegmentWidth, kPaint);
            segmentDrawer.accept(kaleidoscopeCanvas);
        }
        canvas.save();
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());

        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()){
            canvas.save();
            canvas.rotate(angle);
            canvas.drawBitmap(segmentBitmap, p.x - halfSegmentWidth, p.y - halfSegmentWidth, paint);
            canvas.restore();
        }
        if(viewModel.isInfinityModeEnabled){
            drawGlitchSegments(p.x, p.y);
        }
        canvas.restore();
    }

    private void log(String msg){
        System.out.println("KaleidoscopeDrawer: " + msg);
    }
    private Point previousPoint = new Point();

    public void drawKaleidoscope(Point p, Point originalPoint, int currentBrushSize){
        Point diffPoint = new Point();
        diffPoint.x = previousPoint.x - originalPoint.x;
        diffPoint.y = previousPoint.y - originalPoint.y;
        final int MIN_DIMENSION = 50;
        int segmentDimension = MIN_DIMENSION + currentBrushSize;
        float halfSegmentWidth = segmentDimension /2f;
        if(viewModel.isInfinityModeEnabled) {
            infinityImage = Bitmap.createScaledBitmap(paintView.getBitmap(), 500, 500, false);
        }
        else{
            segmentBitmap = Bitmap.createBitmap(segmentDimension, segmentDimension, Bitmap.Config.ARGB_8888);
            segmentBitmap.eraseColor(Color.TRANSPARENT);
            kaleidoscopeCanvas.setBitmap(segmentBitmap);
            kaleidoscopeCanvas.save();

            kaleidoscopeCanvas.translate(halfSegmentWidth, halfSegmentWidth);
            Path tempPath = new Path();
            tempPath.moveTo(diffPoint.x,diffPoint.y);
            tempPath.lineTo(0, 0);
            kaleidoscopeCanvas.drawPath(tempPath, paint);

        }
        canvas.save();
        //1st translation
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());

        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()){
            canvas.save();
            canvas.rotate(angle);
            canvas.drawBitmap(segmentBitmap, p.x - halfSegmentWidth, p.y - halfSegmentWidth, paint);
            canvas.restore();
        }
        if(viewModel.isInfinityModeEnabled){
            drawGlitchSegments(p.x, p.y);
        }
        canvas.restore();
        previousPoint.x = originalPoint.x;
        previousPoint.y = originalPoint.y;
    }






    private void drawGlitchSegments(float x, float y){
        BitmapShader bitmapShader = new BitmapShader(infinityImage, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        infinityPaint.setStyle(Paint.Style.FILL);
        infinityPaint.setShader(bitmapShader);
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()) {
            drawGlitchModeSegment(x, y, angle);
        }
    }


    private void drawGlitchModeSegment(float x, float y, float angle){
        canvas.save();
        canvas.rotate(angle);
        float gx = x - (kaleidoscopeHelper.getCenterX());
        float gy = y - (kaleidoscopeHelper.getCenterY());
        canvas.drawBitmap(infinityImage,gx, gy, infinityPaint);
        canvas.restore();
    }


}
