package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;


public class TextOnCircleBrush extends AbstractBrush implements Brush {


    private final MainViewModel viewModel;

    public TextOnCircleBrush(Canvas canvas, PaintGroup paintGroup, MainViewModel viewModel){
        super(canvas, paintGroup, BrushShape.TEXT);
        this.viewModel = viewModel;
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint) {
        Path path = new Path();
        path.moveTo(x,y);
        path.addCircle(x,y, brushSize * 2, Path.Direction.CW);

        paint.setLinearText(true);
        String text = viewModel.textBrushText;
        float offset = 0f;
        canvas.drawTextOnPath(text, path, offset, offset, paint);
    }


    @Override
    public void setBrushSize(int brushSize) {
        super.setBrushSize(brushSize);
        paintGroup.setTextSize(brushSize/4f);
    }

}