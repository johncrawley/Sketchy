package com.jacstuff.sketchy.controls.colorbuttons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShadeStore {

    private final Map<Integer, List<Integer>> shadesMap;

    public ShadeStore(){
        shadesMap = new HashMap<>();
    }

    public void addShades(int key, List<Integer> shades){
        shadesMap.put(key, shades);
    }


    public List<Integer> getShadesFor(int key, ColorShadeCreator colorShadeCreator){
        if(!shadesMap.containsKey(key)){
           addShades(key, colorShadeCreator.generateShadesFrom(key));
        }
        return getShadesFor(key);
    }


    public List<Integer> getShadesFor(int key){
        if(!shadesMap.containsKey(key)){
            return new ArrayList<>(0);
        }
        return shadesMap.get(key);
    }

}
