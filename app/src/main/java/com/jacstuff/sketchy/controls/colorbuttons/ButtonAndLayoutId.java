package com.jacstuff.sketchy.controls.colorbuttons;

import android.widget.Button;

class ButtonAndLayoutId{

    private final Button button;
    private final int layoutId;

    public ButtonAndLayoutId(Button button, int layoutId){
        this.button = button;
        this.layoutId = layoutId;
    }


    public Button getButton(){
        return button;
    }


    public int getLayoutId(){
        return layoutId;
    }
}