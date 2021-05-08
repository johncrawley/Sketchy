package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TransparentArrowView extends View {

    private int canvasTranslateX,canvasTranslateY, width, height;
    private int angle = 0;
    private Paint paint;
    private Canvas canvasBitmap;
    private boolean isViewDrawn = false;


    public TransparentArrowView(Context context) {
        super(context);
    }

    public TransparentArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    public TransparentArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }




    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void translateXToMiddle(){
        this.canvasTranslateX = width / 2;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.width = right-left;
        this.height = bottom - top;
        defaultAttributes();
        isViewDrawn = true;

        int middleX = (right - left) / 2;
        int middleY = (bottom - top) / 2;
        int quarterY = middleY / 2;

        int arrowHalfWidth = bottom/20;
        int distanceFromEdge = right / 12;
        int distanceFromRightEdge = right - distanceFromEdge;
        int lengthOfArrow = 60;
        int arrowMiddleX = 0;
        int topCornerY = quarterY -arrowHalfWidth;
        int bottomCornerY = quarterY + arrowHalfWidth;

        int rightArrowPointX = (right - distanceFromEdge) - 50;
        int leftArrowPointX = distanceFromEdge;

        int tempLeftPointX = 100;
        int tempRightPointY = right - 100;


        int distanceFromLeftEdge = distanceFromEdge + lengthOfArrow;

        System.out.println("arrow view on layout change = left: " + left + " top: " + top + " right: " + right + " bottom: " + bottom);

        path = new Path();
        path.moveTo(tempRightPointY, quarterY);
        path.lineTo(distanceFromRightEdge, topCornerY);
        path.lineTo(distanceFromRightEdge, bottomCornerY);
        path.close();


        path.moveTo(tempLeftPointX, quarterY);
        path.lineTo(distanceFromLeftEdge, topCornerY);
        path.lineTo(distanceFromLeftEdge, bottomCornerY);
        path.lineTo(distanceFromEdge, quarterY);
        path.close();

    }


    //@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isViewDrawn)
            defaultAttributes();
        isViewDrawn = true;
        Bitmap bitmap = bitmapDraw();
        float bitmapX = 0;
        int bitmapY = 0;
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, null);
    }

    Paint shapePaint;

    private void initPaint(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

        shapePaint = new Paint();
        shapePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        shapePaint.setColor(Color.DKGRAY);
    }


    private void defaultAttributes() {
    }


    private Bitmap bitmapDraw() {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        canvasBitmap = new Canvas(bitmap);
        canvasBitmap.save();
        canvasBitmap.translate(canvasTranslateX, canvasTranslateY);

        if(angle != 0){
            canvasBitmap.rotate(angle);
        }
        drawItems();
        canvasBitmap.restore();
        return bitmap;
    }

    private Path path;

    private void drawItems(){
        canvasBitmap.drawPath(path, shapePaint);
    }



}