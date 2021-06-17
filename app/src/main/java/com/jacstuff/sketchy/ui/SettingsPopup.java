package com.jacstuff.sketchy.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.ButtonUtils;

import java.util.HashSet;
import java.util.Set;


public class SettingsPopup {

    private boolean isPoppedUp;
    private int currentParentButtonId;
    private final ViewGroup popupLayout;
    private final Set<Integer> parentIds;
    private final Set<Integer> ignoreIds;
    private final ButtonUtils buttonUtils;
    private final MainActivity activity;


    public SettingsPopup(ViewGroup layout, MainActivity mainActivity){
        this.popupLayout = layout;
        parentIds = new HashSet<>();
        ignoreIds = new HashSet<>();
        buttonUtils = new ButtonUtils(mainActivity);
        this.activity = mainActivity;
    }


    public void registerParentButton(int parentButtonId){
       parentIds.add(parentButtonId);
       ignoreIds.remove(parentButtonId);
    }

    // ignored views won't trigger a dismiss when clicked
    // we really just want to ignore views that are in the popup itself
    public void registerToIgnore(int id){
        ignoreIds.add(id);
    }


    public void dismiss(){
        setInvisible();
    }


    public void dismiss(View v){
        if(ignoreIds.contains(v.getId())){
            return;
        }
        setInvisible();
    }


    public boolean isVisible(){
        return isPoppedUp;
    }


    public void click(int id){

        if(ignoreIds.contains(id)){
            return;
        }
        if(isCurrentParentOrNotAParent(id)){
            setInvisible();
            return;
        }
        currentParentButtonId = id;
        setVisible();
    }


    boolean isCurrentParentOrNotAParent(int id){
        return (isPoppedUp && currentParentButtonId == id) || !parentIds.contains(id);
    }


    private void setVisible(){
        isPoppedUp = true;
        popupLayout.setVisibility(View.VISIBLE);
    }


    private void setInvisible(){
        isPoppedUp = false;
        popupLayout.setVisibility(View.INVISIBLE);
        buttonUtils.deselectButton(currentParentButtonId);
        hideKeyboard();
    }


    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(popupLayout.getWindowToken(), 0);
    }

}
