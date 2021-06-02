package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.model.TextControlsDto;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class TextBrush  extends AbstractBrush implements Brush {

    private TextControlsDto textControlsDto;

    public TextBrush(Canvas canvas, PaintGroup paintGroup, TextControlsDto textControlsDto){
        super(canvas, paintGroup, BrushShape.TEXT);
        this.textControlsDto = textControlsDto;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        paint.setTextSize(halfBrushSize);
       // paint.setFakeBoldText(true);

       // paint.setSubpixelText(true);
       // paint.setLetterSpacing(0.5f);
       // paint.setElegantTextHeight(true);
        paint.setLinearText(true);
        String text = textControlsDto.getText();
        paint.setTextSkewX(textControlsDto.getTextSkew());
        canvas.drawText(text, getCentreX(x,text, paint), y, paint);
    }

    private float getCentreX(float x, String text, Paint paint){
        float[] measuredWidth = new float[1];
        paint.breakText(text, true, 3000, measuredWidth);
        return x - (measuredWidth[0] /2);
    }

    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x, y, paint);
    }


}
