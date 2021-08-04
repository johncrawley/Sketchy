package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;

public class PathBrush extends AbstractBrush implements Brush {

    private float previousX, previousY;
    private Path totalPath;
    /*

        Will need a special case for shadows:
            so, disable shadows on brush down and brush move
            - but save and append to path, with each brush move
            - on brush up, draw the whole shadow path, then the whole normal path again.
            - then clear the saved path

     */

    public PathBrush(){
        super(BrushShape.PATH);
        drawerType = DrawerFactory.Type.PATH;
        totalPath = new Path();
    }


    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        totalPath.reset();
        totalPath.moveTo(p.x, p.y);
        previousX = p.x;
        previousY = p.y;
    }


    @Override
    public void onTouchMove(Point p, Canvas canvas, Paint paint){
        currentStyle.onDraw();
        Path path = new Path();
        path.moveTo(previousX -p.x, previousY -p.y);
        totalPath.lineTo(p.x, p.y);
        float x1 = previousX -p.x;
        float y1 = previousY -p.y;

        path.lineTo(0,0);
        canvas.drawPath(path, paint);
        previousX = p.x;
        previousY = p.y;
    }


    @Override
    public void onTouchMoveKaleidoscope(Point p, Canvas canvas, Paint paint){
        currentStyle.onDraw();
        Path path = new Path();
        float transformedPreviousX = p.x - previousX;
        float transformedPreviousY = p.y - previousY;
        path.moveTo(transformedPreviousX, transformedPreviousY);
        path.lineTo(0,500);
        canvas.drawPath(path, paint);
        previousX = p.x;
        previousY = p.y;
    }


    private void log(String msg){
        System.out.println("PathBrush: " + msg);
    }

    public void onTouchUp(float x, float y, Paint paint){
        canvas.drawPath(totalPath, paint);
    }

}