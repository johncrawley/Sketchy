package com.jacstuff.sketchy.controls;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.colorbuttons.ButtonType;

import java.util.List;
import java.util.Set;

public class ButtonUtils {

    private final MainActivity activity;
    private  final ButtonLayoutParams buttonLayoutParams;

    public ButtonUtils(MainActivity activity){
        this.activity = activity;
        buttonLayoutParams = new ButtonLayoutParams(activity);
    }


    public void switchSelection(int viewId, Set<Integer> buttonIds){
        if(buttonIds == null){
            return;
        }
        for(int buttonId : buttonIds){
            if(viewId == buttonId){
                selectButton(buttonId);
                continue;
            }
            deselectButton(buttonId);
        }
    }


    private void selectButton(int buttonId){
        View view = findViewById(buttonId);
        if(view == null){
            return;
        }
        view.setSelected(true);
        view.setLayoutParams(buttonLayoutParams.getSelected());
    }


    public void deselectButton(int buttonId){
        View view = findViewById(buttonId);
        if(view == null){
            return;
        }
        view.setSelected(false);
        view.setLayoutParams(buttonLayoutParams.getUnselected());
    }


    public void putButtonInLayoutAndAddToList(Button button, ButtonLayoutParams layoutParams, List<LinearLayout> layoutList ){
        LinearLayout buttonLayout = wrapInMarginLayout(layoutParams, button);
        layoutList.add(buttonLayout);
    }


    public LinearLayout createWrappedButton(int id, int backgroundId){
        Button button = createButton(id, backgroundId, buttonLayoutParams);
        return wrapInSpacingLayout(wrapInMarginLayout(buttonLayoutParams, button));
    }


    public LinearLayout createWrappedButton(int id, int backgroundId, String text){
        Button button = createButton(id, backgroundId, buttonLayoutParams, text);
        return wrapInSpacingLayout(wrapInMarginLayout(buttonLayoutParams, button));
    }


    public LinearLayout createWrappedButton(int id, int backgroundId, ButtonLayoutParams customButtonLayoutParams, View.OnClickListener clickListener){
        Button button = createButton(id, backgroundId, customButtonLayoutParams);
        button.setOnClickListener(clickListener);
        return wrapInMarginLayout(customButtonLayoutParams, button);
    }


    public Button createButton(int id, int backgroundId, ButtonLayoutParams layoutParams){
        return createButton(id,backgroundId,layoutParams, "");
    }


    public Button createButton(int id, int backgroundId, ButtonLayoutParams layoutParams, String text){
        Button button = new Button(activity);
        button.setTextColor(Color.DKGRAY);
        button.setId(id);
        button.setBackgroundResource(backgroundId);
        button.setLayoutParams(layoutParams.getUnselected());
        setStandardWidthOn(button);
        button.setText(text);
        return button;
    }


    public LinearLayout wrapInSpacingLayout(View view){
        LinearLayout layout = new LinearLayout(activity);
        layout.setGravity(Gravity.CENTER);
        int padding = 10;
        layout.setPadding(padding, padding, padding, padding);
        layout.addView(view);
        return layout;
    }


    public LinearLayout wrapInMarginLayout(ButtonLayoutParams layoutParams, Button button){
        LinearLayout layout = new LinearLayout(activity);
        layout.setId(View.generateViewId());
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


    public LinearLayout createShadeButton(final int color, ButtonType buttonType){
        String key = createColorKey(color, buttonType);
        Button button = createButton(color, buttonType, key);
        return wrapInMarginLayout(buttonLayoutParams, button);
    }


    public String createColorKey(int color, ButtonType buttonType){
        return buttonType + "_" + color;
    }


    public Button createButton(int color, ButtonType type, String key){
        Button button = createGenericColorButton(type, key);
        button.setTag(R.string.tag_button_color, color);
        button.setBackgroundColor(color);
        return button;
    }


    public Button createGenericColorButton(ButtonType type, String key){
        Button button = new Button(activity);
        button.setLayoutParams(buttonLayoutParams.getUnselected());
        button.setTag(R.string.tag_button_type, type);
        button.setId(View.generateViewId());
        activity.getSettingsPopup().registerToIgnoreForLandscape(button.getId());
        button.setTag(R.string.tag_button_key, key);
        button.setTag(R.string.tag_button_category, ButtonCategory.COLOR_SELECTION);
        setStandardWidthOn(button);
        activity.getButtonReferenceStore().add(button);
        button.setOnClickListener(activity);
        return button;
    }

}
