package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;
import android.widget.ImageButton;

import com.jacstuff.sketchy.MainActivity;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ButtonConfigHandler<T>{

    private Map<Integer, T> buttonActionMap;
    private Map<Integer, Integer> buttonBackgroundResourceMap;
    private ButtonsConfigurator<T> buttonsConfigurator;
    private MainActivity activity;
    private ButtonUtils buttonUtils;
    private Set<Integer> buttonIds;
    private ImageButton imageButton;

    public ButtonConfigHandler(MainActivity activity, ButtonsConfigurator<T> buttonsConfigurator){
        buttonActionMap = new HashMap<>();
        buttonBackgroundResourceMap = new HashMap<>();
        this.activity = activity;
        this.buttonsConfigurator = buttonsConfigurator;
        this.buttonUtils = new ButtonUtils(activity);
    }


    public void put(int id, T action){
        buttonActionMap.put(id, action);
    }

    public void put(int id, int drawableId, T action){
        buttonActionMap.put(id, action);
        buttonBackgroundResourceMap.put(id, drawableId);
    }

    public Collection<T> getEntries(){
        return buttonActionMap.values();
    }


    void setParentButton(int id){
        this.imageButton = activity.findViewById(id);
    }


    private void setBackgroundOfParentButton(int selectedButtonId){
        if(imageButton == null){
            return;
        }
        if(buttonBackgroundResourceMap.containsKey(selectedButtonId)){
            Integer resId = buttonBackgroundResourceMap.get(selectedButtonId);
            if( resId == null){
                return;
            }
            imageButton.setImageResource(resId);

        }
    }

    private void log(String msg){
        System.out.println("ButtonClickHandler: " +  msg);
    }


    void setupClickHandler(){
        View.OnClickListener shapeListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int viewId = view.getId();
                buttonUtils.switchSelection(view.getId(), buttonIds);
                buttonsConfigurator.handleClick(viewId,buttonActionMap.get(viewId));
                buttonsConfigurator.saveSelection(viewId);
                setBackgroundOfParentButton(viewId);
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
        buttonsConfigurator.handleClick(id, buttonActionMap.get(id));
    }

}
