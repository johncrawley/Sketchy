package com.jacstuff.sketchy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.Arrays;
import java.util.List;


public class PaintView extends View {

    public static int BRUSH_SIZE = 20;
    public static final int DEFAULT_COLOR = Color.RED;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;
    private Paint paint;
    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private int strokeWidth;


    private BrushShape brushShape;

    private boolean emboss;
    private boolean blur;
    private MaskFilter mEmboss;
    private MaskFilter mBlur;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);


    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(DEFAULT_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setXfermode(null);
        paint.setAlpha(0xff);

        mEmboss = new EmbossMaskFilter(new float[] {1, 1, 1}, 0.4f, 6, 3.5f);
        mBlur = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
    }


    public void setStyleToBrokenOutline(){

        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
    }

    public void setStyleToFill(){
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setPathEffect(null);
       // Log.i("paintView", "setStyleToFill, current Path effect: " + paint.getPathEffect().getClass().getName());
        //paint.setPathEffect(new )
    }

    public void setStyleToOutline(){

        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(null);

    }
    private int brushSize;

    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
    }

    public void init(int screenWidth, int screenHeight) {

        int widthHeight = Math.min(screenWidth, screenHeight) - 20;
        bitmap = Bitmap.createBitmap(widthHeight, widthHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,widthHeight,widthHeight, paint);
        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
    }

    public void normal() {
        emboss = false;
        blur = false;
    }



    private List<Integer> colors = Arrays.asList(Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.GRAY,
            Color.MAGENTA);

    private int colorIndex = 0;

    private int getNextColor(){
        colorIndex++;
        if(colorIndex >= colors.size()){
            colorIndex = 0;       }
        return colors.get(colorIndex);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawColor(backgroundColor);
        canvas.drawBitmap(bitmap,0,0,paint);

        //paint.setColor(getNextColor());
        canvas.drawBitmap(bitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    public void setCurrentColor(int color){
        paint.setColor(color);
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }


    private void log(String msg){
        Log.i("PaintView", msg);
    }

    private void touchStart(float x, float y) {
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

        drawAt(x,y);

    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        drawAt(x,y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }

    }


    public void setBrushShape(BrushShape brushShape){
        this.brushShape = brushShape;
    }



    private void drawAt(float x, float y){

        switch (brushShape){
            case CIRCLE: canvas.drawCircle(x,y, brushSize/2, paint); break;
            case SQUARE: drawSquare(x, y, brushSize);
        }

    }


    private void drawSquare(float x, float y, int width){
        int halfWidth = width /2;
        float startingX = x - halfWidth;
        float startingY = y - halfWidth;
        canvas.drawRect(startingX,startingY, startingX + width, startingY + width, paint);

    }


    private Rect getRect(float x, float y, int width, int height){
        return new Rect((int)x, (int)y, (int)(x + width), (int)(y+height));

    }

    private void touchUp() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;
        }

        return true;
    }







    public void emboss() {
        emboss = true;
        blur = false;
    }

    public void blur() {
        emboss = false;
        blur = true;
    }

    public void clear() {
        backgroundColor = DEFAULT_BG_COLOR;
        normal();
        invalidate();
    }
}



