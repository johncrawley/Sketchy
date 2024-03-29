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

import androidx.annotation.NonNull;

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
    private float previewScaleFactor = 2;
    private boolean wasScaled;
    private float initialScale;

    private Bitmap scaledPhoto;
    private ScaleGestureDetector scaleGestureDetector;
    private int rotation = 0;
    private int initialRotation = 0;


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


    public void init(int width, int height, int previewScaleFactor) {
        width = width == 0 ? 500 : width;
        height = height == 0 ? 500 : height;
        this.previewScaleFactor = previewScaleFactor;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawRect(0,0,width,height, paint);
    }


    public void drawAmendedBitmapTo(PaintView paintView){
        Bitmap amendedBitmap = Bitmap.createBitmap(photoBitmap,
                0,
                0,
                photoBitmap.getWidth(),
                photoBitmap.getHeight(),
                getRotateAndScaledMatrix(true),
                true);

        paintView.drawBitmap(amendedBitmap,
                photoX * previewScaleFactor ,
                photoY * previewScaleFactor);
    }


    public void loadAndDrawBitmap(Bitmap photo){
        loadAndDrawBitmap(photo, true);
    }


    public void loadAndDrawBitmap(Bitmap photo, boolean isRotatedInitially){
        if(isRotatedInitially){
            initialRotation = 90;
        }
        setInitialScale(photo.getWidth());
        photoBitmap = photo;
        drawBitmap();
    }


    public void rotate(){
        rotation += 90;
        if(rotation >= Integer.MAX_VALUE - 100){
            rotation = 0;
        }
        drawBitmap();
    }


    public Matrix getRotateAndScaledMatrix(boolean isUsingPreviewScale){
        Matrix matrix = new Matrix();
        matrix.postRotate((getInitialAngle() + rotation) % 360);
        float scale = isUsingPreviewScale ? currentScale * 2 : currentScale;
        matrix.postScale(scale, scale);
        return matrix;
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


    private int getInitialAngle(){
        if(initialRotation == 0){
            return 0;
        }
        return getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE ? 0 : 90;
    }


    private int getScreenOrientation(){
        return context.getResources().getConfiguration().orientation;
    }


    private void drawBitmap(){
        scaledPhoto = Bitmap.createBitmap(photoBitmap, 0,0, photoBitmap.getWidth(), photoBitmap.getHeight(), getRotateAndScaledMatrix(false), true);
        drawBackgroundAndPhoto();
    }


    private void setInitialScale(float photoWidth){
        currentScale = (canvas.getWidth() *2)/ photoWidth;
        currentScale += 0.02f; //required extra width because the matrix scaling operation falls short
        initialScale = currentScale;
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
        final float minimumScale = 0.03f;
        currentScale -= getScaleIncrement();
        if(currentScale < minimumScale){
            currentScale = minimumScale;
        }
        drawBitmap();
    }


    private void zoomIn(){
        final float maximumScale = Math.max(1f , initialScale);
        currentScale += getScaleIncrement();
        if(currentScale > maximumScale){
            currentScale = maximumScale;
        }
        drawBitmap();
    }


    private float getScaleIncrement(){
        if(currentScale < 0.08f){
            return 0.005f;
        }
        return currentScale < 0.3 ? 0.01f : 0.05f;
    }


    private void setupScaleGestureDetector(){
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {

            private float previousSpan;

            @Override
            public boolean onScaleBegin(@NonNull ScaleGestureDetector scaleGestureDetector) {
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

            @Override public boolean onScale(@NonNull ScaleGestureDetector scaleGestureDetector) {return false;}
            @Override public void onScaleEnd(@NonNull ScaleGestureDetector scaleGestureDetector) {}
        });
    }
}