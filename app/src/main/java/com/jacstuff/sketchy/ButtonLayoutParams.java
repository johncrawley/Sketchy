package com.jacstuff.sketchy;

import android.widget.LinearLayout;

class ButtonLayoutParams {

    private int buttonWidth, buttonHeight;
    private LinearLayout.LayoutParams unselectedButtonLayoutParams, selectedButtonLayoutParams;

    ButtonLayoutParams(int buttonWidth, int buttonHeight, int selectedButtonBorderWidth){
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;

        selectedButtonLayoutParams = new LinearLayout.LayoutParams(buttonWidth - selectedButtonBorderWidth, buttonHeight - selectedButtonBorderWidth);
        unselectedButtonLayoutParams = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
    }


    int getButtonWidth(){
        return buttonWidth;
    }

    int getButtonHeight(){
        return buttonHeight;
    }

    LinearLayout.LayoutParams getSelected(){
        return selectedButtonLayoutParams;
    }


    LinearLayout.LayoutParams getUnselected(){
        return unselectedButtonLayoutParams;
    }


}
