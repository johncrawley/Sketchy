package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.multicolor.ColorSelector;


public class PaintView extends View {

    private int canvasWidth, canvasHeight;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private Paint paint;
    private int brushSize;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private ColorSelector colorSelector;
    private BrushStyle currentBrushStyle = BrushStyle.FILL;
    private Brush currentBrush;
    private BrushFactory brushFactory;
    private boolean wasCanvasModifiedSinceLastSaveOrReset;
    private boolean isCanvasLocked;


    public PaintView(Context context) {
        this(context, null);
    }


    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
    }

    public void notifyPictureSaved(){
        wasCanvasModifiedSinceLastSaveOrReset = false;
    }

    public boolean canvasWasModifiedSinceLastSaveOrReset(){
        return wasCanvasModifiedSinceLastSaveOrReset;
    }

    public void set(BrushStyle brushStyle){
        currentBrushStyle = brushStyle;
        currentBrush.setStyle(brushStyle);
    }


    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
        currentBrush.setBrushSize(brushSize);
    }


    public void init(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint.setColor(DEFAULT_BG_COLOR);
        drawPlainBackground();
        initBrushes();
    }


    public void resetCanvas(){
        wasCanvasModifiedSinceLastSaveOrReset = false;
        isCanvasLocked = true;
        int currentColor = paint.getColor();
        resetStylesAndColors();
        drawPlainBackground();
        invalidate();
        currentBrush.setStyle(currentBrushStyle);
        paint.setColor(currentColor);
        isCanvasLocked = false;
    }


    private void resetStylesAndColors(){
        paint.setColor(DEFAULT_BG_COLOR);
        paint.setPathEffect(null);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    private void drawPlainBackground(){
        canvas.drawRect(0,0, canvasWidth, canvasHeight, paint);
    }


    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        canvas = new Canvas(bitmap);
        paint.setColor(DEFAULT_BG_COLOR);
        initBrushes();
    }


    private void initBrushes(){
        brushFactory = new BrushFactory(canvas, paint, brushSize);
        currentBrush = brushFactory.getResettedBrushFor(BrushShape.CIRCLE, currentBrushStyle);
    }


    public void setColorSelector(ColorSelector colorSelector){
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
        return bitmap;
    }


    public void set(BrushShape brushShape){
        currentBrush = brushFactory.getResettedBrushFor(brushShape, currentBrushStyle);
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        paint.setColor(colorSelector.getNextColor());
        performAction(x, y, event.getAction());
        return true;
    }


    private void performAction(float x, float y, int action){
        wasCanvasModifiedSinceLastSaveOrReset = true;
        if(isCanvasLocked){
            return;
        }
        switch(action) {
            case MotionEvent.ACTION_DOWN :
                currentBrush.onTouchDown(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                currentBrush.onTouchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                currentBrush.onTouchUp(x, y);
                invalidate();
        }

    }




}





