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

    private ColorShadeCreator colorShadeCreator;
    private final Map<Integer, List<Integer>> multiColorShades = new HashMap<>();
    private final List<LinearLayout> colorButtonLayouts = new ArrayList<>();
    private final Context context;
    private final Map<String, LinearLayout> shadeLayoutsMap = new HashMap<>();
    private final String MULTI_SHADE_KEY = "multi shade key";
    private final ButtonLayoutParams buttonLayoutParams;
    private final String defaultColor;
    private final ButtonUtils buttonUtils;
    private final MainActivity activity;
    private final MultiShadeButtonIconDrawer multiShadeButtonIconDrawer;
    private final MainViewModel viewModel;
    private final ReusableShadesLayoutHolder reusableShadesLayoutHolder;


    public ColorButtonLayoutCreator(MainActivity mainActivity, ButtonLayoutParams buttonLayoutParams){
        this.context = mainActivity.getApplicationContext();
        this.viewModel = mainActivity.getViewModel();
        this.activity = mainActivity;
        defaultColor = mainActivity.getString(R.string.default_color);
        multiShadeButtonIconDrawer = new MultiShadeButtonIconDrawer(activity, viewModel);
        setupColorShadeCreator();
        this.buttonLayoutParams = buttonLayoutParams;
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


    public ReusableShadesLayoutHolder getReusableShadesLayoutHolder(){
        return this.reusableShadesLayoutHolder;
    }


    private void setupColorShadeCreator(){
        viewModel.numberOfSequenceShadesForButtons = getInt(R.integer.color_sequences_for_buttons_shade_number);
        int shadeIncrementForButtons         = getInt(R.integer.color_sequences_for_buttons_step_size);
        colorShadeCreator = new ColorShadeCreator(viewModel.numberOfSequenceShadesForButtons, shadeIncrementForButtons);
    }


    private void setupColorAndShadeButtons(){
        addMultiColorButton();
        reusableShadesLayoutHolder.initReusableShadesLayout(colorShadeCreator);
        for(int color : viewModel.mainColors){
            addColorAndShadeButtons(color);
        }
        for(int color: viewModel.recentlyAddedColors){
            addColorAndShadeButtons(color);
        }
        addMultiColorShadeButtons();
    }


    private void addColorAndShadeButtons(int color){
        addColorButton(color);
        List<Integer> shades = viewModel.buttonShadesStore.getShadesFor(color, colorShadeCreator);
        addMultiColorShades(color, shades);
    }


    public void addUserGeneratedColorAndShadeButtons(int color, LinearLayout parentLayout){
        parentLayout.addView(createUserColorButton(color));
        List<Integer> shades = viewModel.buttonShadesStore.getShadesFor(color, colorShadeCreator);
        addMultiColorShades(color, shades);
        viewModel.recentlyAddedColors.add(color);
        addMultiShadeButtonFor(color);
    }


    private void addMultiColorButton(){
        Button button = buttonUtils.createGenericColorButton(ButtonType.MULTICOLOR, MULTI_SHADE_KEY);
        button.setBackgroundResource(R.drawable.multi_color_button);
        buttonUtils.putButtonInLayoutAndAddToList(button, buttonLayoutParams, colorButtonLayouts);
    }


    private void addMultiColorShadeButtons(){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(viewModel.mainColors);
        shadeLayout.setId(R.id.multiShadeLayout);
        addMultiShadeButtonsForRecentlyAddedColorsTo(shadeLayout);
        shadeLayoutsMap.put(MULTI_SHADE_KEY, shadeLayout);
    }


    private void addMultiColorShades(int color, List<Integer> shades){
        multiColorShades.put(color, shades);
    }


    private LinearLayout createLayoutWithButtonsFrom(List<Integer> shades){
        LinearLayout multiShadeLayout = new LinearLayout(context);
        for(int shade: shades){
           multiShadeLayout.addView(createMultiShadeButton(shade));
        }
        return multiShadeLayout;
    }


    void addMultiShadeButtonsForRecentlyAddedColorsTo(LinearLayout shadeLayout){
        for(int recentlyAddedColor: viewModel.recentlyAddedColors){
            shadeLayout.addView(createMultiShadeButton(recentlyAddedColor));
        }
    }


    private LinearLayout createMultiShadeButton(int color){
        LinearLayout buttonLayout = buttonUtils.createShadeButton(color, ButtonType.MULTI_SHADE);
        addDrawableToMultiShadeButton(buttonLayout, color);
        return buttonLayout;
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


    private LinearLayout createUserColorButton(int color){
        String key = buttonUtils.createColorKey(color, ButtonType.COLOR);
        Button button = buttonUtils.createButton(color, ButtonType.COLOR, key);
        if(defaultColor.equals(key)){
            button.setTag(R.string.tag_button_default_color);
        }
        else{
            button.setTag(R.string.tag_button_color_button);
        }
        return buttonUtils.wrapInMarginLayout(buttonLayoutParams, button);
    }


    private void addMultiShadeButtonFor(int color){
        LinearLayout multiShadeLayout = shadeLayoutsMap.get(MULTI_SHADE_KEY);
        if(multiShadeLayout == null){
            return;
        }
        multiShadeLayout.addView(createMultiShadeButton(color));
    }


    private int getInt(int resId){
        return activity.getResources().getInteger(resId);
    }

}
