package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;


public class TextOnCircleBrush extends AbstractBrush implements Brush {


    public TextOnCircleBrush(){
        super(BrushShape.TEXT_ON_CIRCLE);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint) {
        Path path = new Path();
        path.moveTo(x,y);
        path.addCircle(x,y, brushSize * 2, Path.Direction.CW);

        paint.setLinearText(true);
        String text = mainViewModel.textBrushText;
        float offset = 0f;
        canvas.drawTextOnPath(text, path, offset, offset, paint);
    }


    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
        paintGroup.setTextSize(brushSize/4f);
    }

}