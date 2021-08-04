package com.jacstuff.sketchy.paintview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import androidx.lifecycle.ViewModel;

public class KaleidoscopePathDrawer extends KaleidoscopeDrawer{



    private final Canvas kaleidoscopeCanvas;
    private final Paint paint;
    private Bitmap segmentBitmap;
    private final Point previousPoint = new Point();



    public KaleidoscopePathDrawer(PaintView paintView, MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        super(paintView, viewModel, kaleidoscopeHelper);
        kaleidoscopeCanvas = paintView.getKaleidoscopeSegmentCanvas();
        paint = paintView.getPaint();
    }





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
            drawToSegmentBitmap(diffPoint, segmentDimension);
        }
        canvas.save();
        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
        float kx,ky;

        kx = p.x -halfSegmentWidth;
        ky = p.y -halfSegmentWidth;
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle += kaleidoscopeHelper.getDegreeIncrement()){
            canvas.save();
            canvas.rotate(angle);
            canvas.drawBitmap(segmentBitmap, kx, ky, paint);
            canvas.restore();
        }

        if(viewModel.isInfinityModeEnabled){
            drawGlitchSegments(p.x, p.y);
        }
        canvas.restore();
        previousPoint.x = originalPoint.x;
        previousPoint.y = originalPoint.y;
        drawFlickerGuardCircles(kx, ky, p, halfSegmentWidth);

    }

    private void drawFlickerGuardCircles(float kx, float ky, Point p, float halfSegmentWidth){

        canvas.translate(kaleidoscopeHelper.getCenterX(), kaleidoscopeHelper.getCenterY());
        paintView.enablePreviewLayer();
        for(float angle = 0; angle < kaleidoscopeHelper.getMaxDegrees(); angle+= kaleidoscopeHelper.getDegreeIncrement()){
            canvas.rotate(kaleidoscopeHelper.getDegreeIncrement());
            kx = p.x -halfSegmentWidth;
            ky = p.y -halfSegmentWidth;
            canvas.drawCircle(kx, ky, paint.getStrokeWidth()/2, paintView.getPreviewPaint());
        }
        canvas.rotate(0);
        canvas.translate(0,0);


    }


    private void drawToSegmentBitmap(Point diffPoint, int segmentDimension){
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
    }


}
