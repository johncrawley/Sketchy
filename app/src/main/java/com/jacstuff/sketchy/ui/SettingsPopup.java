package com.jacstuff.sketchy.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.utils.ActivityUtils;

import java.util.HashSet;
import java.util.Set;


public class SettingsPopup {

    private boolean isPoppedUp;
    private int currentParentButtonId;
    private final ViewGroup popupLayout;
    private final Set<Integer> parentIds;
    private final Set<Integer> ignoreIds;
    private final Set<Integer> ignoreIdsForLandscape;
    private final ButtonUtils buttonUtils;
    private final MainActivity activity;


    public SettingsPopup(ViewGroup layout, MainActivity mainActivity){
        this.popupLayout = layout;
        parentIds = new HashSet<>();
        ignoreIds = new HashSet<>();
        ignoreIdsForLandscape = new HashSet<>();
        buttonUtils = new ButtonUtils(mainActivity);
        this.activity = mainActivity;
    }


    public void registerParentButton(int parentButtonId){
       parentIds.add(parentButtonId);
       ignoreIds.remove(parentButtonId);
    }

    // ignored views won't trigger a dismiss when clicked
    // we really just want to ignore views that are in the popup itself
    public void registerToIgnore(int ...ids){
        for(int id : ids){
            ignoreIds.add(id);
        }
    }


    public void registerToIgnoreForLandscape(int ...ids){
        for(int id : ids){
            ignoreIdsForLandscape.add(id);
        }
    }


    public void dismiss(){
        setInvisible();
    }


    public void dismiss(View v){
        int id = v.getId();
        if(ignoreIds.contains(id)){
            return;
        }
        if(ActivityUtils.isInLandscapeOrientation(activity) && ignoreIdsForLandscape.contains(id)){
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
        if(isIdTheCurrentParentOrNotAParent(id)){
            setInvisible();
            return;
        }
        currentParentButtonId = id;
        setVisible();
    }


    boolean isIdTheCurrentParentOrNotAParent(int id){
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
