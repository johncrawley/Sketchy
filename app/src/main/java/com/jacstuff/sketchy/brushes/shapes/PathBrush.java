package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;
import com.jacstuff.sketchy.paintview.PaintView;


public class PathBrush extends AbstractBrush implements Brush {

    private float previousX, previousY;

    public PathBrush(){
        super(BrushShape.PATH);
        brushInitializer = new LineInitializer();
        drawerType = DrawerFactory.Type.PATH;
    }


    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        previousX = p.x;
        previousY = p.y;
    }


    @Override
    public void onTouchMove(Point p, Canvas canvas, Paint paint){
        currentStyle.onDraw();
        Path path = new Path();
        path.moveTo(previousX -p.x, previousY -p.y);
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
        path.lineTo(0,0);
        canvas.drawPath(path, paint);
        previousX = p.x;
        previousY = p.y;
    }


    private void log(String msg){
        System.out.println("PathBrush: " + msg);
    }

}