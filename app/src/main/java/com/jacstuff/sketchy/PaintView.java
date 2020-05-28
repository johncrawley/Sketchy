package com.jacstuff.sketchy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.sketchy.controls.brushSize.BrushSizeConfig;
import com.jacstuff.sketchy.multicolor.ColorSelector;


public class PaintView extends View {

    public static final int DEFAULT_COLOR = Color.RED;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private Paint paint;
    private int brushSize, halfBrushSize;
    private BrushShape brushShape;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private ColorSelector colorSelector;
    private BrushStyle currentBrushStyle = BrushStyle.FILL;
    private  BrushSizeConfig brushSizeConfig;



    public PaintView(Context context) {
        this(context, null);
    }


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(DEFAULT_COLOR);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
    }


    public void set(BrushStyle brushStyle){
        currentBrushStyle = brushStyle;
        brushSizeConfig.set(brushStyle);
        switch (brushStyle){
            case FILL:
                setStyleToFill(); break;
            case OUTLINE:
                setStyleToOutline();break;
            case BROKEN_OUTLINE:
                setStyleToBrokenOutline();break;
        }
    }

    public void setStyleToBrokenOutline(){
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
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setPathEffect(null);
    }


    public void setStyleToOutline(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(null);
    }


    public void setBrushSize(int brushSize){

        this.brushSize = brushSize;
        log("Entered setBrushSize("  + brushSize + ")");
        this.halfBrushSize = brushSize / 2;
    }


    public void init(int canvasWidth, int canvasHeight, BrushSizeConfig brushSizeConfig) {
        bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0, canvasWidth, canvasHeight, paint);
        this.brushSizeConfig = brushSizeConfig;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        canvas = new Canvas(bitmap);
        paint.setColor(Color.WHITE);
    }


    public void setColorSelector(ColorSelector colorSelector){
        log("setting current color selector: " + colorSelector.getClass().getName());
        this.colorSelector = colorSelector;
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


    private float xDown, yDown;

    private void touchStart(float x, float y) {
        xDown = x;
        yDown = y;
        drawAt(x,y);
    }


    private void touchMove(float x, float y) {
        drawAt(x,y);
    }


    public void set(BrushShape brushShape){
        setStyle();
        this.brushShape = brushShape;
        brushSizeConfig.set(brushShape);
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
        colorSelector.resetCurrentIndex();
        if(brushShape == BrushShape.LINE){
            drawLine(x,y);
        }
    }


    private void drawLine(float x, float y){
        BrushStyle oldBrushStyle = currentBrushStyle;
        setLineStyle();

        float oldWidth = paint.getStrokeWidth();
        if(currentBrushStyle == BrushStyle.OUTLINE){
            paint.setStrokeWidth(1);
            drawLineOutline(x, y);
            paint.setStrokeWidth(oldWidth);
        }
        else{
            paint.setStrokeWidth(halfBrushSize);
            canvas.drawLine(xDown, yDown, x, y, paint);
        }


        if(oldBrushStyle == BrushStyle.BROKEN_OUTLINE){
            setStyleToBrokenOutline();
        }
        paint.setStrokeWidth(halfBrushSize);
    }


    private void drawLineOutline(float x, float y){

        Point p1 = new Point((int) xDown, (int)yDown);
        Point p2 = new Point((int) x,(int) y);
        Point p3 = getPointPerpendicularTo(xDown, yDown, x, y, false);
        Point p4 = getPointPerpendicularTo(x, y, xDown, yDown, true);
        Point p5, p6;
        float distance = getDistance( p1, p2);
        if(distance < brushSize){
            p5 = p3;
            p6 = p4;
        }
        else {
            float divisor = distance / brushSize;
            p5 = getPointFromPerpLine(p1, p3, divisor);
            p6 = getPointFromPerpLine(p2, p4, divisor);
        }
        drawLineFrom(p1, p2);
        drawLineFrom(p1, p5);
        drawLineFrom(p2, p6);
        drawLineFrom(p5, p6);
    }


    private float getDistance(Point p1, Point p2){
        double ac = Math.abs(p2.y - p1.y);
        double cb = Math.abs(p2.x - p1.x);
        return (float)Math.hypot(ac, cb);
    }


    private void drawLineFrom(Point p1, Point p2){
        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
    }

    private Point getPointPerpendicularTo(float x1, float y1, float x2, float y2, boolean isOppositeSide){
        float xDiff = x2 - x1;
        float yDiff = y2 - y1;
        if(isOppositeSide){
            yDiff *= -1;
            xDiff *= -1;
        }
        float x3 = x1 + yDiff;
        float y3 = y1 - xDiff;
        return new Point((int)x3, (int) y3);
    }


    private Point getPointFromPerpLine(Point p1, Point p2, float divisor){
        float top = divisor - 1;
        float x = ((top / divisor) * p1.x ) + ((1.0f/divisor) * p2.x );
        float y = ((top / divisor) * p1.y ) + ((1.0f/divisor) * p2.y );
        return new Point((int)x, (int) y);
    }


    private void log(String msg){
        Log.i("PaintView", msg);
    }


    private void setLineStyle(){
        if(currentBrushStyle == BrushStyle.BROKEN_OUTLINE){
                setStyleToBrokenOutlineForLines();
        }
    }


    private void setStyle(){
        switch(currentBrushStyle) {
            case FILL:
                setStyleToFill();
                break;

            case OUTLINE:
                setStyleToOutline();
                break;

            case BROKEN_OUTLINE:
                setStyleToBrokenOutline();
        }
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        paint.setColor(colorSelector.getNextColor());

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
