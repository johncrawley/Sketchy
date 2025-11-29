package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.jacstuff.sketchy.brushes.BrushShape;

public class ArcBrush extends AbstractBrush implements Brush {

    private int startingAngle;
    private int sweepAngle;
    private boolean isDrawnFromCentre;

    public ArcBrush(){
        super(BrushShape.ARC);
    }


    public void setStartingAngle(int startingAngle) {
        this.startingAngle = startingAngle;
    }


    public int getStartingAngle(){
        return startingAngle;
    }


    public int getSweepAngle() {
        return sweepAngle;
    }


    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
    }


    public boolean isDrawnFromCentre() {
        return isDrawnFromCentre;
    }


    public void setDrawnFromCentre(boolean drawnFromCentre) {
        isDrawnFromCentre = drawnFromCentre;
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        RectF rect = new RectF(-halfBrushSize, -halfBrushSize, halfBrushSize, halfBrushSize);
        int startAngle = 180 + startingAngle;
        int sweep = Math.min(1 + sweepAngle, 359);
        canvas.drawArc(rect, startAngle, sweep, isDrawnFromCentre, paint);
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
