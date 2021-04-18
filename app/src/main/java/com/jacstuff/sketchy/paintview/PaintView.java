package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.multicolor.ColorSelector;


public class PaintView extends View {

    private int canvasWidth, canvasHeight, canvasMidX, canvasMidY;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private Paint paint;
    private int brushSize, halfBrushSize, radialGradientRadius;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private ColorSelector colorSelector;
    private BrushStyle currentBrushStyle = BrushStyle.FILL;
    private Brush currentBrush;
    private BrushFactory brushFactory;
    private boolean wasCanvasModifiedSinceLastSaveOrReset;
    private boolean isCanvasLocked;
    private int angle;
    private GradientType gradientType = GradientType.NONE;
    private int previousColor = Color.WHITE;

    private int clampRadialGradientFactor = 12;
    private int clampRadialGradientRadius = 10;



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
        radialGradientRadius = 10;
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


    public void set(GradientType gradientType){
        this.gradientType = gradientType;
    }


    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
        halfBrushSize = brushSize /2;
        currentBrush.setBrushSize(brushSize);
    }


    public void setRadialGradientRadius(int radiusFactor){
        radialGradientRadius = 1 + canvasWidth / radiusFactor;
        clampRadialGradientRadius = 1 + radialGradientRadius * clampRadialGradientFactor;
    }


    public void init(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        canvasMidX = canvasWidth /2;
        canvasMidY = canvasHeight / 2;
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

    private void log(String msg){
        System.out.println("PaintView: " + msg);
        System.out.flush();
    }

    public void setCurrentColor(int color){
        log("Entered setCurrentColor()");
        paint.setColor(color);
        LinearGradient linearGradient = new LinearGradient(200, 200, 600, 600, color, Color.YELLOW, Shader.TileMode.MIRROR);
        paint.setShader(linearGradient);
       // paint.setShader(new LinearGradient(0, 50, 0, brushSize, color, Color.BLUE, Shader.TileMode.CLAMP));
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
        int color = colorSelector.getNextColor();
        if(color != paint.getColor()){
            previousColor = paint.getColor();
        }
        paint.setColor(color);
        assignGradient(x,y, color, previousColor);
        //angle += 15;
        canvas.save();
        canvas.translate(x,y);
        //canvas.rotate(angle);
        performAction(x, y, event.getAction());
        canvas.restore();
        return true;
    }


    private void assignGradient(float x, float y, int color, int oldColor){
        switch(gradientType){
            case NONE:
                paint.setShader(null);
                break;
            case DIAGONAL_MIRROR:
                int x1 = (int)x + (halfBrushSize);
                int y1 = (int)y + (halfBrushSize);
                paint.setShader(new LinearGradient(x, y, x1, y1, color, oldColor, Shader.TileMode.MIRROR));
                break;
            case RADIAL_CLAMP:
                paint.setShader(new RadialGradient(0, 0, clampRadialGradientRadius, new int []{color,oldColor}, null, Shader.TileMode.CLAMP ));
                break;
            case RADIAL_REPEAT:
                paint.setShader(new RadialGradient(0, 0, radialGradientRadius, new int []{color,oldColor}, null, Shader.TileMode.REPEAT ));
                break;
            case RADIAL_MIRROR:
                paint.setShader(new RadialGradient(0, 0, radialGradientRadius, new int []{color,oldColor}, null, Shader.TileMode.MIRROR ));

        }
    }


    private void performAction(float x, float y, int action){
        wasCanvasModifiedSinceLastSaveOrReset = true;
        if(isCanvasLocked){
            return;
        }
        switch(action) {
            case MotionEvent.ACTION_DOWN :
                currentBrush.onTouchDown(0,0);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                currentBrush.onTouchMove(0,0);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                currentBrush.onTouchUp(0,0);
                invalidate();
        }

    }




}





