package com.jacstuff.sketchy;

import android.graphics.Bitmap;
import android.graphics.Color;

class PaintViewSingleton  {


    private static PaintViewSingleton instance;
    private Bitmap bitmap;
    private String mostRecentColor, mostRecentShade;
    private boolean wasMostRecentClickAShade = false;


    private PaintViewSingleton(){

    }


    static PaintViewSingleton getInstance(){
        if(instance == null){
            instance = new PaintViewSingleton();
        }
        return instance;
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
