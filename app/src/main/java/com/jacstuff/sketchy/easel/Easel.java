package com.jacstuff.sketchy.easel;

import static android.graphics.Paint.Style.FILL_AND_STROKE;
import static android.graphics.Paint.Style.STROKE;

import android.graphics.Canvas;
import android.graphics.Paint;


import java.util.List;

public class Easel {

    private Canvas canvas;
    private PaintHolder fillPaint, strokePaint, shadowPaint;



    public Easel(){
        fillPaint = new PaintHolder(Paint.Style.FILL);
        strokePaint = new PaintHolder(STROKE);
        shadowPaint = new PaintHolder(FILL_AND_STROKE);
    }


    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }


    public Paint getFillPaint(){
        return fillPaint.getPaint();
    }


    public Paint getStrokePaint(){
        return strokePaint.getPaint();
    }

    public List<Paint> getActivePaints(){
        return List.of(fillPaint.getPaint());
    }


    public Canvas getCanvas(){
        return canvas;
    }
}
