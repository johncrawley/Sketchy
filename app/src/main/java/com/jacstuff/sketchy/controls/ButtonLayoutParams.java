package com.jacstuff.sketchy.controls;

import android.content.Context;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.R;

public class ButtonLayoutParams {

    private final int buttonWidth, buttonHeight;
    private final LinearLayout.LayoutParams unselectedButtonLayoutParams, selectedButtonLayoutParams;
    private final Context context;


    public ButtonLayoutParams(Context context){
        this(context, R.integer.button_default_selected_border_width);
    }


    public ButtonLayoutParams(Context context, int selectedButtonBorderWidthId){
        this.context = context;
        this.buttonWidth = getInt(R.integer.button_width);
        this.buttonHeight = getInt(R.integer.button_height);
        int unselectedButtonBorderWidth = getInt(R.integer.button_default_unselected_border_width);
        int selectedButtonBorderWidth = getInt(selectedButtonBorderWidthId);

        selectedButtonLayoutParams = new LinearLayout.LayoutParams(buttonWidth - selectedButtonBorderWidth, buttonHeight - selectedButtonBorderWidth);
        unselectedButtonLayoutParams = new LinearLayout.LayoutParams(buttonWidth - unselectedButtonBorderWidth, buttonHeight - unselectedButtonBorderWidth);
    }


    private int getInt(int resId){
        return context.getResources().getInteger(resId);
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
