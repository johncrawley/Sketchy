package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class PreviewLayerImpl extends View implements PreviewLayer {


    private Paint paint;
    private Canvas canvas;
    private int width, height;

    public PreviewLayerImpl(Context context) {
        this(context, null);
    }


    public PreviewLayerImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    //@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = bitmapDraw();
        float bitmapX = 0;
        int bitmapY = 0;
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, null);
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }

    private Bitmap bitmapDraw() {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        canvas = new Canvas(bitmap);
        canvas.save();
        //canvas.translate(canvasTranslateX, canvasTranslateY);
        //drawItems();
        //canvasBitmap.restore();
        return bitmap;
    }


    private void initPaint(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
    }



    @Override
    public void updateOnMove(float x, float y) {

    }

    @Override
    public void onTouchUp() {
      //  canvas.
    }

    @Override
    public void setColor(int color) {

    }
}
