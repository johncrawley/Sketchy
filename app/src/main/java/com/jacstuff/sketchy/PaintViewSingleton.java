package com.jacstuff.sketchy;

public class PaintViewSingleton  {

    private PaintView paintView;
    private static PaintViewSingleton instance;

    public static PaintViewSingleton getInstance(){
        if(instance == null){
            instance = new PaintViewSingleton();
        }
        return instance;
    }

    public void setPaintView(PaintView paintView){
        this.paintView = paintView;
    }

    public PaintView getPaintView(){
        return this.paintView;
    }

    private PaintViewSingleton(){

    }

}
