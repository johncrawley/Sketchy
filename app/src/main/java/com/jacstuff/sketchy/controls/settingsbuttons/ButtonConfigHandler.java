package com.jacstuff.sketchy.controls.settingsbuttons;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.settings.PaintViewSingleton;
import com.jacstuff.sketchy.ui.SettingsPopup;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ButtonConfigHandler<T>{

    private Map<Integer, T> buttonActionMap;
    private ButtonsConfigurator<T> buttonsConfigurator;
    private MainActivity activity;
    private ButtonUtils buttonUtils;
    private Set<Integer> buttonIds;
    private Button parentButton;
    private ButtonCategory buttonCategory;
    private LinearLayout linearLayout;
    private SettingsPopup settingsPopup;
    private Map<Drawable, Drawable> drawableCopyMap;


    public ButtonConfigHandler(MainActivity activity, ButtonsConfigurator<T> buttonsConfigurator, ButtonCategory buttonCategory, int layoutId){
        buttonActionMap = new HashMap<>();
        drawableCopyMap = new HashMap<>();
        this.activity = activity;
        this.settingsPopup = activity.getSettingsPopup();
        this.buttonsConfigurator = buttonsConfigurator;
        this.buttonUtils = new ButtonUtils(activity);
        this.buttonCategory = buttonCategory;
        linearLayout = activity.findViewById(layoutId);
        settingsPopup.registerToIgnore(layoutId);

    }


    public void add(int id, T action){
        settingsPopup.registerToIgnore(id);
        buttonActionMap.put(id, action);
    }


    public void add(int id, int drawableId, T action){
        add(id, action);
        linearLayout.addView(buttonUtils.createWrappedButton(id, drawableId));
    }


    public void add(int id, T action, String text){
        add(id, action);
        linearLayout.addView(buttonUtils.createWrappedButton(id, R.drawable.blank_button, text));
    }


    public Collection<T> getEntries(){
        return buttonActionMap.values();
    }


    void setParentButton(int id){
        this.parentButton = activity.findViewById(id);
        settingsPopup.registerParentButton(id);
    }


    Set<Integer> getButtonIds(){
        return buttonIds;
    }


    void setupClickHandler(){
        View.OnClickListener clickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                handleClick(view);
            }
        };
        setClickListenerForButtons(clickListener);
    }


    private void handleClick(View view){
        int viewId = view.getId();
        buttonUtils.switchSelection(view.getId(), buttonIds);

        buttonsConfigurator.handleClick(viewId, buttonActionMap.get(viewId));
        PaintViewSingleton.getInstance().saveSetting(viewId, buttonCategory);
        assignBackgroundAndTextToParentButtonFrom(view);
    }


    private void assignBackgroundAndTextToParentButtonFrom(View view){
        Button clickedButton = (Button)view;
        if(parentButton == null){
            return;
        }
        parentButton.setBackground(getCopyOfDrawableFrom(clickedButton));

        parentButton.setText(clickedButton.getText());
    }


    private Drawable getCopyOfDrawableFrom(Button button){
        Drawable drawable = button.getBackground();
        if(!drawableCopyMap.containsKey(drawable)){
            Drawable.ConstantState constantState = drawable.getConstantState();
            if(constantState != null){
                drawableCopyMap.put(drawable, constantState.newDrawable().mutate());
            }
            else{
                drawableCopyMap.put(drawable, drawable);
            }
        }
       return drawableCopyMap.get(drawable);
    }


    private void setClickListenerForButtons(View.OnClickListener clickListener){
        buttonIds = buttonActionMap.keySet();
        for(int buttonId : buttonIds){
            View view =  activity.findViewById(buttonId);
            if(view != null){
                view.setOnClickListener(clickListener);
            }
        }
    }


    void setDefaultSelection(int id){
        buttonUtils.switchSelection(id, buttonIds);
        buttonsConfigurator.handleClick(id, buttonActionMap.get(id));
    }

}
