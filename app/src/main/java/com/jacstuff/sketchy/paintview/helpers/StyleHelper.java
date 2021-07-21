package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;

import java.util.HashMap;
import java.util.Map;

public class StyleHelper {

    private PaintGroup paintGroup;
    private final Map<String, Paint.Cap> strokeCapMap;
    private final Map<String, Paint.Join> strokeJoinMap;

    public StyleHelper(){
        strokeCapMap = new HashMap<>(3);
        strokeCapMap.put("Square", Paint.Cap.SQUARE);
        strokeCapMap.put("Round", Paint.Cap.ROUND);
        strokeCapMap.put("Butt", Paint.Cap.BUTT);

        strokeJoinMap = new HashMap<>(3);
        strokeJoinMap.put("Bevel", Paint.Join.BEVEL);
        strokeJoinMap.put("Mitre", Paint.Join.MITER);
        strokeJoinMap.put("Round", Paint.Join.ROUND);
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


    public void setStrokeJoin(String type){
        Paint.Join strokeJoin = strokeJoinMap.get(type);
        if(strokeJoin != null){
            paintGroup.setStrokeJoin(strokeJoin);
        }
    }

}
