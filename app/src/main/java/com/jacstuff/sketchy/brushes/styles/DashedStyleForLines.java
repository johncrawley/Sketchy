package com.jacstuff.sketchy.brushes.styles;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class DashedStyleForLines extends DashedStyle implements Style {

    private int brushSize;
    private PaintGroup paintGroup;
    private DashPathEffect dashPathEffect;

    public DashedStyleForLines(PaintGroup paintGroup, int initialBrushSize){
        super(paintGroup);
        this.paintGroup = paintGroup;
        brushSize = initialBrushSize;
        dashPathEffect = calculateDashPath();
    }


    @Override
    public void init(PaintGroup paintGroup, int brushSize) {
        this.paintGroup = paintGroup;
        paintGroup.setStyle(Paint.Style.STROKE);

        dashPathEffect = calculateDashPath();
        paintGroup.setPathEffect(dashPathEffect);
    }


    @Override
    public void onDrawAfterSettingsChanged(){
           dashPathEffect = calculateDashPath();
           paintGroup.setPathEffect(dashPathEffect);
    }


    private DashPathEffect calculateDashPath(){
        float onStroke = calculateOnStroke();
        float offStroke = calculateOffStroke();
        return new DashPathEffect(new float[] {onStroke, offStroke}, 0);
    }

    private float calculateOnStroke(){
        return 20 + (paintGroup.getLineWidth() /16f);
    }

    private float calculateOffStroke(){
        return 60 + (paintGroup.getLineWidth() );
    }
}
