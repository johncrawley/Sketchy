package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class ArcBrush extends AbstractBrush implements Brush {

    public ArcBrush(){
        super(BrushShape.ARC);
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        RectF rect = new RectF(-halfBrushSize, -halfBrushSize, halfBrushSize, halfBrushSize);
        int startAngle = 180 + viewModel.arcShapeStartingAngle;
        int sweep = Math.min(1 + viewModel.arcShapeAngleSweepAngle, 359);
        canvas.drawArc(rect, startAngle, sweep, viewModel.isArcShapeDrawnFromCentre, paint);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x, y, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
    }
}
