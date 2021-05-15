package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;
import android.widget.ImageButton;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.settings.PaintViewSingleton;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ButtonConfigHandler<T>{

    private Map<Integer, T> buttonActionMap;
    private Map<Integer, Integer> buttonBackgroundResourceMap;
    private Map<Integer, Integer> toastMessageIdMap;
    private ButtonsConfigurator<T> buttonsConfigurator;
    private MainActivity activity;
    private ButtonUtils buttonUtils;
    private Set<Integer> buttonIds;
    private ImageButton imageButton;
    private ButtonCategory buttonCategory;


    public ButtonConfigHandler(MainActivity activity, ButtonsConfigurator<T> buttonsConfigurator, ButtonCategory buttonCategory){
        buttonActionMap = new HashMap<>();
        buttonBackgroundResourceMap = new HashMap<>();
        toastMessageIdMap = new HashMap<>();
        this.activity = activity;
        this.buttonsConfigurator = buttonsConfigurator;
        this.buttonUtils = new ButtonUtils(activity);
        this.buttonCategory = buttonCategory;
    }


    public void put(int id, T action){
        buttonActionMap.put(id, action);
    }


    public void put(int id, int drawableId, T action){
        buttonActionMap.put(id, action);
       // buttonUtils.setStandardWidthOn(id);
        buttonBackgroundResourceMap.put(id, drawableId);
    }


    public void put(int id, int drawableID, T action, int toastMessageId){
        put(id, drawableID, action);
        toastMessageIdMap.put(id, toastMessageId);
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
        if(toastMessageIdMap.containsKey(selectedButtonId)){
            Integer id = toastMessageIdMap.get(selectedButtonId);
            if(id != null) {
                activity.toast(id);
            }
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
                buttonsConfigurator.handleClick(viewId, buttonActionMap.get(viewId));
                PaintViewSingleton.getInstance().saveSetting(viewId, buttonCategory);
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
