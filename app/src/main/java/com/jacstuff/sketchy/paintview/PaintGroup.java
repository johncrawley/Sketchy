package com.jacstuff.sketchy.paintview;

import android.graphics.Paint;
import android.graphics.PathEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PaintGroup {


    private Collection<Paint> paints;

    public PaintGroup(Paint...paintItems){

        paints = Arrays.asList(paintItems);

    }


    public void setStyle(Paint.Style style){
        for(Paint p : paints){
            p.setStyle(style);
        }

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
