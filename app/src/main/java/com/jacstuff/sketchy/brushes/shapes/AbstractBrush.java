package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.styles.FillStyle;
import com.jacstuff.sketchy.brushes.styles.Style;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractBrush {

    Canvas canvas;
    Paint paint;
    int brushSize;
    Style currentStyle;
    int halfBrushSize;
    private Map<BrushStyle, Style> styleMap;


    AbstractBrush(Canvas canvas, Paint paint){
        this.canvas = canvas;
        this.paint = paint;
        styleMap = new HashMap<>();
        currentStyle = new FillStyle();
    }

    public void add(BrushStyle brushStyle, Style style){
        styleMap.put(brushStyle, style);
    }

    public void setStyle(BrushStyle style){
        currentStyle = styleMap.getOrDefault(style, new FillStyle());
        currentStyle.init(paint, brushSize);
    }

    public void onTouchMove(float x, float y){
        //do nothing
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        this.halfBrushSize = brushSize / 2;
        currentStyle.setBrushSize(paint, brushSize);
    }

}
