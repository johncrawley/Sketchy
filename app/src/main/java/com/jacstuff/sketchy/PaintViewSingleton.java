package com.jacstuff.sketchy;

class PaintViewSingleton  {

    private PaintView paintView;
    private static PaintViewSingleton instance;

    static PaintViewSingleton getInstance(){
        if(instance == null){
            instance = new PaintViewSingleton();
        }
        return instance;
    }

    void setPaintView(PaintView paintView){
        this.paintView = paintView;
    }

    PaintView getPaintView(){
        return this.paintView;
    }

    private PaintViewSingleton(){

    }

}
