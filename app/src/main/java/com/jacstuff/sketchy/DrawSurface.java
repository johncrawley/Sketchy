package com.jacstuff.sketchy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jacstuff.sketchy.state.StateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import android.os.Handler;

/**
 * Created by John on 29/08/2017.
 * The draw surface object intersects the canvas.
 */

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback {

    private Context context;
    private AnimationThread animationThread;
    protected SurfaceHolder surfaceHolder;
    private ScheduledExecutorService scheduledExecutorService;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private StateManager stateManager;

    private final SurfaceHolder sh = surfaceHolder;
    private List<TouchPoint> touchPoints;



    public DrawSurface(Context context){
        super(context);
    }

    public DrawSurface(Context context, StateManager stateManager) {
        super(context);
        this.stateManager = stateManager;
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        scheduledExecutorService = Executors.newScheduledThreadPool(4);
        touchPoints = new ArrayList<>();


    }



    public boolean onTouchEvent(MotionEvent event) {
    //@Override
    //public void performClick(MotionEvent event) {

       // List<TouchPoint> touchPoints = new ArrayList<>();
        //Log.i("DrawSurface", "new touch event handled!");

        for (int i = 0; i < event.getPointerCount(); i++) {
            float x = event.getX(i);
            float y = event.getY(i);
            boolean isReleasedTouchPoint = isUpEvent(event) && event.getActionIndex() == i;
            touchPoints.add(new TouchPoint(x,y, isReleasedTouchPoint));
            //break; // let's just handle the first touch point for now.
        }
        stateManager.handleTouchPoints(touchPoints);
        //draw();
        return true;
    }

    @Override
    public void onDraw(Canvas canvas){
        for(TouchPoint touchPoint : touchPoints){

            canvas.drawCircle(touchPoint.getX(), touchPoint.getY(), 20,  paint);

        }

    }

    private void draw(){

        Canvas canvas = null;
                    try {
            canvas = sh.lockCanvas(null);
            synchronized (sh) {
                if (canvas == null){
                 return;
                }
                //fillBackground(canvas);
                stateManager.draw(canvas,paint);
            }
        }
        finally {
                      if (canvas != null) {
                            sh.unlockCanvasAndPost(canvas);
                        }
                    }
    }




    private boolean isUpEvent(MotionEvent event){
        this.invalidate();
        return event.getActionMasked() == MotionEvent.ACTION_POINTER_UP || event.getAction() == MotionEvent.ACTION_UP ;
    }


    public void surfaceCreated(SurfaceHolder holder) {
        animationThread = new AnimationThread(context);

        scheduledExecutorService = Executors.newScheduledThreadPool(4);
        scheduledExecutorService.scheduleAtFixedRate(animationThread, 0, 20, TimeUnit.MILLISECONDS);
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) 	  {

        animationThread.setSurfaceSize(width, height);
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
        scheduledExecutorService.shutdown();
    }



    class AnimationThread extends Thread{


        private final SurfaceHolder sh = surfaceHolder;
        Context ctx;

        AnimationThread(Context context){
            this.ctx = context;
        }

        public void run() {

                stateManager.update();

                Canvas canvas = null;
                try {
                    canvas = sh.lockCanvas(null);
                    synchronized (sh) {
                        if (canvas == null){
                            return;
                        }
                        //fillBackground(canvas);
                       // stateManager.draw(canvas,paint);
                        //List<TouchPoint> drawPoints = new ArrayList<>(touchPoints);
                        //paint.setColor(Color.RED);
                       // for(TouchPoint tp : drawPoints){
                          //  canvas.drawCircle(tp.getX(),tp.getY(), 20, paint);
                        //}
                    }
                } finally {
                    if (canvas != null) {
                        sh.unlockCanvasAndPost(canvas);
                    }
                }
        }

        private void fillBackground(Canvas canvas){

            paint.setColor(Color.BLACK);
            canvas.drawRect(0,0,1080, 1980, paint);

        }

        private void setSurfaceSize(int width, int height) {
            synchronized (sh) {

            }
        }


    }
}
