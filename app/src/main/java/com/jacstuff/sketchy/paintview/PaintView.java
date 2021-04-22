package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.sketchy.brushes.BlurType;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.brushes.ShadowType;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.paintview.helpers.BlurHelper;
import com.jacstuff.sketchy.paintview.helpers.GradientHelper;
import com.jacstuff.sketchy.paintview.helpers.ShadowHelper;


public class PaintView extends View {

    private int canvasWidth, canvasHeight;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private Paint paint;
    private int brushSize, halfBrushSize;
    private Bitmap bitmap;
    private Canvas canvas;
    private ColorSelector colorSelector;
    private BrushStyle currentBrushStyle = BrushStyle.FILL;
    private Brush currentBrush;
    private BrushFactory brushFactory;
    private boolean wasCanvasModifiedSinceLastSaveOrReset;
    private boolean isCanvasLocked;
    private int angle;
    private int previousColor = Color.WHITE;

    private ShadowHelper shadowHelper;
    private BlurHelper blurHelper;
    private GradientHelper gradientHelper;


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
        paint.setDither(true);
        shadowHelper = new ShadowHelper(paint);
        blurHelper = new BlurHelper(paint);
        gradientHelper = new GradientHelper(paint);
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

    public void set(BlurType blurType){
        blurHelper.setBlurType(blurType);
    }


    public void set(GradientType gradientType){
        gradientHelper.setGradientType(gradientType);
    }


    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
        halfBrushSize = brushSize /2;
        currentBrush.setBrushSize(brushSize);
        gradientHelper.updateBrushSize(brushSize);
    }


    public void setRadialGradientRadius(int radiusFactor){
        gradientHelper.setGradientRadius(radiusFactor, canvasWidth);
    }

    public void setLineWidth(int lineWidth){
        paint.setStrokeWidth(lineWidth);
    }

    public void setBlurRadius(int blurRadius){
        blurHelper.setBlurRadius(blurRadius);
    }

    public void set(ShadowType shadowType){
        shadowHelper.set(shadowType);
    }


    public void init(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

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
        int currentColor = paint.getColor();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0, canvasWidth, canvasHeight, paint);
        paint.setColor(currentColor);
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
    protected void onDraw(Canvas viewCanvas) {
        viewCanvas.save();
        viewCanvas.drawColor(DEFAULT_BG_COLOR);
        viewCanvas.drawBitmap(bitmap,0,0,paint);
        viewCanvas.restore();
    }


    public void setCurrentColor(int color){
        paint.setColor(color);
    }


    public Bitmap getBitmap(){
        return bitmap;
    }


    public void set(BrushShape brushShape){
        currentBrush = brushFactory.getResettedBrushFor(brushShape, currentBrushStyle);
        currentBrush.setBrushSize(brushSize);
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(!(event.getAction() != MotionEvent.ACTION_DOWN && currentBrush.getBrushShape() == BrushShape.LINE)){
            int color = colorSelector.getNextColor();
            if(color != paint.getColor()){
                previousColor = paint.getColor();
            }
            paint.setColor(color);

            blurHelper.assignBlur();
            shadowHelper.assignShadow();
            gradientHelper.assignGradient(x,y, color, previousColor);
        }

        if(BrushShape.LINE == currentBrush.getBrushShape()){
            canvas.save();
            performAction(x, y, event.getAction());
            canvas.restore();
            return true;
        }
        updateAngle();
        canvas.save();
        canvas.translate(x,y);
       // canvas.rotate(angle);
        performAction(event.getAction());

        canvas.restore();
        return true;
    }


    private void updateAngle(){
        angle+= 15;
        angle = angle % 360;
    }


    public void setShadowSize(int size){
        shadowHelper.setShadowSize(size, halfBrushSize);
    }


    private void performAction(int action){
        performAction(0,0, action);
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
                currentBrush.onTouchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                currentBrush.onTouchUp(x,y);
                invalidate();
        }
    }

}





