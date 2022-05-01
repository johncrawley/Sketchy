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

    private Bitmap halfSizePhoto;
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
        Bitmap amendedBitmap= Bitmap.createBitmap(photoBitmap, 0,0, photoBitmap.getWidth(), photoBitmap.getHeight(), getRotateOnlyMatrix(), true);
        paintView.drawBitmap(amendedBitmap, photoX*2 , photoY * 2);
    }


    public void loadBitmap(Bitmap photo){
        halfSizePhoto = Bitmap.createBitmap(photo, 0,0, photo.getWidth(), photo.getHeight(), getRotateAndShrinkMatrix(), true);
        photoBitmap = photo;
        drawPhotoAtPosition(0,0);
        invalidate();
    }


    private void drawPhotoAtPosition(float xOffset, float yOffset){
        canvas.drawBitmap(halfSizePhoto, xOffset, yOffset, paint);
    }


    private Matrix getRotateAndShrinkMatrix(){
        int angle = getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE ? -90 : 90;
        Matrix m = new Matrix();
        m.postRotate(angle);
        m.postScale(.5f, .5f);
        return m;
    }


    private Matrix getRotateOnlyMatrix(){
        int angle = getScreenOrientation() == Configuration.ORIENTATION_LANDSCAPE ? -90 : 90;
        Matrix m = new Matrix();
        m.postRotate(angle);
        return m;
    }


    int getScreenOrientation(){
        return context.getResources().getConfiguration().orientation;
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
        boolean wasScaled = scaleGestureDetector.onTouchEvent(event);
        if(wasScaled){
            return true;
        }
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        if(action == MotionEvent.ACTION_DOWN){
            diffX = x - photoX;
            diffY = y - photoY;
        }
        else if (action == MotionEvent.ACTION_MOVE){
            canvas.drawRect(0,0,getWidth(), getHeight(), paint);
            photoX = (x - diffX);
            photoY = (y - diffY);
            drawPhotoAtPosition(photoX, photoY);
        }
        invalidate();
        return true;
    }


    private void setupScaleGestureDetector(){
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                System.out.println("^^^ Scaling!");
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                System.out.println("^^^ on scale begin!");
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

            }
        });
    }
}