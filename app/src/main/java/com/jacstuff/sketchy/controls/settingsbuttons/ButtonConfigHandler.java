package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
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
    private Button button;
    private ButtonCategory buttonCategory;
    private LinearLayout linearLayout;
    private ButtonLayoutParams buttonLayoutParams;


    public ButtonConfigHandler(MainActivity activity, ButtonsConfigurator<T> buttonsConfigurator, ButtonCategory buttonCategory, int layoutId){
        buttonActionMap = new HashMap<>();
        buttonBackgroundResourceMap = new HashMap<>();
        toastMessageIdMap = new HashMap<>();
        this.activity = activity;
        this.buttonsConfigurator = buttonsConfigurator;
        this.buttonUtils = new ButtonUtils(activity);
        this.buttonCategory = buttonCategory;

        linearLayout = activity.findViewById(layoutId);
        buttonLayoutParams = activity.getButtonLayoutParams();
    }


    public void put(int id, T action){
        buttonActionMap.put(id, action);
    }


    public void put(int id, int drawableId, T action){
        buttonActionMap.put(id, action);
        linearLayout.addView(buttonUtils.createWrappedButton(id, drawableId, buttonLayoutParams));
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
        this.button = activity.findViewById(id);
    }


    private void setBackgroundOfParentButton(int selectedButtonId){
        if(button == null){
            return;
        }
        if(buttonBackgroundResourceMap.containsKey(selectedButtonId)){
            Integer resId = buttonBackgroundResourceMap.get(selectedButtonId);
            if( resId == null){
                return;
            }
            //button.setImageResource(resId);
            button.setBackgroundResource(resId);
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
        View.OnClickListener clickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int viewId = view.getId();
                buttonUtils.switchSelection(view.getId(), buttonIds, buttonLayoutParams);
                buttonsConfigurator.handleClick(viewId, buttonActionMap.get(viewId));
                PaintViewSingleton.getInstance().saveSetting(viewId, buttonCategory);
                setBackgroundOfParentButton(viewId);
            }
        };
        buttonIds = buttonActionMap.keySet();
        for(int buttonId : buttonIds){
           View view =  activity.findViewById(buttonId);
           if(view != null){
               view.setOnClickListener(clickListener);
           }
        }
    }


    void setDefaultSelection(int id){
        buttonUtils.switchSelection(id, buttonIds, buttonLayoutParams);
        buttonsConfigurator.handleClick(id, buttonActionMap.get(id));
    }

}
