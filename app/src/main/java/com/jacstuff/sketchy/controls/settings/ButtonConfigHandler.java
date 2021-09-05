package com.jacstuff.sketchy.controls.settings;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.viewmodel.ViewModelHelper;
import com.jacstuff.sketchy.ui.SettingsPopup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ButtonConfigHandler<T>{

    private final Map<Integer, T> buttonActionMap;
    private final ButtonsConfigurator<T> buttonsConfigurator;
    private final MainActivity activity;
    private final ButtonUtils buttonUtils;
    private Set<Integer> buttonIds;
    private Button parentButton;
    private final ButtonCategory buttonCategory;
    private LinearLayout linearLayout;
    private final SettingsPopup settingsPopup;
    private final Map<Drawable, Drawable> drawableCopyMap;
    private int defaultSelectionId;
    private final ViewModelHelper viewModelHelper;


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
        viewModelHelper = activity.getViewModelHelper();
    }


    public void add(int id, T action){
        settingsPopup.registerToIgnore(id);
        buttonActionMap.put(id, action);
    }


    public void add(int id, int drawableId, T action){
        add(id, action);
        linearLayout.addView(buttonUtils.createWrappedButton(id, drawableId));
    }


    public void add(int id, String text,  T action ){
        add(id, action);
        linearLayout.addView(buttonUtils.createWrappedButton(id, R.drawable.blank_button, text));
    }


    public void add(int id, int drawableId,  T action, ButtonLayoutParams buttonLayoutParams ){
        add(id, action);
        linearLayout.addView(buttonUtils.createWrappedButton(id, drawableId, buttonLayoutParams));
    }


    public void setParentLayout(int layoutId){
        this.linearLayout = activity.findViewById(layoutId);
    }


    public Set<T> getEntries(){
        return new HashSet<>(buttonActionMap.values());
    }


    public void setParentButton(int id){
        this.parentButton = activity.findViewById(id);
        settingsPopup.registerParentButton(id);
    }


    Set<Integer> getButtonIds(){
        return buttonIds;
    }


    public void setupClickHandler(){
        View.OnClickListener clickListener = this::handleClick;
        buttonIds = buttonActionMap.keySet();
        setClickListenerForButtons(clickListener);
    }


    private void handleClick(View view){
        int viewId = view.getId();
        buttonUtils.switchSelection(view.getId(), buttonIds);
        buttonsConfigurator.handleClick(viewId, buttonActionMap.get(viewId));
        viewModelHelper.saveRecentClick(buttonCategory, viewId);
        assignBackgroundAndTextToParentButtonFrom(view);
    }


    private void assignBackgroundAndTextToParentButtonFrom(View view){
        if(parentButton == null){
            return;
        }
        Button clickedButton = (Button)view;
        parentButton.setBackground(getCopyOfDrawableFrom(clickedButton));
        parentButton.setText(clickedButton.getText());
    }


    private Drawable getCopyOfDrawableFrom(Button button){
        Drawable drawable = button.getBackground();
        if(!drawableCopyMap.containsKey(drawable)){
            Drawable.ConstantState constantState = drawable.getConstantState();
            Drawable drawableToSave = constantState != null ? constantState.newDrawable().mutate() : drawable;
            drawableCopyMap.put(drawable, drawableToSave);
        }
       return drawableCopyMap.get(drawable);
    }


    private void setClickListenerForButtons(View.OnClickListener clickListener){
        for(int buttonId : buttonIds){
           setClickListenerFor(buttonId, clickListener);
        }
    }


    private void setClickListenerFor(int buttonId, View.OnClickListener clickListener){
        View view =  activity.findViewById(buttonId);
        if(view != null){
            view.setOnClickListener(clickListener);
        }
    }


    public void setDefaultSelection(int id){
        defaultSelectionId = id;
    }


    void selectDefaultSelection(){
        buttonUtils.switchSelection(defaultSelectionId, buttonIds);
        buttonsConfigurator.handleDefaultClick(defaultSelectionId, buttonActionMap.get(defaultSelectionId));
    }

}
