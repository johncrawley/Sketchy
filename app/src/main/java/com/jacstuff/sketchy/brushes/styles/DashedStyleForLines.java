package com.jacstuff.sketchy.brushes.styles;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class DashedStyleForLines extends DashedStyle implements Style {

    private int brushSize;
    private PaintGroup paintGroup;

    public DashedStyleForLines(int initialBrushSize){
        brushSize = initialBrushSize;
        dashPathEffect = calculateDashPath();
    }


    @Override
    public void init(PaintGroup paintGroup, int brushSize) {
        this.paintGroup = paintGroup;
        paintGroup.setStrokeWidth(calculate(brushSize));
        paintGroup.setStyle(Paint.Style.STROKE);

        dashPathEffect = calculateDashPath();
        paintGroup.setPathEffect(dashPathEffect);
    }


    @Override
    public void setBrushSize(PaintGroup paint, int brushSize) {
        this.brushSize = brushSize;
        haveSettingsChanged = true;
    }


    @Override
    public void onDrawAfterSettingsChanged(){
           dashPathEffect = calculateDashPath();
           paintGroup.setPathEffect(dashPathEffect);
    }



    private float calculate(int brushSize){
        return brushSize / 2f;
    }


    private DashPathEffect calculateDashPath(){
        float onStroke = calculateOnStroke();
        float offStroke = calculateOffStroke();
        return new DashPathEffect(new float[] {onStroke, offStroke}, 0);
    }

    private float calculateOnStroke(){
        return 20 + (brushSize /16f);
    }

    private float calculateOffStroke(){
        return 60 + (brushSize /2f );
    }
}
