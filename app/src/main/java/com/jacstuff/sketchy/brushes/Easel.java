package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class Easel {

    private Canvas canvas;
    private Paint fillPaint, strokePaint, shadowPaint;


    PaintGroup temp;

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

    public Paint getFillPaint(){
        return fillPaint;
    }

    public Paint getStrokePaint(){
        return strokePaint;
    }
}
