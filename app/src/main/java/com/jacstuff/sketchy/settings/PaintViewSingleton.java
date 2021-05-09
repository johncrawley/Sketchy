package com.jacstuff.sketchy.settings;

import android.graphics.Bitmap;

import com.jacstuff.sketchy.controls.ButtonCategory;

import java.util.HashMap;
import java.util.Map;

public class PaintViewSingleton  {


    private static PaintViewSingleton instance;
    private Bitmap bitmap;
    private String mostRecentColor, mostRecentShade;
    private int mostRecentSettingButtonId, mostRecentCategoryButtonId;
    private boolean wasMostRecentClickAShade = false;
    private Map<ButtonCategory, Integer> mostRecentlyClickedButtons;


    private PaintViewSingleton(){
        mostRecentlyClickedButtons = new HashMap<>();
    }


   public static PaintViewSingleton getInstance(){
        if(instance == null){
            instance = new PaintViewSingleton();
        }
        return instance;
    }


    public void saveSetting(int viewId, ButtonCategory buttonCategory){
        mostRecentlyClickedButtons.put(buttonCategory, viewId);
     }


    public void saveSelectedCategoryButton(int viewId){
        mostRecentCategoryButtonId = viewId;
    }


    public int getMostRecentSettingButtonId(ButtonCategory buttonCategory){
        Integer id = mostRecentlyClickedButtons.get(buttonCategory);
        if(id == null){
            id = -1;
        }
        return id;
    }


    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }


    public void setMostRecentColor(String key){
        this.mostRecentColor = key;
    }

    public void setMostRecentShade(String key){
        this.mostRecentShade = key;
    }


    public void setWasMostRecentClickAShade(boolean b){
        this.wasMostRecentClickAShade = b;
    }

    public boolean wasMostRecentClickAShade(){
        return this.wasMostRecentClickAShade;
    }


    public String getMostRecentColor(){
        return this.mostRecentColor;
    }


    public String getMostRecentShade(){
        return this.mostRecentShade;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }


}
