package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushShape;
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
    BrushShape brushShape;
    private Map<BrushStyle, Style> styleMap;
    private FillStyle fillStyle;


    AbstractBrush(Canvas canvas, Paint paint, BrushShape brushShape){
        this.canvas = canvas;
        this.paint = paint;
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

    public void setStyle(BrushStyle style){
        currentStyle = styleMap.get(style);
        if(currentStyle == null){
            currentStyle = fillStyle;
        }
        currentStyle.init(paint, brushSize);
    }


    public void onTouchMove(float x, float y){
        //do nothing
    }


    public void onTouchUp(float x, float y){
        // do nothing
    }


    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        this.halfBrushSize = brushSize / 2;
        currentStyle.setBrushSize(paint, brushSize);
    }

}
