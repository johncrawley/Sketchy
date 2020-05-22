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
    private final String MULTI_SHADE_KEY = "multi shade key";
    private Context context;
    private Map<String, Color> colors;
    private Map<String, LinearLayout> shadeLayoutsMap = new HashMap<>();
    private View.OnClickListener onClickListener;
    private ButtonLayoutParams buttonLayoutParams;


    ColorButtonLayoutPopulator(MainActivity mainActivity, ButtonLayoutParams buttonLayoutParams, Map<String, Color> colors){
        this.context = mainActivity.getApplicationContext();
        this.onClickListener = mainActivity;
        colorShadeCreator = new ColorShadeCreator();
        this.buttonLayoutParams = buttonLayoutParams;
        this.colors = colors;
        setupColorAndShadeButtons();
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


    private void addColorButton(Color currentColor, String key){
        if(currentColor == null){
            return;
        }
        Button button = createColorButton(currentColor);
        button.setTag(key);
        button.setTag(R.string.tag_button_type, R.string.button_type_color);
        putButtonInLayoutAndAddToList(button);
    }


    private void addMultiColorButton(){

        Button button = createMultiColorButton();
        putButtonInLayoutAndAddToList(button);
    }


    private Button createMultiColorButton(){
        Button button = createGenericColorButton();
        button.setBackgroundResource(R.drawable.multi_color_button);
        button.setTag(R.string.tag_button_type, R.string.button_type_multi_color);
        button.setTag(MULTI_SHADE_KEY);
        return button;
    }


    private Button createColorButton(Color color){
        Button button = createGenericColorButton();
        button.setTag(R.string.tag_button_color, color);
        button.setBackgroundColor(color.toArgb());
        return button;
    }


    private Button createGenericColorButton(){
        Button button = new Button(context);
        button.setLayoutParams(buttonLayoutParams.getUnselected());
        button.setOnClickListener(onClickListener);
        return button;
    }


    private void putButtonInLayoutAndAddToList(Button button){
        LinearLayout buttonLayout = putInLayout(button);
        colorButtonLayouts.add(buttonLayout);
    }



    private LinearLayout putInLayout(Button button){
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
        LinearLayout shadeLayout = createMultiShadeLayoutWithButtonsFrom(colorList);
        shadeLayoutsMap.put(MULTI_SHADE_KEY, shadeLayout);
    }


    private void addShadesToLayoutMap(String key, List<Color> shades){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(shades);
        shadeLayoutsMap.put(key, shadeLayout);
    }

    private void addMultiColorShades(Color color, List<Color> shades){
        multiColorShades.put(color, shades);
    }


    private LinearLayout createLayoutWithButtonsFrom(List<Color> shades){
        LinearLayout shadeLayout = new LinearLayout(context);
        for(int i = 0; i< shades.size(); i++){
            int labelNumber = i + 1;
            LinearLayout buttonLayout = createShadeButton(shades.get(i), labelNumber);
            shadeLayout.addView(buttonLayout);
        }
        return shadeLayout;
    }


    private LinearLayout createMultiShadeLayoutWithButtonsFrom(List<Color> colors){
        LinearLayout layout = new LinearLayout(context);
        for(Color color : colors){
            LinearLayout buttonLayout = createMultiShadeButton(color);
            layout.addView(buttonLayout);
        }
        return layout;
    }


    private LinearLayout createShadeButton(Color color, int number){
        Button button = createColorButton(color);
        button.setTag(R.string.tag_button_type, R.string.button_type_shade);
        return putInLayout(button);
    }


    private LinearLayout createMultiShadeButton(Color color){
        Button button = createColorButton(color );
        button.setTag(R.string.tag_button_type, R.string.button_type_multi_shade);
        return putInLayout(button);
    }



}
