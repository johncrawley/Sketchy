package com.jacstuff.sketchy.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class LoadPhotoPreview extends View {

    private final Context context;
    private Bitmap bitmap;
    private Canvas canvas;
    private final Paint drawPaint, paint;

    public LoadPhotoPreview(Context context) {
        this(context, null);
    }


    public LoadPhotoPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        drawPaint = new Paint();
    }


    public void init(int width, int height) {
        if(width == 0){
            width = 500;
        }
        if(height == 0){
            height = 500;
        }
        log("Entered init() width: " + getWidth() + " height: " + getHeight());
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        invalidate();
    }


    private void log(String msg){
        System.out.println("^^^ LoadPhotoPreview: "+  msg);
    }


    @Override
    protected void onDraw(Canvas viewCanvas) {
       // viewCanvas.save();
        viewCanvas.drawBitmap(bitmap, 0, 0, drawPaint);
       // viewCanvas.restore();
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        log("Entered onTouchEvent()" + event.getX() + ","  + event.getY());
        paint.setColor(Color.DKGRAY);
        canvas.drawCircle(event.getX(),event.getY(),100f, paint);
        invalidate();
        return true;
    }
}