package com.jacstuff.sketchy.controls;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.colorbuttons.ButtonType;

import java.util.List;
import java.util.Set;

public class ButtonUtils {

    private Activity activity;

    public ButtonUtils(Activity activity){
        this.activity = activity;
    }


   public void switchSelection(int viewId, Set<Integer> buttons, ButtonLayoutParams buttonLayoutParams){
        for(int buttonId : buttons){
            if(viewId == buttonId){
                switchSelectionToButton(buttonId, buttons, buttonLayoutParams);
                return;
            }
        }
    }


    private void switchSelectionToButton(int buttonId, Set<Integer> buttonList, ButtonLayoutParams buttonLayoutParams){
        selectButton(buttonId, buttonLayoutParams);
        deselectOtherButtons(buttonId, buttonList, buttonLayoutParams);
    }


    private void selectButton(int buttonId, ButtonLayoutParams buttonLayoutParams){
        View button = findViewById(buttonId);
        if(button == null){
            return;
        }
        button.setSelected(true);
        button.setLayoutParams(buttonLayoutParams.getSelected());
      //  button.setBackgroundColor(getColor(R.color.selected_button_border));
    }


    private int getColor(int colorId){
        return activity.getResources().getColor(R.color.selected_button_border, null);
    }


    private void deselectOtherButtons(int selectedButtonId, Set<Integer> buttonList, ButtonLayoutParams buttonLayoutParams){
        for(int buttonId : buttonList){
            if(buttonId == selectedButtonId){
                continue;
            }
            deselectButton(buttonId, buttonLayoutParams);
        }
    }


    public void deselectButton(int buttonId, ButtonLayoutParams buttonLayoutParams){
        View button = findViewById(buttonId);
        if(button == null){
            return;
        }
        button.setSelected(false);
        button.setLayoutParams(buttonLayoutParams.getUnselected());
       // button.setBackgroundColor(Color.LTGRAY);
    }


    public void setStandardWidthOn(int id){
        Button button = (Button)findViewById(id);
        setStandardWidthOn(button);
    }


    public void putButtonInLayoutAndAddToList(Button button, ButtonLayoutParams layoutParams, List<LinearLayout> layoutList ){
        LinearLayout buttonLayout = wrapInMarginLayout(layoutParams, button);
        layoutList.add(buttonLayout);
    }


    public LinearLayout createWrappedButton(int id, int backgroundId, ButtonLayoutParams layoutParams){
        Button button = createButton(id, backgroundId, layoutParams);
        return wrapInMarginLayout(layoutParams, button);
    }


    public Button createButton(int id, int backgroundId, ButtonLayoutParams layoutParams){
        Button button = new Button(activity);
        button.setId(id);
        button.setBackgroundResource(backgroundId);
        button.setPadding(10,10,10,10);
        button.setLayoutParams(layoutParams.getUnselected());
        setStandardWidthOn(button);
        return button;
    }


    public LinearLayout wrapInMarginLayout(ButtonLayoutParams layoutParams, Button button){
        LinearLayout layout = new LinearLayout(activity);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.DKGRAY);
        layout.setMinimumWidth(layoutParams.getButtonWidth());
        layout.setMinimumHeight(layoutParams.getButtonHeight());
        layout.addView(button);
        return layout;
    }

    public void setStandardWidthOn(Button button){
        float dps = activity.getResources().getDimension(R.dimen.color_button_width);
        final float scale = activity.getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        button.setWidth(pixels);
    }


    private View findViewById(int id){
        return activity.findViewById(id);
    }
}