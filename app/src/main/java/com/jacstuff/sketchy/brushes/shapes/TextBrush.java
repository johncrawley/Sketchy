package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class TextBrush  extends AbstractBrush implements Brush {

    private final MainViewModel viewModel;

    public TextBrush(Canvas canvas, PaintGroup paintGroup, MainViewModel viewModel){
        super(canvas, paintGroup, BrushShape.TEXT);
        this.viewModel = viewModel;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        float size = halfBrushSize * 6;
        paint.setTextSize(size);
        String text = viewModel.textBrushText;
        canvas.drawText(text, getCentreX(text, paint), size /3f, paint);
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
