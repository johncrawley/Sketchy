package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
public class SpiralBrush extends AbstractBrush implements Brush {

    private int spiralProgression;

    public SpiralBrush(){
        super(BrushShape.SPIRAL);
    }


    public void setBrushSize(int size){
        super.setBrushSize(size);
        spiralProgression = this.brushSize /20;
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        drawAltSpiral(paint);
       // onDraw(canvas, paint, 20, 12);
    }


    private void drawAltSpiral(Paint paint){
        float angle;
        double x,y;
        Path path = new Path();
        for (int i=0; i< halfBrushSize; i++) {
            angle = (0.1f + spiralProgression) * i;
            x= ((1 + angle) * Math.cos(angle));
            y= ((1 + angle) * Math.sin(angle));
            path.lineTo((float)x, (float)y);
        }
        canvas.drawPath(path, paint);
    }

    private void drawSpiral(Canvas canvas, Paint paint, int spacing, int numberOfTwists){

        int centerX = 0;
        int centerY = 0;
        Path path = new Path();

        int left = centerX - spacing;
        int right = centerX + spacing;
        int top = centerY - spacing;
        int bottom = centerY + spacing;

        int startAngle = 0;
        int arcAngle = 180;
        path.moveTo(right, bottom);
        for(int i = 0; i < numberOfTwists * 2; i++){
            path.addArc(left, top, right, bottom, startAngle, arcAngle);
            top -= spacing /2;
            bottom += spacing /2;
            if(i%2 == 0){
                right += spacing;
            }
            else{
                left -= spacing;
            }
            startAngle = startAngle + arcAngle;
        }
        canvas.drawPath(path, paint);
    }

}