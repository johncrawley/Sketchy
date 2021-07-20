package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

import java.util.HashMap;
import java.util.Map;

public class StyleHelper {

    private PaintGroup paintGroup;
    private final Map<String, Paint.Cap> strokeCapMap;

    public StyleHelper(){
        strokeCapMap = new HashMap<>(3);
        strokeCapMap.put("Square", Paint.Cap.SQUARE);
        strokeCapMap.put("Round", Paint.Cap.ROUND);
        strokeCapMap.put("Butt", Paint.Cap.BUTT);
    }

    public void init(PaintGroup paintGroup){
        this.paintGroup = paintGroup;
    }


    public void setStokeCap(String type){
        Paint.Cap strokeCap = strokeCapMap.get(type);
        if(strokeCap != null){
            paintGroup.setStrokeCap(strokeCap);
        }
    }

}
