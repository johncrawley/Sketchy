package com.jacstuff.sketchy;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ColorButtonLayoutPopulator {

    private ColorShadeCreator colorShadeCreator;
    private Map<Color, List<Color>> multiColorShades = new HashMap<>();
    private List<LinearLayout> colorButtonLayouts = new ArrayList<>();
    private Context context;
    private Map<String, Color> colors;
    private Map<String, LinearLayout> shadeLayoutsMap = new HashMap<>();
    private View.OnClickListener onClickListener;
    private ButtonLayoutParams buttonLayoutParams;
    private Map<String, Button> buttonMap;

    private final String MULTI_SHADE_KEY = "multi shade key";

    ColorButtonLayoutPopulator(MainActivity mainActivity, ButtonLayoutParams buttonLayoutParams, Map<String, Color> colors){
        this.context = mainActivity.getApplicationContext();
        this.onClickListener = mainActivity;
        colorShadeCreator = new ColorShadeCreator();
        this.buttonLayoutParams = buttonLayoutParams;
        this.colors = colors;
        buttonMap = new HashMap<>();
        setupColorAndShadeButtons();
    }


    Button getButton(String key){
        return buttonMap.get(key);
    }


    void addColorButtonLayoutsTo(LinearLayout layout){
        for(LinearLayout buttonLayout: colorButtonLayouts){
            layout.addView(buttonLayout);
        }
    }


    Map<String, LinearLayout> getShadeLayoutsMap (){
        return this.shadeLayoutsMap;
    }


    Map<Color, List<Color>> getMultiColorShades(){
        return this.multiColorShades;
    }


    private void setupColorAndShadeButtons(){
        for(String key : colors.keySet()){
            createAndAddColorAndShadeButtons(key);
        }
        addMultiColorButton();
        createMultiColorShadeButtons();
    }


    private void createAndAddColorAndShadeButtons(String key){
        Color color = colors.get(key);
        addColorButton(color, key);
        List<Color> shades = colorShadeCreator.generateShadesFrom(color);
        addShadesToLayoutMap(key, shades);
        addMultiColorShades(color, shades);
    }

    private void addMultiColorShades(Color color, List<Color> shades){
        multiColorShades.put(color, shades);
    }

    private void addShadesToLayoutMap(String key, List<Color> shades){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(shades, ButtonType.SHADE);
        shadeLayoutsMap.put(key, shadeLayout);
    }


    private LinearLayout createLayoutWithButtonsFrom(List<Color> shades, ButtonType buttonType){
        LinearLayout shadeLayout = new LinearLayout(context);
        for(Color shade: shades){
            LinearLayout buttonLayout = createShadeButton(shade, buttonType);
            shadeLayout.addView(buttonLayout);
        }
        return shadeLayout;
    }


    private LinearLayout createShadeButton(Color color, ButtonType buttonType){
        String key = getKeyFrom(color);
        Button button = createButton(color, buttonType, key);
        return wrapInMarginLayout(button);
    }


    private String getKeyFrom(Color color){
        return "" + color.red() + "_" + color.green() + "_" + color.blue() + "_a" + color.alpha();
    }


    private void addColorButton(Color currentColor, String key){
        if(currentColor == null){
            return;
        }
        Button button = createButton(currentColor, ButtonType.COLOR, key);
        putButtonInLayoutAndAddToList(button);
    }


    private Button createButton(Color color, ButtonType type, String key){
        Button button = createGenericColorButton(type, key);
        button.setTag(R.string.tag_button_color, color);
        button.setBackgroundColor(color.toArgb());
        return button;
    }


    private Button createGenericColorButton(ButtonType type, String key){
        Button button = new Button(context);
        button.setLayoutParams(buttonLayoutParams.getUnselected());
        button.setTag(R.string.tag_button_type, type);
        button.setTag(R.string.tag_button_key, key);
        button.setTag(R.string.tag_button_category, ButtonCategory.COLOR_SELECTION_BUTTON);
        buttonMap.put(key, button);
        button.setOnClickListener(onClickListener);
        return button;
    }


    private void addMultiColorButton(){
        Button button = createMultiColorButton();
        putButtonInLayoutAndAddToList(button);
    }


    private Button createMultiColorButton(){
        Button button = createGenericColorButton(ButtonType.MULTICOLOR, MULTI_SHADE_KEY);
        button.setBackgroundResource(R.drawable.multi_color_button);
        return button;
    }


    private void putButtonInLayoutAndAddToList(Button button){
        LinearLayout buttonLayout = wrapInMarginLayout(button);
        colorButtonLayouts.add(buttonLayout);
    }


    private LinearLayout wrapInMarginLayout(Button button){
        LinearLayout layout = new LinearLayout(context);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.DKGRAY);
        layout.setMinimumWidth(buttonLayoutParams.getButtonWidth());
        layout.setMinimumHeight(buttonLayoutParams.getButtonHeight());
        layout.addView(button);
        return layout;
    }


    private void createMultiColorShadeButtons(){
        List<Color> colorList = new ArrayList<>(colors.values());
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(colorList, ButtonType.MULTISHADE);
        shadeLayoutsMap.put(MULTI_SHADE_KEY, shadeLayout);
    }

}
