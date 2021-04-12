package com.jacstuff.sketchy.controls.settingsbuttons;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ImageButton;
import com.jacstuff.sketchy.R;
import java.util.Set;

public class ButtonUtils {

    private Activity activity;

    public ButtonUtils(Activity activity){
        this.activity = activity;
    }


    void switchSelection(int viewId, Set<Integer> buttons){
        for(int buttonId : buttons){
            if(viewId == buttonId){
                switchSelectionToButton(buttonId, buttons);
                return;
            }
        }
    }


    private void switchSelectionToButton(int buttonId, Set<Integer> buttonList){
        selectButton(buttonId);
        deselectOtherButtons(buttonId, buttonList);
    }


    private void selectButton(int buttonId){
        ImageButton button = findViewById(buttonId);
        button.setSelected(true);
        button.setBackgroundColor(getColor(R.color.selected_button_border));
    }


    private int getColor(int colorId){
        return activity.getResources().getColor(R.color.selected_button_border, null);
    }


    private void deselectOtherButtons(int selectedButtonId, Set<Integer> buttonList){
        for(int buttonId : buttonList){
            if(buttonId == selectedButtonId){
                continue;
            }
            deselectButton(buttonId);
        }
    }


    private void deselectButton(int buttonId){
        ImageButton button = findViewById(buttonId);
        button.setSelected(false);
        button.setBackgroundColor(Color.LTGRAY);
    }


    private ImageButton findViewById(int id){
        return (ImageButton)activity.findViewById(id);
    }
}
