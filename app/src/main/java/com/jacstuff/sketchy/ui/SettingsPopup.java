package com.jacstuff.sketchy.ui;

import android.view.View;
import android.view.ViewGroup;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.ButtonUtils;

import java.util.HashSet;
import java.util.Set;


public class SettingsPopup {

    private boolean isPoppedUp;
    private int currentParentButtonId;
    private ViewGroup popupLayout;
    private Set<Integer> parentIds;
    private Set<Integer> ignoreIds;
    private ButtonUtils buttonUtils;
    private MainActivity activity;


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


    public void registerToIgnore(int id){
        ignoreIds.add(id);
    }


    public void dismiss(){
        setInvisible();
    }


    public boolean isVisible(){
        return isPoppedUp;
    }


    public void click(int id){
        printParentButtonIdsAndCurrentId(id);
        if(ignoreIds.contains(id)){
            System.out.println("id ignored!");
            return;
        }
        if(!parentIds.contains(id)){
            setInvisible();
            return;
        }
        if(isPoppedUp && currentParentButtonId == id){
            setInvisible();
            return;
        }
        currentParentButtonId = id;
        setVisible();
    }

    private void printParentButtonIdsAndCurrentId(int currentId){
        for(int id : parentIds){
            System.out.println("SettingsPopup: currentParentId: " + id);
        }
        for(int id : ignoreIds){
            System.out.println("SettingsPopup: currentIgnoreId: " + id);
        }
        System.out.println("SettingsPopup: current id: " + currentId);


    }



    private void setVisible(){
        isPoppedUp = true;
        popupLayout.setVisibility(View.VISIBLE);
    }


    private void setInvisible(){
        isPoppedUp = false;
        popupLayout.setVisibility(View.INVISIBLE);
        buttonUtils.deselectButton(currentParentButtonId, activity.getSettingsButtonsLayoutParams());
    }

}
