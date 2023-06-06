package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;

import java.util.ArrayList;
import java.util.List;

public class SmoothPathBrush extends AbstractBrush implements Brush {

    private Path path, smoothPath;
    private final List<Point> smoothPoints;
    private boolean isSmoothPathReady;

    public SmoothPathBrush(){
        super(BrushShape.PATH);
        brushInitializer = new LineInitializer();
        drawerType = DrawerFactory.Type.SMOOTH_PATH;
        smoothPoints = new ArrayList<>(1000);
    }


    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        setBrushSize((int)paint.getStrokeWidth());
        path = new Path();
        path.moveTo(p.x, p.y);
        smoothPoints.clear();
        smoothPoints.add(p);
        isSmoothPathReady = false;
    }


    @Override
    public void onTouchMove(Point p, Canvas canvas, Paint paint){
        currentStyle.onDraw();
        path.lineTo(p.x, p.y);
        smoothPoints.add(p);
        canvas.drawPath(path, paint);
    }


    @Override
    public void onTouchUp(Point p, Canvas canvas, Paint paint){
       setupSmoothPath(p);
       canvas.drawPath(smoothPath, paint);
    }


    private void setupSmoothPath(Point negativeOffset){
        if(isSmoothPathReady){
            return;
        }
        calculateSmoothPoints();
        createSmoothPath(negativeOffset);
        isSmoothPathReady = true;
    }


    private void calculateSmoothPoints(){
        int numberOfRounds = 3;
        for(int i=0; i<numberOfRounds; i++){
            adjustSmoothPoints();
        }
    }


    private void adjustSmoothPoints(){
        if(smoothPoints.size() < 5){
            return;
        }
        for(int i = 1; i < smoothPoints.size()-1; i++){
            Point point = smoothPoints.get(i);
            int x = (point.x + smoothPoints.get(i-1).x + smoothPoints.get(i+1).x)/3;
            int y = (point.y + smoothPoints.get(i-1).y + smoothPoints.get(i+1).y)/3;
            point.x = x;
            point.y = y;
        }
    }


    private void createSmoothPath(Point negativeOffset){
        smoothPath = new Path();
        smoothPath.moveTo(smoothPoints.get(0).x - negativeOffset.x,
                smoothPoints.get(0).y - negativeOffset.y);
        for(int i=1; i<smoothPoints.size(); i++){
            smoothPath.lineTo(smoothPoints.get(i).x - negativeOffset.x,
                    smoothPoints.get(i).y - negativeOffset.y);
        }
    }



}