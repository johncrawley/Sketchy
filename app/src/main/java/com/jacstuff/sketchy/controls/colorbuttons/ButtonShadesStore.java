package com.jacstuff.sketchy.controls.colorbuttons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ButtonShadesStore {

    private final Map<Integer, List<Integer>> shadesMap;

    public ButtonShadesStore(){
        shadesMap = new HashMap<>();
    }

    public void addShades(int key, List<Integer> shades){
        shadesMap.put(key, shades);
    }


    public List<Integer> getShadesFor(int key){
        if(!shadesMap.containsKey(key)){
            return new ArrayList<>(0);
        }
        return shadesMap.get(key);
    }

}
