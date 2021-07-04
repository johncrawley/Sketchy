package com.jacstuff.sketchy.controls;

import android.widget.LinearLayout;

public class ButtonLayoutParams {

    private final int buttonWidth, buttonHeight;
    private final LinearLayout.LayoutParams unselectedButtonLayoutParams, selectedButtonLayoutParams;

    public ButtonLayoutParams(int buttonWidth, int buttonHeight, int selectedButtonBorderWidth){
        this(buttonWidth, buttonHeight, selectedButtonBorderWidth, 0);
    }

    public ButtonLayoutParams(int buttonWidth, int buttonHeight, int selectedButtonBorderWidth, int unselectedButtonBorderWidth){
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;

        selectedButtonLayoutParams = new LinearLayout.LayoutParams(buttonWidth - selectedButtonBorderWidth, buttonHeight - selectedButtonBorderWidth);
        unselectedButtonLayoutParams = new LinearLayout.LayoutParams(buttonWidth - unselectedButtonBorderWidth, buttonHeight - unselectedButtonBorderWidth);
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
