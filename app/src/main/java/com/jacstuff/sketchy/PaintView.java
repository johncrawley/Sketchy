package com.jacstuff.sketchy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.sketchy.multicolor.MulticolorHandler;

import java.util.List;


public class PaintView extends View {

    public static final int DEFAULT_COLOR = Color.RED;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private Paint paint;
    private int brushSize, halfBrushSize;
    private BrushShape brushShape;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private MulticolorHandler multicolorHandler;
    private BrushStyle currentBrushStyle = BrushStyle.FILL;



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
        paint.setStrokeCap(Paint.Cap.SQUARE);
        multicolorHandler = new MulticolorHandler();
    }


    public void setStyleToBrokenOutline(){
        currentBrushStyle = BrushStyle.BROKEN_OUTLINE;
        paint.setStyle(Paint.Style.STROKE);
        float onStroke = 15;
        float offStroke = 36;

        paint.setPathEffect(new DashPathEffect(new float[] {onStroke, offStroke}, 0));
    }


    public void setStyleToBrokenOutlineForLines(){
        paint.setStyle(Paint.Style.STROKE);
        float onStroke =  20 + (halfBrushSize /8f);
        float offStroke = 40 + (halfBrushSize );

        paint.setPathEffect(new DashPathEffect(new float[] {onStroke, offStroke}, 0));
    }

    public void setStyleToFill(){
        currentBrushStyle = BrushStyle.FILL;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setPathEffect(null);
    }


    public void setStyleToOutline(){
        currentBrushStyle = BrushStyle.OUTLINE;
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(null);
    }


    public void setBrushSize(int brushSize){

        this.brushSize = brushSize;
        this.halfBrushSize = brushSize / 2;


    }


    public void init(int canvasWidth, int canvasHeight) {
        bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0, canvasWidth, canvasHeight, paint);
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        canvas = new Canvas(bitmap);
        paint.setColor(Color.WHITE);
    }

    public void setMultiColor(List<Color> colors){
        multicolorHandler.enable(colors );
    }


    public void setSingleColorMode(){
        multicolorHandler.disable();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawColor(DEFAULT_BG_COLOR);
        canvas.drawBitmap(bitmap,0,0,paint);
        canvas.drawBitmap(bitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    public void setCurrentColor(int color){
        paint.setColor(color);
    }

    public Bitmap getBitmap(){
       // return Bitmap.createBitmap(bitmap, 0,0, bitmap.getWidth(), this.getHeight());
        return bitmap;
    }


    private float touchdownX, touchDownY;

    private void touchStart(float x, float y) {
        touchdownX = x;
        touchDownY = y;
        drawAt(x,y);
    }


    private void touchMove(float x, float y) {
        drawAt(x,y);
    }


    public void setBrushShape(BrushShape brushShape){
        this.brushShape = brushShape;
    }


    private void drawAt(float x, float y){

        switch (brushShape){
            case CIRCLE: canvas.drawCircle(x,y, halfBrushSize, paint); break;
            case SQUARE: drawSquare(x, y);
        }
    }


    private void drawSquare(float x, float y){
        float left = x - halfBrushSize;
        float top = y - halfBrushSize;
        float right = left + brushSize;
        float bottom = top + brushSize;
        canvas.drawRect(left, top, right, bottom, paint);
    }


    private void touchUp(float x, float y) {
        if(multicolorHandler.isEnabled()){
            multicolorHandler.resetIndex();
        }
        if(brushShape == BrushShape.LINE){
            drawLine(x,y);
        }
    }


    private void drawLine(float x, float y){
        BrushStyle oldBrushStyle = currentBrushStyle;
        if(currentBrushStyle == BrushStyle.BROKEN_OUTLINE){
            setStyleToBrokenOutlineForLines();
        }
        float oldWidth = paint.getStrokeWidth();
        paint.setStrokeWidth(halfBrushSize);
        canvas.drawLine(touchdownX, touchDownY, x, y, paint);
        paint.setStrokeWidth(oldWidth);

        if(oldBrushStyle == BrushStyle.BROKEN_OUTLINE){
            setStyleToBrokenOutline();
        }
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(multicolorHandler.isEnabled()){
            paint.setColor(multicolorHandler.getNextColor());
        }

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
                touchUp(x, y);
                invalidate();
                break;
        }
        return true;
    }



}
