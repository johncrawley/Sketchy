package com.jacstuff.sketchy.controls.childpanel;

import android.app.Activity;
import android.view.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChildSettingsPanelManager {


    private final Activity activity;
    private final Set<Integer> settingsPanelIds;
    private final Map<Integer, Integer> childPanelMap;


    public ChildSettingsPanelManager(Activity activity){
        this.activity = activity;
        settingsPanelIds = new HashSet<>();
        childPanelMap = new HashMap<>();
    }


    public void add(int buttonId, int childPanelId){
        childPanelMap.put(buttonId, childPanelId);
        settingsPanelIds.add(childPanelId);
    }


    public void select(int buttonId){
        Integer childPanelId = childPanelMap.get(buttonId);
        if(childPanelId == null){
            childPanelId = -1;
        }
        for(int settingsPanelId : settingsPanelIds){
            int visibility = settingsPanelId == childPanelId ? View.VISIBLE : View.GONE;
            activity.findViewById(settingsPanelId).setVisibility(visibility);
        }
    }


}
