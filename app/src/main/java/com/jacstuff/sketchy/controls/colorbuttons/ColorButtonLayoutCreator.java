package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.ButtonUtils;
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
    private final MainViewModel viewModel;
    private final ReusableShadesLayoutHolder reusableShadesLayoutHolder;


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
        reusableShadesLayoutHolder = new ReusableShadesLayoutHolder(mainActivity, buttonUtils);
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
        addColorButton(color);
        List<Integer> shades = viewModel.buttonShadesStore.getShadesFor(color, colorShadeCreator);
        reusableShadesLayoutHolder.initReusableShadesLayout();
        addMultiColorShades(color, shades);
        addMultiColorShadesForSequences(color);
    }


    public ReusableShadesLayoutHolder getReusableShadesLayoutHolder(){
        return this.reusableShadesLayoutHolder;
    }


    private void addMultiColorButton(){
        Button button = buttonUtils.createGenericColorButton(ButtonType.MULTICOLOR, MULTI_SHADE_KEY);
        button.setBackgroundResource(R.drawable.multi_color_button);
        buttonUtils.putButtonInLayoutAndAddToList(button, buttonLayoutParams, colorButtonLayouts);
    }


    private void addMultiColorShadeButtons(){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(colors);
        shadeLayout.setId(R.id.multiShadeLayout);
        shadeLayoutsMap.put(MULTI_SHADE_KEY, shadeLayout);
    }


    private void addMultiColorShades(int color, List<Integer> shades){
        multiColorShades.put(color, shades);
    }


    private void addMultiColorShadesForSequences(int color){
        multiColorShadesForSequences.put(color, viewModel.buttonShadesStore.getShadesFor(color, colorShadeCreatorForSequences));
    }


    private LinearLayout createLayoutWithButtonsFrom(List<Integer> shades){
        LinearLayout shadeLayout = new LinearLayout(context);
        for(int shade: shades){
            LinearLayout buttonLayout = buttonUtils.createShadeButton(shade, ButtonType.MULTI_SHADE);
            addDrawableToMultiShadeButton(buttonLayout, shade);
            shadeLayout.addView(buttonLayout);
        }
        return shadeLayout;
    }


    private void addDrawableToMultiShadeButton(LinearLayout buttonLayout, int color){
        Button button = (Button) buttonLayout.getChildAt(0);
        multiShadeButtonIconDrawer.drawBackgroundOf(button, multiColorShades.get(color));
    }


    private void addColorButton(int color){
        String key = buttonUtils.createColorKey(color, ButtonType.COLOR);
        Button button = buttonUtils.createButton(color, ButtonType.COLOR, key);
        if(defaultColor.equals(key)){
            button.setTag(R.string.tag_button_default_color);
        }
        else{
            button.setTag(R.string.tag_button_color_button);
        }
        buttonUtils.putButtonInLayoutAndAddToList(button, buttonLayoutParams, colorButtonLayouts);
    }

}
