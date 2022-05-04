package com.jacstuff.sketchy.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.jacstuff.sketchy.paintview.PaintView;


public class LoadPhotoPreview extends View {

    private final Context context;
    private Bitmap bitmap;
    private Canvas canvas;
    private final Paint drawPaint, paint, borderPaint;
    private float photoX, photoY;
    private float diffX, diffY;
    private Bitmap photoBitmap;
    private float currentScale = 1;
    private final float PREVIEW_SCALE_FACTOR = 2;
    boolean wasScaled;

    private Bitmap scaledPhoto;
    private ScaleGestureDetector scaleGestureDetector;


    public LoadPhotoPreview(Context context) {
        this(context, null);
    }


    public LoadPhotoPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(Color.DKGRAY);
        drawPaint = new Paint();
        setupScaleGestureDetector();
    }


    public void init(int width, int height) {
        width = width == 0 ? 500 : width;
        height = height == 0 ? 500 : height;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        invalidate();
    }


    public void drawAmendedBitmapTo(PaintView paintView){
        Bitmap amendedBitmap = Bitmap.createBitmap(photoBitmap, 0,0, photoBitmap.getWidth(), photoBitmap.getHeight(), getRotateAndScaledMatrix(false), true);
        paintView.drawBitmap(amendedBitmap, photoX * PREVIEW_SCALE_FACTOR , photoY * PREVIEW_SCALE_FACTOR);
    }


    public void loadAndDrawBitmap(Bitmap photo){
        setInitialScale(photo.getWidth());
        loadAndDrawScaledBitmap(photo);
    }


    public void loadAndDrawScaledBitmap(Bitmap photo){
        scaledPhoto = Bitmap.createBitmap(photo, 0,0, photo.getWidth(), photo.getHeight(), getRotateAndScaledMatrix(true), true);
        photoBitmap = photo;
        drawBackgroundAndPhoto();
    }


    private void drawBitmap(){
        scaledPhoto = Bitmap.createBitmap(photoBitmap, 0,0, photoBitmap.getWidth(), photoBitmap.getHeight(), getRotateAndScaledMatrix(true), true);
        drawBackgroundAndPhoto();
    }


    private void log(String msg){
        System.out.println("^^^ LoadPhotoPreview: " + msg);
    }


    private void setInitialScale(float photoWidth){
        log("photoWidth: " + photoWidth + " getWidth() : " + canvas.getWidth());
        currentScale = (canvas.getWidth())/ photoWidth;
    }


    public Matrix getRotateAndScaledMatrix(boolean isUsingPreviewScale){
        int angle = getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE ? -90 : 90;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        float scale = isUsingPreviewScale ? currentScale : currentScale * PREVIEW_SCALE_FACTOR;
        log("getRotateAndScaledMatrix("  + isUsingPreviewScale + ") scale used: " + scale);
        matrix.postScale(scale, scale);
        return matrix;
    }


    int getScreenOrientation(){
        return context.getResources().getConfiguration().orientation;
    }


    @Override
    protected void onDraw(Canvas viewCanvas) {
        viewCanvas.drawBitmap(bitmap, 0, 0, drawPaint);
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        if(action == MotionEvent.ACTION_DOWN){
            diffX = x - photoX;
            diffY = y - photoY;
        }
        else if (action == MotionEvent.ACTION_MOVE){
            scaleGestureDetector.onTouchEvent(event);
            if(wasScaled){
                return true;
            }
            redrawPhoto(x,y);
        }
        else if (action == MotionEvent.ACTION_UP){
            wasScaled = false;
        }
        return true;
    }


    private void redrawPhoto(float x, float y){
        photoX = (x - diffX);
        photoY = (y - diffY);
        drawBackgroundAndPhoto();
    }


    private void drawBackgroundAndPhoto(){
        drawBackground();
        drawPhotoBorderRect();
        drawPhotoAtPosition(photoX, photoY);
        invalidate();
    }


    private void drawBackground(){
        canvas.drawRect(0,0,getWidth(), getHeight(), paint);
    }


    private void drawPhotoBorderRect(){
        int borderWidth = 4;
        float left = photoX - borderWidth;
        float top = photoY - borderWidth;
        float right = photoX + scaledPhoto.getWidth() + borderWidth;
        float bottom = photoY + scaledPhoto.getHeight() +  borderWidth;
        canvas.drawRect(new RectF(left, top, right, bottom), borderPaint);
    }


    private void drawPhotoAtPosition(float xOffset, float yOffset){
        canvas.drawBitmap(scaledPhoto, xOffset, yOffset, paint);
    }


    private void zoomOut(){
        final float minimumScale = 0.1f;
        currentScale -= getScaleIncrement();
        if(currentScale < minimumScale){
            currentScale = minimumScale;
        }
        drawBitmap();
    }


    private void zoomIn(){
        final float maximumScale = 1f;
        currentScale += getScaleIncrement();
        if(currentScale > maximumScale){
            currentScale = maximumScale;
        }
        drawBitmap();
    }


    private float getScaleIncrement(){
        return currentScale < 0.3 ? 0.01f : 0.05f;
    }


    private void setupScaleGestureDetector(){
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {

            private float previousSpan;

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                float currentSpan = scaleGestureDetector.getCurrentSpan();
                if(currentSpan > previousSpan){
                    zoomIn();
                }
                else{
                    zoomOut();
                }
                previousSpan = currentSpan;
                wasScaled = true;
                return false;
            }

            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }
        });
    }
}