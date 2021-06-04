package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class WavyStyle extends AbstractStyle implements Style {

    PathDashPathEffect pathDashPathEffect;
    private PaintGroup paintGroup;

    public WavyStyle(PaintGroup paintGroup){
        this.paintGroup = paintGroup;
        paintGroup.setStyle(Paint.Style.STROKE);
        pathDashPathEffect = new PathDashPathEffect(createPath(20, 10, 20), 12, 5, PathDashPathEffect.Style.ROTATE);
    }


    @Override
    public void init(PaintGroup paintGroup, int brushSize ) {
        this.paintGroup = paintGroup;
        this.brushSize = brushSize;
        paintGroup.setStyle(Paint.Style.STROKE);
        assignPath();
    }



    private static Path createPath(float height, float length, float thickness) {
        Path p = new Path();
        p.moveTo(-length, 0);
        p.cubicTo(0, -height, 0, height, length,0);

        p.lineTo(length, thickness);
        p.cubicTo(0, height + thickness, 0, -height + thickness, -length, thickness);
        p.close();


        return p;
    }



    private void assignPath(){
        float length = (float) brushSize / 20;
        pathDashPathEffect = new PathDashPathEffect(createPath(paintGroup.getLineWidth(), length, paintGroup.getLineWidth()), length * 2, 0, PathDashPathEffect.Style.MORPH);
        paintGroup.setPathEffect(pathDashPathEffect);
    }


    void onDrawAfterSettingsChanged(){
        assignPath();
    }

}
