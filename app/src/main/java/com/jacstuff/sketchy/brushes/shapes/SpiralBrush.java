package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;

public class SpiralBrush extends AbstractBrush implements Brush {

    private float savedStrokeWidth;
    private final Path path;

    public SpiralBrush() {
        super(BrushShape.SPIRAL);
        path = new Path();
    }


    public void setBrushSize(int size) {
        super.setBrushSize(size);
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        int extraSpacing = 10;
        drawSpiral(canvas, paint,  1 + (brushSize / 30));
    }


    private void drawSpiral(Canvas canvas, Paint paint, int numberOfTwists) {
        path.reset();
        saveStrokeWidth(paint);
        int spacing = (int) paint.getStrokeWidth() + mainViewModel.spiralExtraSpacing;
        int left = -spacing;
        int right = spacing;
        int top = -spacing;
        int bottom = spacing;
        int startAngle = 0;
        int arcAngle = 180;
        int halfSpacing = spacing / 2;
        path.moveTo(right, bottom);

        for (int i = 0; i < numberOfTwists * 2; i++) {
            path.addArc(left, top, right, bottom, startAngle, arcAngle);
            top -= halfSpacing;
            bottom += halfSpacing;
            if (i % 2 == 0) {
                right += spacing;
            } else {
                left -= spacing;
            }
            startAngle = startAngle + arcAngle;
        }
        canvas.drawPath(path, paint);
        recallStrokeWidth(paint);
    }


    private void saveStrokeWidth(Paint paint) {
        savedStrokeWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(1+ (savedStrokeWidth / 10));
    }


    private void recallStrokeWidth(Paint paint){
        paint.setStrokeWidth(savedStrokeWidth);
    }


    @Override
    public void onDeallocate(){
        super.onDeallocate();
        paintGroup.setStrokeWidth(savedStrokeWidth);
        paintGroup.setStyle(savedStyle);
    }

    private Paint.Style savedStyle;

    @Override
    public void reinitialize(){
        super.reinitialize();
        savedStyle = Paint.Style.valueOf(paintGroup.getStyle());
        paintGroup.setStyle(Paint.Style.STROKE);
        savedStrokeWidth = paintGroup.getLineWidth();
        paintGroup.setStrokeWidth( 1 + (savedStrokeWidth/20));
    }
}