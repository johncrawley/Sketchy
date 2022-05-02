package com.jacstuff.sketchy.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.jacstuff.sketchy.paintview.PaintView;


public class LoadPhotoPreview extends View {

    private final Context context;
    private Bitmap bitmap;
    private Canvas canvas;
    private final Paint drawPaint, paint;
    private float photoX, photoY;
    private float diffX, diffY;
    private Bitmap photoBitmap;
    private float currentScale = 1;
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
        drawPaint = new Paint();
        setupScaleGestureDetector();
    }


    public void init(int width, int height) {
        if(width == 0){
            width = 500;
        }
        if(height == 0){
            height = 500;
        }

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        invalidate();
    }

    public void drawAmendedBitmapTo(PaintView paintView){
        Bitmap amendedBitmap= Bitmap.createBitmap(photoBitmap, 0,0, photoBitmap.getWidth(), photoBitmap.getHeight(), getRotateAndScaledMatrix(false), true);
        paintView.drawBitmap(amendedBitmap, photoX*2 , photoY * 2);
    }


    public void loadAndDrawBitmap(Bitmap photo){
        loadAndDrawBitmap(photo, 0,0);
    }


    public void loadAndDrawBitmap(Bitmap photo, int x, int y){
        scaledPhoto = Bitmap.createBitmap(photo, 0,0, photo.getWidth(), photo.getHeight(), getRotateAndScaledMatrix(false), true);
        photoBitmap = photo;
        drawPhotoAtPosition(x,y);
        invalidate();
    }


    private void drawBitmap(){
        scaledPhoto = Bitmap.createBitmap(photoBitmap, 0,0, photoBitmap.getWidth(), photoBitmap.getHeight(), getRotateAndShrinkMatrix(), true);
        drawBackgroundAndPhoto();
    }


    private void drawPhotoAtPosition(float xOffset, float yOffset){
        canvas.drawBitmap(scaledPhoto, xOffset, yOffset, paint);
    }


    private Matrix getRotateAndShrinkMatrix(){
        return getRotateAndScaledMatrix(true);
    }


    public Matrix getRotateAndScaledMatrix(boolean isUsingPreviewScale){
        int angle = getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE ? -90 : 90;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        final float PREVIEW_SCALE_FACTOR = 2;
        float scale = isUsingPreviewScale ? currentScale / PREVIEW_SCALE_FACTOR : currentScale;
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
        canvas.drawRect(0,0,getWidth(), getHeight(), paint);
        drawPhotoAtPosition(photoX, photoY);
        invalidate();
    }


    private void zoomOut(){
        final float minimumScale = 0.05f;
        currentScale -= getScaleIncrement();
        if(currentScale < minimumScale){
            currentScale = minimumScale;
        }
        drawBitmap();
    }


    private void zoomIn(){
        final float maximumScale = 2.0f;
        currentScale += getScaleIncrement();
        if(currentScale > maximumScale){
            currentScale = maximumScale;
        }
        drawBitmap();
    }


    private float getScaleIncrement(){
        float SCALE_INCREMENT = 0.02f;
        return SCALE_INCREMENT * (1 + (currentScale * 4));
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