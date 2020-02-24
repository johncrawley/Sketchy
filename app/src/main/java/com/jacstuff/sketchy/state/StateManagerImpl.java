package com.jacstuff.sketchy.state;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.jacstuff.sketchy.TouchPoint;
import com.jacstuff.sketchy.paint.PaintView;
import com.jacstuff.sketchy.paint.PaintViewImpl;

import java.util.ArrayList;
import java.util.List;

public class StateManagerImpl implements  StateManager {


    private Context context;
    private int width, height;
    private List<TouchPoint> touchPoints;
    private PaintView view;

    public StateManagerImpl(Context context, int width, int height){

        this.touchPoints = new ArrayList<>();
        this.view = new PaintViewImpl();
        this.context = context;
        this.width = width;
        this.height = height;
    }

    @Override
    public void handleTouchPoints(List<TouchPoint> touchPoints) {
        this.touchPoints = new ArrayList<>(touchPoints);

        Log.i("StateMngrImpl", "touchpoint count: " + this.touchPoints.size());
       // for(TouchPoint tp : this.touchPoints){
        //    Log.i("StateMngr", "tp :"  + tp.getX() + "," + tp.getY());
        //}
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        view.paint(canvas, paint, touchPoints);
    }
}
