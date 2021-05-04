package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

public class TriangleBrush extends AbstractBrush implements Brush {


    private Point leftPoint, rightPoint, topPoint, centreBottom;

    public TriangleBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.TRIANGLE);
        leftPoint = new Point();
        rightPoint = new Point();
        topPoint = new Point();
        centreBottom = new Point();
    }


    @Override
    public void onTouchDown(float x, float y, Paint paint){
        currentStyle.onDraw(paintGroup);
        centreBottom.set(x, y + halfBrushSize);
        leftPoint.set(centreBottom.x - halfBrushSize, centreBottom.y);
        rightPoint.set(centreBottom.x + halfBrushSize, centreBottom.y);
        topPoint.set(x, y - halfBrushSize);

        Path path = new Path();
        path.moveTo(leftPoint.x, leftPoint.y);
        path.lineTo(topPoint. x, topPoint.y);
        path.lineTo(rightPoint.x, rightPoint.y);
        path.close();
        canvas.drawPath(path, paint);
    }
}