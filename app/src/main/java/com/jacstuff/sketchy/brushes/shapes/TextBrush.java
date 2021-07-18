package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class TextBrush  extends AbstractBrush implements Brush {

    private final MainViewModel viewModel;
    private float textSize;

    public TextBrush(Canvas canvas, PaintGroup paintGroup, MainViewModel viewModel){
        super(canvas, paintGroup, BrushShape.TEXT);
        this.viewModel = viewModel;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        String text = viewModel.textBrushText;
        canvas.drawText(text, getCentreX(text, paint), textSize /3f, paint);
    }


    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
        textSize = brushSize * 1.2f;
        paintGroup.setTextSize(textSize);
    }


    private float getCentreX(String text, Paint paint){
        float[] measuredWidth = new float[1];
        paint.breakText(text, true, 3000, measuredWidth);
        return - (measuredWidth[0] /2);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x, y, paint);
    }

}
