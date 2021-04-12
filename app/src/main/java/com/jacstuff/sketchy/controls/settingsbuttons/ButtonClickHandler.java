package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;
import com.jacstuff.sketchy.MainActivity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ButtonClickHandler<T>{

    private Map<Integer, T> buttonActionMap;
    private ButtonsConfigurator<T> buttonsConfigurator;
    private MainActivity activity;
    private ButtonUtils buttonUtils;
    private Set<Integer> buttonIds;

    public ButtonClickHandler(MainActivity activity, ButtonsConfigurator<T> buttonsConfigurator){
        buttonActionMap = new HashMap<>();
        this.activity = activity;
        this.buttonsConfigurator = buttonsConfigurator;
        this.buttonUtils = new ButtonUtils(activity);;
    }


    public void put(int id, T action){
        buttonActionMap.put(id, action);
    }

    public Collection<T> getEntries(){
        return buttonActionMap.values();
    }


    void setupClickHandler(){
        View.OnClickListener shapeListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int viewId = view.getId();
                buttonUtils.switchSelection(view.getId(), buttonIds);
                buttonsConfigurator.handleClick(viewId,buttonActionMap.get(viewId));
                buttonsConfigurator.saveSelection(viewId);
            }
        };
        buttonIds = buttonActionMap.keySet();

        for(int id: buttonIds){
            View buttonView = activity.findViewById(id);
            buttonView.setOnClickListener(shapeListener);
        }
    }

    void setDefaultSelection(int id){
        buttonUtils.switchSelection(id, buttonIds);
        buttonsConfigurator.handleClick(id,buttonActionMap.get(id));
    }

}
