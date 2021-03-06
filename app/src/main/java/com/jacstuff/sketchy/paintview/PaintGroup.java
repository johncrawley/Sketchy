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

    public String getStyle(){
        return paints.get(0).getStyle().toString();
    }


    public void setPathEffect(PathEffect pathEffect){
        for(Paint p: paints){
            p.setPathEffect(pathEffect);
        }
    }

    public void setTextSkewX(float value){
        for(Paint p: paints){
            p.setTextSkewX(value);
        }
    }

    public void setStrokeWidth(float strokeWidth){
        for(Paint p: paints){
            p.setStrokeWidth(strokeWidth);
        }
    }
    public void setTextSize(float textSize){
        for(Paint p: paints){
            p.setTextSize(textSize);
        }
    }

    public void setTextBold(boolean b){
        for(Paint p : paints){
            p.setFakeBoldText(b);
        }
    }


    public void setStrikeThrough(boolean b){
        for(Paint p : paints){
            p.setStrikeThruText(b);
        }
    }


    public void setTextUnderline(boolean b){
        for(Paint p : paints){
            p.setUnderlineText(b);
        }
    }



}
