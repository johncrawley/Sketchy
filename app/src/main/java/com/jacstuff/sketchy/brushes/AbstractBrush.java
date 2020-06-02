package com.jacstuff.sketchy.brushes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.BrushStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class AbstractBrush {

    Canvas canvas;
    Paint paint;
    int brushSize;
    int halfBrushSize;
    private Map<BrushStyle, BiConsumer<Paint, Integer>> styleMap;


    AbstractBrush(Canvas canvas, Paint paint, int brushSize){
        this.canvas = canvas;
        this.paint = paint;
        styleMap = new HashMap<>();
        setBrushSize(brushSize);
    }

    void add(BrushStyle style, BiConsumer<Paint, Integer> biConsumer){
        styleMap.put(style, biConsumer);
    }

    public void setStyle(BrushStyle style){
        BiConsumer<Paint, Integer> consumer = styleMap.get(style);
        if(consumer != null){
            consumer.accept(paint, brushSize);
        }

    }

    public void onTouchMove(float x, float y){
        //do nothing
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        this.halfBrushSize = brushSize / 2;
    }
    public void reset(){

    }
    public void reset(int brushSize){
        reset();
    }


}
