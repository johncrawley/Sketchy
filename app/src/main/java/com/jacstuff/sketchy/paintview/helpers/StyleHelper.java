package com.jacstuff.sketchy.paintview.helpers;

import android.content.Context;
import android.graphics.Paint;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintGroup;

import java.util.HashMap;
import java.util.Map;

public class StyleHelper {

    private PaintGroup paintGroup;
    private final Context context;
    private final Map<String, Paint.Cap> strokeCapMap;
    private final Map<String, Paint.Join> strokeJoinMap;


    public StyleHelper(Context context){
        this.context = context;
        strokeCapMap = new HashMap<>(3);
        put(strokeCapMap, R.string.stroke_cap_spinner_round_item,  Paint.Cap.ROUND);
        put(strokeCapMap, R.string.stroke_cap_spinner_square_item, Paint.Cap.SQUARE);
        put(strokeCapMap, R.string.stroke_cap_spinner_butt_item,   Paint.Cap.BUTT);

        strokeJoinMap = new HashMap<>(3);
        put(strokeJoinMap, R.string.stroke_join_spinner_mitre_item, Paint.Join.MITER);
        put(strokeJoinMap, R.string.stroke_join_spinner_round_item, Paint.Join.ROUND);
        put(strokeJoinMap, R.string.stroke_join_spinner_bevel_item, Paint.Join.BEVEL);
    }


    private <T> void put(Map<String, T> map, int id, T item){
        String str = getStr(id);
        map.put(str, item);
    }


    private String getStr(int resId){
        return context.getResources().getString(resId);
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
