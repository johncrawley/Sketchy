package com.jacstuff.sketchy.brushes.shapes.onestep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.AbstractShape;

public class TextBrush extends AbstractShape {

    protected float textSize;
    protected String text;
    boolean wasTextSizeChanged;


    public TextBrush(){
        this(BrushShape.TEXT);
    }


    public TextBrush(BrushShape brushShape){
        super(brushShape);
    }


    public String getText(){
        return text;
    }


    public void setText(String text){
        this.text = text;
    }


    @Override
    public void draw(PointF p, Canvas canvas, Paint paint){
        if(text.trim().isEmpty()){
           // mainActivity.toast(R.string.toast_text_brush_is_empty);
            return;
        }
        updateTextSizeOn(paint);
        canvas.drawText(text, getCentreX(text, paint), textSize /3f, paint);
    }

    private void updateTextSizeOn(Paint paint){
        if(wasTextSizeChanged){
            paint.setTextSize(textSize);
            wasTextSizeChanged = false;
        }
    }


    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
        textSize = brushSize * 1.2f;
        wasTextSizeChanged = true;
    }


    private float getCentreX(String text, Paint paint){
        float[] measuredWidth = new float[1];
        paint.breakText(text, true, 3000, measuredWidth);
        return - (measuredWidth[0] /2);
    }


}
