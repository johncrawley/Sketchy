package com.jacstuff.sketchy.brushes.styles;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class TranslateStyle extends AbstractStyle implements Style {

    PathDashPathEffect pathDashPathEffect;
    private PaintGroup paintGroup;


    public TranslateStyle(PaintGroup paintGroup){
        this.paintGroup = paintGroup;
        pathDashPathEffect = new PathDashPathEffect(createPath(20, 10), 12, 5, PathDashPathEffect.Style.ROTATE);
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
        pathDashPathEffect = new PathDashPathEffect(createPath(outerY, innerY), 12, 5, PathDashPathEffect.Style.TRANSLATE);
        paintGroup.setPathEffect(pathDashPathEffect);
    }


    private static Path createPath(float outerY, float innerY) {
        Path p = new Path();
        float x = 6;
        p.moveTo(-x, outerY);
        p.lineTo(x,outerY);
        p.lineTo(x,innerY);
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