package com.jacstuff.sketchy.controls;

import android.widget.LinearLayout;

public class ButtonLayoutParams {

    private int buttonWidth, buttonHeight;
    private LinearLayout.LayoutParams unselectedButtonLayoutParams, selectedButtonLayoutParams;

    public ButtonLayoutParams(int buttonWidth, int buttonHeight, int selectedButtonBorderWidth){
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;

        selectedButtonLayoutParams = new LinearLayout.LayoutParams(buttonWidth - selectedButtonBorderWidth, buttonHeight - selectedButtonBorderWidth);
        unselectedButtonLayoutParams = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
    }


    public int getButtonWidth(){
        return buttonWidth;
    }


    public int getButtonHeight(){
        return buttonHeight;
    }


    public LinearLayout.LayoutParams getSelected(){
        return selectedButtonLayoutParams;
    }


    public LinearLayout.LayoutParams getUnselected(){
        return unselectedButtonLayoutParams;
    }


}
