package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;

public class CurvedLineBrush extends AbstractBrush implements Brush {

    private float downX, downY, upX, upY;
    public enum State { DRAW_LINE, DRAW_CURVE }
    private State state;
    private final Path path;


    public CurvedLineBrush() {
        super(BrushShape.CURVE);
        brushInitializer = new LineInitializer();
        drawerType = DrawerFactory.Type.CURVE;
        state = State.DRAW_LINE;
        path = new Path();
    }


    @Override
    public void onBrushTouchDown(Point p, Canvas canvas, Paint paint) {
        if (state == State.DRAW_LINE) {
            downX = p.x;
            downY = p.y;
        }
    }

    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        if(state == State.DRAW_CURVE){
            path.reset();
            path.moveTo(downX, downY);
            path.quadTo(x,y,upX, upY);
            canvas.drawPath(path, paint);
            return;
        }

        canvas.drawLine(downX, downY, x, y, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        if(state == State.DRAW_CURVE){
            path.reset();
            path.moveTo(downX, downY);
            path.quadTo( x,y, upX, upY);
            //path.close();
            canvas.drawPath(path, paint);
            state = State.DRAW_LINE;
            return;
        }
        canvas.drawLine(downX, downY, x, y, paint);
        upX = x;
        upY = y;
        state = State.DRAW_CURVE;
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint) {
        onTouchUp(x, y, 0,0, paint);
    }


    @Override
    public void setStyle(BrushStyle brushStyle){
        super.setStyle(brushStyle);
    }


    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }
}