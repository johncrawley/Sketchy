package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintGroup;

import androidx.annotation.RequiresApi;

public class CrescentBrush extends AbstractBrush implements Brush {

    private final Path path;
    private final android.graphics.Point bottomPoint;
    private final android.graphics.Point topPoint;
    private final android.graphics.Point upperDipPoint;
    private final android.graphics.Point lowerDipPoint;
    private boolean hasSizeChanged = false;

    public CrescentBrush(Canvas canvas, PaintGroup paintGroup){
        super(canvas, paintGroup, BrushShape.CRESENT);
        path = new Path();
        bottomPoint = new android.graphics.Point(0,0);
        topPoint = new android.graphics.Point(0,0);
        upperDipPoint = new android.graphics.Point(0,0);
        lowerDipPoint = new Point(0,0);
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        clipPaint = new Paint();
    }
    final Paint redPaint;
    final Paint clipPaint;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBrushTouchDown(float x, float y, Paint paint) {
        readjustPointsOnSizeChanged();
        path.reset();
        float adjust = halfBrushSize / 3f;
        Path clipPath = new Path();
        clipPath.addCircle(-adjust, 0, halfBrushSize, Path.Direction.CW);
        //canvas.clipPath(clipPath);
        canvas.clipOutPath(clipPath);
        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawCircle(0, 0, halfBrushSize, paint);
       // paint.setXfermode(null);
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(0,0, paint);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        hasSizeChanged = true;
    }

    private void readjustPointsOnSizeChanged(){
        if(!hasSizeChanged){
            return;
        }
        hasSizeChanged = false;
        int adjustment = brushSize /8;
        int third = halfBrushSize /3;
        bottomPoint.x = -halfBrushSize + adjustment ;
        bottomPoint.y = halfBrushSize;
        topPoint.x = 0 ;
        topPoint.y = -halfBrushSize + adjustment;
        upperDipPoint.x = -halfBrushSize /6;
        upperDipPoint.y = -halfBrushSize / 6;
        upperDipPoint.x = halfBrushSize/3;
        upperDipPoint.y = third;
        lowerDipPoint.x = halfBrushSize - adjustment;
        lowerDipPoint.y = halfBrushSize - adjustment;
    }





}