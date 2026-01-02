package com.jacstuff.sketchy.brushes.shapes.onestep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractShape;

public class ArcBrush extends AbstractShape {

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
    public void draw(PointF point, Canvas canvas, Paint paint) {
        var rect = new RectF(-halfBrushSize, -halfBrushSize, halfBrushSize, halfBrushSize);
        int startAngle = 180 + startingAngle;
        int sweep = Math.min(1 + sweepAngle, 359);
        canvas.drawArc(rect, startAngle, sweep, isDrawnFromCentre, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
    }
}
