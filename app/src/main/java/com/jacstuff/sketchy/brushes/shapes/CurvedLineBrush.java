package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.shapes.initializer.LineInitializer;
import com.jacstuff.sketchy.brushes.shapes.line.DefaultLineDrawer;
import com.jacstuff.sketchy.brushes.shapes.line.LineDrawer;
import com.jacstuff.sketchy.brushes.shapes.line.LineOutlineDrawer;

import java.util.HashMap;
import java.util.Map;


public class CurvedLineBrush extends AbstractBrush implements Brush {

    private float downX, downY, upX, upY, curveX, curveY;
    private LineDrawer currentLineDrawer;
    private Map<BrushStyle, LineDrawer> lineDrawerMap;
    public enum State { DRAW_LINE, DRAW_CURVE }
    private State state;
    private final Path path;


    public CurvedLineBrush() {
        super(BrushShape.LINE);
        brushInitializer = new LineInitializer();
        drawerType = DrawerFactory.Type.CURVE;
        state = State.DRAW_LINE;
        path = new Path();
    }

    @Override
    void postInit(){
        setupLineDrawers();
    }


    private void setupLineDrawers(){
        lineDrawerMap = new HashMap<>();
        LineDrawer defaultLineDrawer = new DefaultLineDrawer(canvas);
        LineDrawer outlineDrawer = new LineOutlineDrawer(canvas);
        lineDrawerMap.put(BrushStyle.FILL, defaultLineDrawer);
        lineDrawerMap.put(BrushStyle.BROKEN_OUTLINE, defaultLineDrawer);
        lineDrawerMap.put(BrushStyle.OUTLINE,outlineDrawer );
        lineDrawerMap.put(BrushStyle.JAGGED,outlineDrawer );
        lineDrawerMap.put(BrushStyle.WAVY, defaultLineDrawer );
        lineDrawerMap.put(BrushStyle.SPIKED,outlineDrawer );
        lineDrawerMap.put(BrushStyle.DOUBLE_EDGE,outlineDrawer );
        lineDrawerMap.put(BrushStyle.TRANSLATE, outlineDrawer );
        currentLineDrawer = lineDrawerMap.get(BrushStyle.FILL);
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
            path.close();
            canvas.drawPath(path, paint);
            return;
        }
        currentLineDrawer.draw(downX , downY, x, y, brushSize, paint);
    }

    private void log(String msg){
        System.out.println("^^^ CurvedLineBrush: " + msg);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        if(state == State.DRAW_CURVE){
            path.reset();
            path.moveTo(downX, downY);
            log("drawing curve from: " + downX + "," + downY + " controlPoint: " + x +"," + y + " ending: "+  upX + "," + upY);
            path.quadTo( x,y, upX, upY);
            //path.close();
            canvas.drawPath(path, paint);
            state = State.DRAW_LINE;
            return;
        }
        currentLineDrawer.draw(downX - offsetX, downY - offsetY, x -offsetX, y - offsetY, brushSize, paint);
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
        currentLineDrawer = lineDrawerMap.get(brushStyle);
    }


    @Override
    public boolean isUsingPlacementHelper(){
        return false;
    }
}