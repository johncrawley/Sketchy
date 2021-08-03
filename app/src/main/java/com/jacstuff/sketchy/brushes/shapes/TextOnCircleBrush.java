package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;


public class TextOnCircleBrush extends AbstractBrush implements Brush {


    public TextOnCircleBrush(){
        super(BrushShape.TEXT_ON_CIRCLE);
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        Path path = new Path();
        path.moveTo(0,0);
        path.addCircle(0,0, brushSize * 2, Path.Direction.CW);

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