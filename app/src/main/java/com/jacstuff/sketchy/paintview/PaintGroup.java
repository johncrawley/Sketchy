package com.jacstuff.sketchy.paintview;

import android.graphics.Paint;
import android.graphics.PathEffect;

import java.util.Arrays;
import java.util.List;

public class PaintGroup {


    private List<Paint> paints;

    public PaintGroup(Paint...paintItems){
        paints = Arrays.asList(paintItems);
    }


    public void setStyle(Paint.Style style){
        for(Paint p : paints){
            p.setStyle(style);
        }
    }

    public float getLineWidth(){
       return paints.get(0).getStrokeWidth();
    }

    public void setPathEffect(PathEffect pathEffect){
        for(Paint p: paints){
            p.setPathEffect(pathEffect);
        }
    }

    public void setStrokeWidth(float strokeWidth){
        for(Paint p: paints){
            p.setStrokeWidth(strokeWidth);
        }
    }


}
