package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class DoubleEdgeStyle   extends AbstractStyle implements Style {

    private PathDashPathEffect pathEffect;
    private PaintGroup paintGroup;

    public DoubleEdgeStyle(PaintGroup paintGroup){
        this.paintGroup = paintGroup;
        pathEffect = new PathDashPathEffect(createPath(20, 10), 12, 5, PathDashPathEffect.Style.ROTATE);
    }


    @Override
    public void init(PaintGroup paintGroup, int brushSize ) {
        this.paintGroup = paintGroup;
        this.brushSize = brushSize;
        paintGroup.setStyle(Paint.Style.STROKE);
        assignPath();
    }


    void onDrawAfterSettingsChanged(){
        assignPath();
    }


    private void assignPath(){
        float edgeDepth = 1 + (paintGroup.getLineWidth() /8);
        float outerY = 4 * edgeDepth;
        float innerY = 3 * edgeDepth;
        pathEffect = new PathDashPathEffect(createPath(outerY, innerY), 12, 1, PathDashPathEffect.Style.MORPH);
        paintGroup.getDrawPaint().setPathEffect(pathEffect);
        paintGroup.getShadowPaint().setPathEffect(pathEffect);
        paintGroup.getPreviewPaint().setPathEffect(null);
    }


    private static Path createPath(float outerY, float innerY) {
        Path p = new Path();
        float x = 6;
        p.moveTo(-x, outerY);
        p.lineTo(x, outerY);
        p.lineTo(x, innerY);
        p.lineTo(-x, innerY);
        p.close();
        p.moveTo(-x, -outerY);
        p.lineTo(x,-outerY);
        p.lineTo(x,-innerY);
        p.lineTo(-x, -innerY);
        p.close();
        return p;
    }

}