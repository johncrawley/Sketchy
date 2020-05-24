package com.jacstuff.sketchy;

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

    public int getMostRecentBrushStyleId(){
        return this.mostRecentBrushStyleId;
    }

    public int getMostRecentBrushShapeId(){
        return this.mostRecentBrushShapeId;
    }


    void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }


    void setMostRecentColor(String key){
        this.mostRecentColor = key;
    }

    void setMostRecentShade(String key){
        this.mostRecentShade = key;
    }


    void setWasMostRecentClickAShade(boolean b){
        this.wasMostRecentClickAShade = b;
    }

    boolean wasMostRecentClickAShade(){
        return this.wasMostRecentClickAShade;
    }


    String getMostRecentColor(){
        return this.mostRecentColor;
    }


    String getMostRecentShade(){
        return this.mostRecentShade;
    }

    Bitmap getBitmap(){
        return this.bitmap;
    }


}
