package com.jacstuff.sketchy.paint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import com.jacstuff.sketchy.TouchPoint;

import java.util.List;

public class PaintViewImpl implements PaintView {

    private int drawRadius;
    private Bitmap bitmap;


    private int logInterval = 400;

    public PaintViewImpl(){
        this.drawRadius = 20;
        Canvas canvas;
       // bitmap = Bitmap.createBitmap(canvas.)
       // bitmap.setWidth(800);
       // bitmap.setHeight(1080);
       // bitmap.prepareToDraw();
    }

    @Override
    public void paint(Canvas canvas, Paint paint, List<TouchPoint> touchPoints){
        paint.setColor(Color.CYAN);

       for(TouchPoint tp : touchPoints){
          // bitmap.setPixel((int)tp.getX(), (int)tp.getY(), Color.CYAN);
           canvas.drawCircle(tp.getX(), tp.getY(), drawRadius, paint);
        }

        //canvas.drawBitmap(bitmap,0,0, paint);
        //logI("touchPointCount :" + touchPoints.size());
    }



    private int logCounter = 0;
    private void logI(String msg){
        logCounter++;
        if(logCounter > logInterval){

            logCounter = 0;
            Log.i("PaintView", msg);
        }

    }
}
