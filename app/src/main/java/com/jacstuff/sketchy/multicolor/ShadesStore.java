package com.jacstuff.sketchy.multicolor;

import android.graphics.Color;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ShadesStore {

    private Map<Integer, List<Integer>> shadesMap;


    public List<Integer> getShadesFor(int color){
        if(!shadesMap.containsKey(color)){
            return Collections.singletonList(Color.BLACK);
        }
        return shadesMap.get(color);
    }


    public void setShades(Map<Integer, List<Integer>> shadesMap){
        this.shadesMap = shadesMap;
    }
}
