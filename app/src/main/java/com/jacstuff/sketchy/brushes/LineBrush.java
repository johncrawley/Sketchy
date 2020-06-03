package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.BrushStyle;
import com.jacstuff.sketchy.brushes.line.DefaultLineDrawer;
import com.jacstuff.sketchy.brushes.line.LineDrawer;
import com.jacstuff.sketchy.brushes.line.LineOutlineDrawer;

import java.util.HashMap;
import java.util.Map;

public class LineBrush extends AbstractBrush implements Brush {

    private float xDown, yDown;
    private LineDrawer currentLineDrawer;
    private Map<BrushStyle, LineDrawer> lineDrawerMap;
    private BrushStyle currentStyle;

    LineBrush(Canvas canvas, Paint paint, int brushSize) {
        super(canvas, paint, brushSize);
        setupLineDrawers();
    }


    private void setupLineDrawers(){
        lineDrawerMap = new HashMap<>();
        LineDrawer defaultLineDrawer = new DefaultLineDrawer(canvas, paint);
        lineDrawerMap.put(BrushStyle.FILL, defaultLineDrawer);
        lineDrawerMap.put(BrushStyle.BROKEN_OUTLINE, defaultLineDrawer);
        lineDrawerMap.put(BrushStyle.OUTLINE, new LineOutlineDrawer(canvas, paint));
        currentLineDrawer = lineDrawerMap.get(BrushStyle.FILL);
    }

    @Override
    public void onTouchDown(float x, float y) {
        xDown = x;
        yDown = y;
    }

    @Override
    public void setStyle(BrushStyle style){
        super.setStyle(style);
        currentStyle = style;
        currentLineDrawer = lineDrawerMap.get(style);
        if(currentLineDrawer == null){
            return;
        }
        currentLineDrawer.initStrokeWidth(brushSize);
    }

    public void reset(int brushSize){
        setBrushSize(brushSize);
        paint.setStrokeWidth(halfBrushSize);
    }


    @Override
    public void setBrushSize(int brushSize){
        super.setBrushSize(brushSize);
        super.setStyle(currentStyle);
    }


    @Override
    public void onTouchUp(float x, float y) {
        //paint.setStrokeWidth(halfBrushSize);
        currentLineDrawer.draw(xDown, yDown, x, y, brushSize);
    }

}