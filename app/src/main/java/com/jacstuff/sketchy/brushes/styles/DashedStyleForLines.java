package com.jacstuff.sketchy.brushes.styles;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class DashedStyleForLines extends DashedStyle implements Style {

    private int brushSize;
    private boolean hasBrushSizeChanged = false;

    public DashedStyleForLines(int initialBrushSize){

        dashPathEffect = calculateDashPath(initialBrushSize);
    }

    private DashPathEffect calculateDashPath(int brushSize){
        float onStroke = calculateOnStroke(brushSize);
        float offStroke = calculateOffStroke(brushSize);
        return new DashPathEffect(new float[] {onStroke, offStroke}, 0);
    }

    private float calculateOnStroke(int brushSize){
        return 20 + (brushSize /16f);
    }

    private float calculateOffStroke(int brushSize){
        return 60 + (brushSize /2f );
    }


    @Override
    public void init(Paint paint, int brushSize) {
        paint.setStrokeWidth(calculate(brushSize));
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(dashPathEffect);
    }


    @Override
    public void setBrushSize(Paint paint, int brushSize) {
        this.brushSize = brushSize;
        hasBrushSizeChanged = true;
    }


    private float calculate(int brushSize){
        return brushSize / 2f;
    }


    @Override
    public void onDraw(Paint paint){
       if(hasBrushSizeChanged){
           dashPathEffect = calculateDashPath(brushSize);
           paint.setPathEffect(dashPathEffect);
           hasBrushSizeChanged = false;
       }
    }
}
