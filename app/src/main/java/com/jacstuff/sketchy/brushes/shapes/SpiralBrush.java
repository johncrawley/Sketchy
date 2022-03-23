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
        spiralProgression = this.brushSize /10;
    }

    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        float angle;
        double x,y;
        Path path = new Path();
        for (int i=0; i< brushSize; i++) {
          //  angle = 0.1f * i;
           // angle = 0.1f * spiralProgression * i;
            //angle = (0.1f + spiralProgression) * i;
            float scale =  1 - (float)i / brushSize;
           // int numRevs = 10;
            //float angle2 = (float)(i * 2 * Math.PI / (brushSize / numRevs));
            angle = 0.1f * i;
            x= ((1 + angle) * Math.cos(angle)) + (0.1 * i);
            y= ((1 + angle) * Math.sin(angle))+ (0.1 * i);
            path.lineTo((float)x, (float)y);
        }
       // canvas.drawPath(path, paint);
        onDraw(canvas, paint, 20, 12);
    }


    protected void onDraw(Canvas canvas, Paint paint, int spacing, int numberOfTwists){

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