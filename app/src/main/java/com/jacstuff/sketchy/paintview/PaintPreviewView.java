package com.jacstuff.sketchy.paintview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.BrushFactory;


public class PaintPreviewView extends View {

    private int canvasWidth, canvasHeight;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private Paint paint;
    private int brushSize;
    private Bitmap bitmap;
    private Canvas canvas;
    private BrushStyle currentBrushStyle = BrushStyle.FILL;
    private Brush currentBrush;
    private BrushFactory brushFactory;
    private boolean isCanvasLocked;
    private int angle;
    private PaintView paintView;


    public PaintPreviewView(Context context) {
        this(context, null);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
    }

    public void setPaintView(PaintView paintView){
        this.paintView = paintView;
    }

    public PaintPreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
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
        bitmap.eraseColor(Color.TRANSPARENT);
        canvas = new Canvas(bitmap);
        initBrushes();
    }

    private void resetPreviewCanvas(){
        bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        canvas = new Canvas(bitmap);
    }




    private void initBrushes(){
        //brushFactory = new BrushFactory(canvas, paint, brushSize);
      //  currentBrush = brushFactory.getResettedBrushFor(BrushShape.CIRCLE, currentBrushStyle);
    }


    @Override
    protected void onDraw(Canvas viewCanvas) {
        viewCanvas.save();
        viewCanvas.drawColor(DEFAULT_BG_COLOR);
        viewCanvas.drawBitmap(bitmap,0,0,paint);
        viewCanvas.restore();
    }


    private void log(String msg){
        System.out.println("PaintView: " + msg);
        System.out.flush();
    }


    public void setCurrentColor(int color){
        log("Entered setCurrentColor()");
        paint.setColor(color);
    }


    public Bitmap getBitmap(){
        return bitmap;
    }


    public void set(BrushShape brushShape){
       // currentBrush = brushFactory.getResettedBrushFor(brushShape, currentBrushStyle);
      //  currentBrush.setBrushSize(brushSize);
    }


    @Override
    @SuppressWarnings("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(paintView == null){
            return true;
        }
        paintView.onTouchEvent(event);
        paint.setColor(Color.GRAY);
        updateAngle();
        // canvas.rotate(angle);
        /*
        if(BrushShape.LINE != currentBrush.getBrushShape()){
            canvas.translate(x,y);
            x = 0;
            y = 0;
        }

         */
        performAction(x,y,event.getAction());
        return true;
    }


    private void updateAngle(){
        angle+= 15;
        angle = angle % 360;
    }


    private void performAction(float x, float y, int action){
        if(isCanvasLocked){
            return;
        }

        int size = 100;
        canvas.save();
        switch(action) {
            case MotionEvent.ACTION_DOWN :
               // currentBrush.onTouchDown(x,y);
                canvas.drawRect(x,y, x+size, y+size, paint);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                resetPreviewCanvas();
                canvas.drawRect(x,y, x + size, y + size, paint);
                //currentBrush.onTouchUp(x,y);
                invalidate();
                //canvas.restore();
                break;
            case MotionEvent.ACTION_UP :
               // canvas.restore();
                resetPreviewCanvas();
                invalidate();
        }
    }



}





