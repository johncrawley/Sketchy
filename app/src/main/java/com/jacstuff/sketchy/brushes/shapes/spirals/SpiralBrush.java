package com.jacstuff.sketchy.brushes.shapes.spirals;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.Brush;

public class SpiralBrush extends AbstractSpiral {

    private final Path path;

    public SpiralBrush() {
        super(BrushShape.SPIRAL);
        path = new Path();
    }


    public void setBrushSize(int size) {
        super.setBrushSize(size);
    }


    @Override
    public void generatePath(PointF p) {
        path.reset();
       // saveSettings(paint);
       // int spacing = (int) paint.getStrokeWidth() + 10; //viewModel.spiralExtraSpacing;
        int spacing = 10;
        int numberOfTwists = brushSize / 20;
        int left = -spacing;
        int right = spacing;
        int top = -spacing;
        int bottom = spacing;
        int startAngle = 0;
        int arcAngle = 180;
        int halfSpacing = spacing / 2;
        path.moveTo(right, bottom);

        for (int i = 0; i < numberOfTwists * 2; i++) {
            path.addArc(left, top, right, bottom, startAngle, arcAngle);
            top -= halfSpacing;
            bottom += halfSpacing;
            if (i % 2 == 0) {
                right += spacing;
            } else {
                left -= spacing;
            }
            startAngle = startAngle + arcAngle;
        }
        //recallSettings(paint);
    }




}