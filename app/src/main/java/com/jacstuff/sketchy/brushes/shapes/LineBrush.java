package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.line.DefaultLineDrawer;
import com.jacstuff.sketchy.brushes.shapes.line.LineDrawer;
import com.jacstuff.sketchy.brushes.shapes.line.LineOutlineDrawer;
import com.jacstuff.sketchy.paintview.PaintGroup;

import java.util.HashMap;
import java.util.Map;

public class LineBrush extends AbstractBrush implements Brush {

    private float xDown, yDown;
    private LineDrawer currentLineDrawer;
    private Map<BrushStyle, LineDrawer> lineDrawerMap;


    public LineBrush(Canvas canvas, PaintGroup paintGroup) {
        super(canvas, paintGroup, BrushShape.LINE);
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
        lineDrawerMap.put(BrushStyle.SPIKED,outlineDrawer );
        lineDrawerMap.put(BrushStyle.DOUBLE_EDGE,outlineDrawer );
        lineDrawerMap.put(BrushStyle.TRANSLATE, outlineDrawer );
        currentLineDrawer = lineDrawerMap.get(BrushStyle.FILL);
    }


    @Override
    public void onBrushTouchDown(float x, float y, Paint paint){
        xDown = x;
        yDown = y;
    }


    @Override
    public void onTouchMove(float x, float y, Paint paint) {
        currentLineDrawer.draw(xDown, yDown, x, y, brushSize, paint);
    }


    @Override
    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint) {
        currentLineDrawer.draw(xDown - offsetX, yDown - offsetY, x -offsetX, y - offsetY, brushSize, paint);
    }


    @Override
    public void onTouchUp(float x, float y, Paint paint) {
        currentLineDrawer.draw(xDown, yDown, x, y, brushSize, paint);
    }


    @Override
    public void setStyle(BrushStyle brushStyle){
        super.setStyle(brushStyle);
        currentLineDrawer = lineDrawerMap.get(brushStyle);
    }
}