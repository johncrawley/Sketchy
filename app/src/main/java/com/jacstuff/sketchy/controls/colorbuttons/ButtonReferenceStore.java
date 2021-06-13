package com.jacstuff.sketchy.controls.colorbuttons;

import android.widget.Button;

import com.jacstuff.sketchy.R;

import java.util.HashMap;
import java.util.Map;

public class ButtonReferenceStore {

    private Map<String, Integer> buttonReferences;
    private int keyTag = R.string.tag_button_key;

    public ButtonReferenceStore(){
        buttonReferences = new HashMap<>();
    }

    public void add(Button button){
        buttonReferences.put((String)button.getTag(keyTag), button.getId());
        System.out.println("Adding tag: " + button.getTag(keyTag) + " for button ID: "+ button.getId());
    }

    public int getIdFor(String tag){
        Integer result = buttonReferences.get(tag);
        log("getIdFor(" + tag + ") : result: " + result);
        return result == null ? -1 : result;
    }

    private void log(String msg){
        System.out.println("ButtonReferenceStore: " + msg);
    }

    public String getKeyFrom(Button button){
        if(button == null){
            return "null button";
        }
        return (String)button.getTag(keyTag);
    }
}
