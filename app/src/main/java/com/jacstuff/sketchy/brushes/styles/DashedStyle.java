package com.jacstuff.sketchy.brushes.styles;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class DashedStyle extends AbstractStyle implements Style {

    private PaintGroup paintGroup;

    public DashedStyle(PaintGroup paintGroup){
        this.paintGroup = paintGroup;
        assignPath();
    }


    @Override
    public void init(PaintGroup paintGroup, int brushSize ) {
        this.paintGroup = paintGroup;
        this.brushSize = brushSize;
        paintGroup.setStyle(Paint.Style.STROKE);
        assignPath();
    }


    private void assignPath(){
        float lineWidthFactor = paintGroup.getLineWidth() / 2;
        float baseOffStroke = 30;
        float onStroke = 6;
        float offStroke = baseOffStroke + lineWidthFactor;
        paintGroup.setPathEffect(new DashPathEffect(new float[] {onStroke, offStroke}, 0));
    }


    void onDrawAfterSettingsChanged(){
        assignPath();
    }

}


