package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class SpikedStyle extends AbstractStyle implements Style {

    private PathDashPathEffect pathDashPathEffect;
    private PaintGroup paintGroup;

    public SpikedStyle(PaintGroup paintGroup){
        pathDashPathEffect = new PathDashPathEffect(createPath(20), 12, 5, PathDashPathEffect.Style.ROTATE);
    }


    @Override
    public void init(PaintGroup paintGroup, int brushSize ) {
        this.paintGroup = paintGroup;
        this.brushSize = brushSize;
        paintGroup.setStyle(Paint.Style.STROKE);
        assignPath();
    }


    private static Path createPath(float lineWidthFactor) {

        Path p = new Path();
        p.moveTo(1 * lineWidthFactor, 1);
        p.lineTo(1,-1);
        p.lineTo(-2,-1 * lineWidthFactor);
        p.lineTo(-1, 2);
        p.close();
        return p;
    }


    private void assignPath(){
        float spikeFactor = paintGroup.getLineWidth() / 4;
        pathDashPathEffect = new PathDashPathEffect(createPath(spikeFactor), 12, 5, PathDashPathEffect.Style.ROTATE);
        paintGroup.setPathEffect(pathDashPathEffect);
    }


    void onDrawAfterSettingsChanged(){
        assignPath();
    }

}


