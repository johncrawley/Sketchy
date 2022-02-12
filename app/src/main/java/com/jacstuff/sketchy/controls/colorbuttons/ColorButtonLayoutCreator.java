package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.ui.SettingsPopup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ColorButtonLayoutCreator {

    private ColorShadeCreator colorShadeCreator, colorShadeCreatorForSequences;
    private final Map<Integer, List<Integer>> multiColorShades = new HashMap<>();
    private final Map<Integer, List<Integer>> multiColorShadesForSequences = new HashMap<>();
    private final List<LinearLayout> colorButtonLayouts = new ArrayList<>();
    private final Context context;
    private final List<Integer> colors;
    private final Map<String, LinearLayout> shadeLayoutsMap = new HashMap<>();
    private final String MULTI_SHADE_KEY = "multi shade key";
    private final ButtonLayoutParams buttonLayoutParams;
    private final String defaultColor;
    private final ButtonUtils buttonUtils;
    private final MainActivity activity;
    private final MultiShadeButtonIconDrawer multiShadeButtonIconDrawer;
    private final SettingsPopup settingsPopup;
    private final MainViewModel viewModel;
    private LinearLayout reusableShadesLayout;


    public ColorButtonLayoutCreator(MainActivity mainActivity, ButtonLayoutParams buttonLayoutParams, final List<Integer> colors){
        this.context = mainActivity.getApplicationContext();
        this.viewModel = mainActivity.getViewModel();
        this.activity = mainActivity;
        defaultColor = mainActivity.getString(R.string.default_color);
        multiShadeButtonIconDrawer = new MultiShadeButtonIconDrawer(activity, activity.getViewModel());
        setupColorShadeCreator();
        this.buttonLayoutParams = buttonLayoutParams;
        this.colors = colors;
        buttonUtils = new ButtonUtils(mainActivity);
        settingsPopup = mainActivity.getSettingsPopup();
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


    public LinearLayout getMultiShadesLayout(){
        return shadeLayoutsMap.get(MULTI_SHADE_KEY);
    }


    public Map<Integer, List<Integer>> getMultiColorShadesForSequences(){
        return this.multiColorShadesForSequences;
    }


    private void setupColorShadeCreator(){

        int numberOfSequenceShades  = getRes(R.integer.color_sequences_shade_number);
        int shadeIncrement          = getRes(R.integer.color_sequences_step_size);
        colorShadeCreatorForSequences = new ColorShadeCreator(numberOfSequenceShades, shadeIncrement);

        int numberOfSequenceShadesForButtons = getRes(R.integer.color_sequences_for_buttons_shade_number);
        int shadeIncrementForButtons         = getRes(R.integer.color_sequences_for_buttons_step_size);
        colorShadeCreator = new ColorShadeCreator(numberOfSequenceShadesForButtons, shadeIncrementForButtons);
    }


    private int getRes(int resId){
        return activity.getResources().getInteger(resId);
    }


    private void setupColorAndShadeButtons(){
        for(int color : colors){
            addColorAndShadeButtons(color);
        }
        addMultiColorButton();
        addMultiColorShadeButtons();
    }


    private void addColorAndShadeButtons(int color){
        addColorButton(color, createColorKey(color, ButtonType.COLOR));
        List<Integer> shades = colorShadeCreator.generateShadesFrom(color);
        viewModel.buttonShadesStore.addShades(color, shades);
        addShadesToLayoutMap(color, shades);
        addMultiColorShades(color, shades);
        addMultiColorShadesForSequences(color, colorShadeCreatorForSequences.generateShadesFrom(color));
    }


    private void addMultiColorButton(){
        Button button = createGenericColorButton(ButtonType.MULTICOLOR, MULTI_SHADE_KEY);
        button.setBackgroundResource(R.drawable.multi_color_button);
        buttonUtils.putButtonInLayoutAndAddToList(button, buttonLayoutParams, colorButtonLayouts);
    }


    private void addMultiColorShadeButtons(){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(colors, ButtonType.MULTI_SHADE);
        shadeLayout.setId(R.id.multiShadeLayout);
        shadeLayoutsMap.put(MULTI_SHADE_KEY, shadeLayout);
    }


    private void addMultiColorShades(int color, List<Integer> shades){
        multiColorShades.put(color, shades);
    }


    private void addMultiColorShadesForSequences(int color, List<Integer> shades){
        multiColorShadesForSequences.put(color, shades);
    }


    private void addShadesToLayoutMap(int color, List<Integer> shades){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(shades, ButtonType.SHADE);
        shadeLayoutsMap.put(createColorKey(color, ButtonType.COLOR), shadeLayout);
    }


    private LinearLayout createLayoutWithButtonsFrom(List<Integer> shades, ButtonType buttonType){
        LinearLayout shadeLayout = new LinearLayout(context);
        for(int shade: shades){
            LinearLayout buttonLayout = createShadeButton(shade, buttonType);
            shadeLayout.addView(buttonLayout);
        }
        return shadeLayout;
    }


    private void createReusableShadesLayout(List<Integer> shades, ButtonType buttonType){
        reusableShadesLayout = new LinearLayout(context);
        for(int shade: shades){
            LinearLayout buttonLayout = createShadeButton(shade, buttonType);
            reusableShadesLayout.addView(buttonLayout);
        }
    }


    private void assignShadesToReusableShadesLayout(List<Integer> shades, ButtonType buttonType){
        reusableShadesLayout = new LinearLayout(context);
        for(int i=0; i< reusableShadesLayout.getChildCount(); i++){
            LinearLayout wrapperLayout = (LinearLayout) reusableShadesLayout.getChildAt(i);
            Button button = (Button)wrapperLayout.getChildAt(0);
            int shade = shades.get(i);
            String key = createColorKey(shade, buttonType);
            button.setBackgroundColor(shade);
            button.setTag(R.string.tag_button_key, key);
        }
        for(int shade: shades){
            LinearLayout buttonLayout = createShadeButton(shade, buttonType);
            reusableShadesLayout.addView(buttonLayout);
        }
    }


    private LinearLayout createShadeButton(final int color, ButtonType buttonType){
        String key = createColorKey(color, buttonType);
        Button button = createButton(color, buttonType, key);
        if(buttonType == ButtonType.MULTI_SHADE){
            addMultiShadeDrawableTo(button, color);
        }
        return buttonUtils.wrapInMarginLayout(buttonLayoutParams, button);
    }


    private void addMultiShadeDrawableTo(Button button, final int color){
        final List<Integer> shades = multiColorShades.get(color);
        multiShadeButtonIconDrawer.drawBackgroundOf(button, shades);
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
        settingsPopup.registerToIgnoreForLandscape(button.getId());
        button.setTag(R.string.tag_button_key, key);
        button.setTag(R.string.tag_button_category, ButtonCategory.COLOR_SELECTION);
        buttonUtils.setStandardWidthOn(button);
        activity.getButtonReferenceStore().add(button);
        button.setOnClickListener(activity);
        return button;
    }


}
