package com.jacstuff.sketchy.paintview;

import android.graphics.Bitmap;

import com.jacstuff.sketchy.controls.ButtonCategory;

public class PaintViewSingleton  {


    private static PaintViewSingleton instance;
    private Bitmap bitmap;
    private String mostRecentColor, mostRecentShade;
    private int mostRecentBrushStyleId, mostRecentBrushShapeId;
    private boolean wasMostRecentClickAShade = false;


    private PaintViewSingleton(){

    }


   public static PaintViewSingleton getInstance(){
        if(instance == null){
            instance = new PaintViewSingleton();
        }
        return instance;
    }


    public void saveSetting(int viewId, ButtonCategory buttonCategory){
        if(buttonCategory == ButtonCategory.SHAPE_SELECTION){
            mostRecentBrushShapeId = viewId;
            return;
        }
        mostRecentBrushStyleId = viewId;
    }

    public void saveShapeSelectionSetting(int viewId){
        mostRecentBrushShapeId = viewId;
    }

    public void saveStyleSelectionSetting(int viewId){
        mostRecentBrushStyleId = viewId;
    }

    public int getMostRecentBrushStyleId(){
        return this.mostRecentBrushStyleId;
    }

    public int getMostRecentBrushShapeId(){
        return this.mostRecentBrushShapeId;
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
