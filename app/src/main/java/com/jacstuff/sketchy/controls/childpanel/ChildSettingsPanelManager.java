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
    private final Map<Integer, Set<Integer>> childPanelMap;
    private final Map<Integer, Integer> excludeMap;
    private int defaultLayoutId;


    public ChildSettingsPanelManager(Activity activity){
        this.activity = activity;
        settingsPanelIds = new HashSet<>();
        childPanelMap = new HashMap<>();
        excludeMap = new HashMap<>();
    }


    public void add(int buttonId, int childPanelId){
        if (!childPanelMap.containsKey(buttonId)) {
            childPanelMap.put(buttonId, new HashSet<>());
        }
        Set<Integer> childPanelIds = childPanelMap.get(buttonId);
        assert childPanelIds != null;
        childPanelIds.add(childPanelId);

        settingsPanelIds.add(childPanelId);
    }

    public void addExclusion(int buttonId, int layoutId){
        excludeMap.put(buttonId, layoutId);
    }

    public void setDefaultLayoutId(int id){
        this.defaultLayoutId = id;
    }

    private int offButtonId;
    private View defaultLayout;

    public void setOffButtonAndDefaultLayout(int offButtonId, int defaultLayoutId){
        this.offButtonId = offButtonId;
        this.defaultLayoutId = defaultLayoutId;
        this.defaultLayout = activity.findViewById(defaultLayoutId);
    }


    private void showOrHideDefaultLayout(int buttonId){
        if(defaultLayout == null){
            return;
        }
        if(buttonId == offButtonId){
            defaultLayout.setVisibility(View.GONE);
            log("entered select() buttonId is the same as OffButton");

        }
        else{
            defaultLayout.setVisibility(View.VISIBLE);
            log("entered select() buttonId is NOT the same as OffButton");
        }
    }


    public void select(int buttonId){
        showOrHideDefaultLayout(buttonId);

        Set<Integer> childPanelIds = childPanelMap.get(buttonId);
        if(childPanelIds == null){
            return;
        }
        for(int settingsPanelId : settingsPanelIds){
            int visibility = childPanelIds.contains(settingsPanelId) ? View.VISIBLE : View.GONE;
            activity.findViewById(settingsPanelId).setVisibility(visibility);
        }

        /*
        activity.findViewById(defaultLayoutId).setVisibility(View.VISIBLE);
        if(excludeMap.containsKey(buttonId)){
            Integer excludedLayoutId = excludeMap.get(buttonId);
            if(excludedLayoutId != null) {
                activity.findViewById(excludedLayoutId).setVisibility(View.GONE);
            }
        }
        */

    }

    private void log(String msg){
        System.out.println("^^^ ChildSettingsPanelManager: "+  msg);
    }


}
