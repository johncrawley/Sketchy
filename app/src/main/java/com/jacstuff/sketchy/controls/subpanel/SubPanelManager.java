package com.jacstuff.sketchy.controls.subpanel;

import android.app.Activity;
import android.view.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubPanelManager {


    private final Activity activity;
    private final Set<Integer> settingsPanelIds;
    private final Set<View> subPanels;
    private final Map<Integer, Set<Integer>> panelMap;
    private final Set<Integer> buttonIdsWithoutPanels;
    private View defaultLayout;


    public SubPanelManager(Activity activity){
        this.activity = activity;
        settingsPanelIds = new HashSet<>();
        subPanels = new HashSet<>();
        buttonIdsWithoutPanels = new HashSet<>();
        panelMap = new HashMap<>();
    }


    public void add(int buttonId, int subPanelId){
        if (!panelMap.containsKey(buttonId)) {
            panelMap.put(buttonId, new HashSet<>());
        }
        Set<Integer> childPanelIds = panelMap.get(buttonId);
        assert childPanelIds != null;
        childPanelIds.add(subPanelId);

        settingsPanelIds.add(subPanelId);
        View view = activity.findViewById(subPanelId);
        if(view != null){
            subPanels.add(view);
        }
    }


    public void registerButtonWithoutPanel(int buttonId){
        buttonIdsWithoutPanels.add(buttonId);
    }


    public void setOffButtonAndDefaultLayout(int offButtonId, int defaultLayoutId){
        buttonIdsWithoutPanels.add(offButtonId);
        setDefaultLayout(defaultLayoutId);
    }


    public void setDefaultLayout(int defaultLayoutId){
        this.defaultLayout = activity.findViewById(defaultLayoutId);
    }


    public void select(int buttonId){
        showOrHideDefaultLayout(buttonId);
        hideAllSubPanelsIfButtonIsOffButton(buttonId);
        for(int settingsPanelId : settingsPanelIds){
            setVisibilityOfSubPanel(settingsPanelId, buttonId);
        }

    }


    private void setVisibilityOfSubPanel(int settingsPanelId, int buttonId){
        Set<Integer> subPanelIds = panelMap.get(buttonId);
        int visibility = subPanelIds != null && subPanelIds.contains(settingsPanelId) ? View.VISIBLE : View.GONE;
        activity.findViewById(settingsPanelId).setVisibility(visibility);
    }


    private void showOrHideDefaultLayout(int buttonId){
        if(defaultLayout == null){
            return;
        }
        if(buttonIdsWithoutPanels.contains(buttonId)){
            defaultLayout.setVisibility(View.GONE);
        }
        else{
            defaultLayout.setVisibility(View.VISIBLE);
        }
    }


    private void hideAllSubPanelsIfButtonIsOffButton(int buttonId){
        if(!buttonIdsWithoutPanels.contains(buttonId)){
            return;
        }
        for(View view : subPanels){
            view.setVisibility(View.GONE);
        }
    }


    private void log(String msg){
        System.out.println("^^^ ChildSettingsPanelManager: "+  msg);
    }


}
