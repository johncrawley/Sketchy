package com.jacstuff.sketchy.easel;

import android.graphics.Color;
import android.graphics.Paint;

public class PaintHolder {

    private Paint paint;
    private boolean isEnabled;


    public PaintHolder(Paint.Style style){
        paint = new Paint();
        paint.setStyle(style);
        paint.setColor(Color.BLACK);
    }

    public Paint getPaint(){
        return paint;
    }

    public boolean isEnabled(){
        return  isEnabled;
    }

    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }
}
