package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.ButtonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ColorButtonLayoutPopulator {

    private ColorShadeCreator colorShadeCreator;
    private final Map<Integer, List<Integer>> multiColorShades = new HashMap<>();
    private final List<LinearLayout> colorButtonLayouts = new ArrayList<>();
    private final Context context;
    private final List<Integer> colors;
    private final Map<String, LinearLayout> shadeLayoutsMap = new HashMap<>();
    private final String MULTI_SHADE_KEY = "multi shade key";
    private final String RANDOM_SHADE_KEY = "random shade key";
    private final ButtonLayoutParams buttonLayoutParams;
    private final String defaultColor;
    private final ButtonUtils buttonUtils;
    private final ButtonReferenceStore buttonReferenceStore;
    private int layoutOrientation;
    private final MainActivity activity;
    private final RandomShadeIconDrawer randomShadeIconDrawer;


    public ColorButtonLayoutPopulator(MainActivity mainActivity, ButtonLayoutParams buttonLayoutParams, final List<Integer> colors){
        this.context = mainActivity.getApplicationContext();
        this.activity = mainActivity;
        defaultColor = mainActivity.getString(R.string.default_color);
        randomShadeIconDrawer = new RandomShadeIconDrawer(activity);
        setupLayoutOrientation();
        this.buttonReferenceStore = mainActivity.getButtonReferenceStore();
        setupColorShadeCreator();
        this.buttonLayoutParams = buttonLayoutParams;
        this.colors = colors;
        buttonUtils = new ButtonUtils(mainActivity);
        setupColorAndShadeButtons();
    }


    public void addColorButtonLayoutsTo(LinearLayout layout){
        for(LinearLayout buttonLayout: colorButtonLayouts){
            layout.addView(buttonLayout);
        }
    }


    public Map<String, LinearLayout> getShadeLayoutsMap (){
        return this.shadeLayoutsMap;
    }


    public Map<Integer, List<Integer>> getMultiColorShades(){
        return this.multiColorShades;
    }


    private void setupColorShadeCreator(){
        int numberOfShades = context.getResources().getInteger(R.integer.number_of_shades);
        int shadeIncrement = context.getResources().getInteger(R.integer.shade_increment);
        colorShadeCreator = new ColorShadeCreator(numberOfShades, shadeIncrement);
    }


    private void setupLayoutOrientation(){
        layoutOrientation = LinearLayout.HORIZONTAL;
    }


    private void setupColorAndShadeButtons(){
        for(int color : colors){
            addColorAndShadeButtons(color);
        }
        addMultiColorButton();
        addRandomButton();
        addMultiColorShadeButtons();
        addRandomShadeButtons();
    }


    private void addColorAndShadeButtons(int color){
        addColorButton(color, createColorKey(color, ButtonType.COLOR));
        List<Integer> shades = colorShadeCreator.generateShadesFrom(color);
        addShadesToLayoutMap(color, shades);
        addMultiColorShades(color, shades);
    }


    private void addMultiColorButton(){
        Button button = createGenericColorButton(ButtonType.MULTICOLOR, MULTI_SHADE_KEY);
        button.setBackgroundResource(R.drawable.multi_color_button);
        buttonUtils.putButtonInLayoutAndAddToList(button, buttonLayoutParams, colorButtonLayouts);
    }


    private void addRandomButton(){
        Button button = createGenericColorButton(ButtonType.RANDOM_COLOR, RANDOM_SHADE_KEY);
        button.setBackgroundResource(R.drawable.random_color_button);
        buttonUtils.putButtonInLayoutAndAddToList(button, buttonLayoutParams, colorButtonLayouts);
    }


    private void addMultiColorShadeButtons(){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(colors, ButtonType.MULTISHADE);
        shadeLayoutsMap.put(MULTI_SHADE_KEY, shadeLayout);
    }


    private void addRandomShadeButtons(){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(colors, ButtonType.RANDOM_SHADE);
        shadeLayoutsMap.put(RANDOM_SHADE_KEY, shadeLayout);
    }


    private void addMultiColorShades(int color, List<Integer> shades){
        multiColorShades.put(color, shades);
    }


    private void addShadesToLayoutMap(int color, List<Integer> shades){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(shades, ButtonType.SHADE);
        shadeLayoutsMap.put(createColorKey(color, ButtonType.COLOR), shadeLayout);
    }


    private LinearLayout createLayoutWithButtonsFrom(List<Integer> shades, ButtonType buttonType){
        LinearLayout shadeLayout = new LinearLayout(context);
        shadeLayout.setOrientation(layoutOrientation);
        for(int shade: shades){
            LinearLayout buttonLayout = createShadeButton(shade, buttonType);
            shadeLayout.addView(buttonLayout);
        }
        return shadeLayout;
    }


    private LinearLayout createShadeButton(final int color, ButtonType buttonType){
        String key = createColorKey(color, buttonType);
        Button button = createButton(color, buttonType, key);
        if(buttonType == ButtonType.MULTISHADE){
            addMultiShadeDrawableTo(button, color);
        }
        return buttonUtils.wrapInMarginLayout(buttonLayoutParams, button);
    }


    private void addMultiShadeDrawableTo(Button button, final int color){

        final List<Integer> shades = multiColorShades.get(color);
        randomShadeIconDrawer.drawBackgroundOf(button, shades);
    }


    private String createColorKey(int color, ButtonType buttonType){
        return buttonType + "_" + color;
    }


    private void addColorButton(int currentColor, String key){
        Button button = createButton(currentColor, ButtonType.COLOR, key);
        if(defaultColor.equals(key)){
            button.setTag(R.string.tag_button_default_color);
        }
        else{
            button.setTag(R.string.tag_button_color_button);
        }
        buttonUtils.putButtonInLayoutAndAddToList(button, buttonLayoutParams, colorButtonLayouts);
    }


    private Button createButton(int color, ButtonType type, String key){
        Button button = createGenericColorButton(type, key);
        button.setTag(R.string.tag_button_color, color);
        button.setBackgroundColor(color);
        return button;
    }


    private Button createGenericColorButton(ButtonType type, String key){
        Button button = new Button(context);
        button.setLayoutParams(buttonLayoutParams.getUnselected());
        button.setTag(R.string.tag_button_type, type);
        button.setId(View.generateViewId());
        button.setTag(R.string.tag_button_key, key);
        button.setTag(R.string.tag_button_category, ButtonCategory.COLOR_SELECTION);
        buttonUtils.setStandardWidthOn(button);
        buttonReferenceStore.add(button);
        button.setOnClickListener(activity);
        return button;
    }


}
