package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.model.TextControlsDto;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class TextOnCircleBrush extends AbstractBrush implements Brush {


    private TextControlsDto textControlsDto;

    public TextOnCircleBrush(Canvas canvas, PaintGroup paintGroup, TextControlsDto textControlsDto){
        super(canvas, paintGroup, BrushShape.TEXT);
        this.textControlsDto = textControlsDto;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint) {
        Path path = new Path();
        path.moveTo(x,y);
        path.addCircle(x,y, brushSize * 2, Path.Direction.CW);

        paint.setLinearText(true);
        String text = textControlsDto.getText();
        paint.setTextSkewX(textControlsDto.getTextSkew());
        float offset = 0f;
        canvas.drawTextOnPath(text, path, offset, offset, paint);
    }


    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
        paintGroup.setTextSize(brushSize/4f);
    }

}