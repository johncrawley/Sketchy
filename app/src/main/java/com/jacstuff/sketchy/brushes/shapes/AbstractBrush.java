package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.styles.FillStyle;
import com.jacstuff.sketchy.brushes.styles.Style;
import com.jacstuff.sketchy.paintview.PaintGroup;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractBrush {

    Canvas canvas;
    PaintGroup paintGroup;
    int brushSize;
    Style currentStyle;
    int halfBrushSize;
    BrushShape brushShape;
    private final Map<BrushStyle, Style> styleMap;
    private final FillStyle fillStyle;


    AbstractBrush(Canvas canvas, PaintGroup paintGroup, BrushShape brushShape){
        this.canvas = canvas;
        this.paintGroup = paintGroup;
        styleMap = new HashMap<>();
        fillStyle = new FillStyle();
        currentStyle = fillStyle;
        this.brushShape = brushShape;
    }

    public void add(BrushStyle brushStyle, Style style){
        styleMap.put(brushStyle, style);
    }

    public BrushShape getBrushShape(){
        return this.brushShape;
    }


    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        this.halfBrushSize = brushSize / 2;
        currentStyle.setBrushSize(paintGroup, brushSize);
        currentStyle.notifyStyleChange();
    }


    public void setStyle(BrushStyle style){
        currentStyle = styleMap.get(style);
        if(currentStyle == null){
            currentStyle = fillStyle;
        }
        currentStyle.init(paintGroup, brushSize);
    }


    public void notifyStrokeWidthChanged(){
        currentStyle.notifyStyleChange();
    }


    public final void onTouchDown(float x, float y, Paint paint){
        currentStyle.onDraw();
        onBrushTouchDown(x, y, paint);
    }


    void onBrushTouchDown(float x, float y, Paint paint){}


    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x ,y, paint);
    }


    public void onTouchUp(float x, float y, Paint paint){
        //do nothing
    }

    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint){
        //do nothing
    }

}
