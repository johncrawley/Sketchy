package com.jacstuff.sketchy.controls.colorbuttons;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.ui.UserColorStore;
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
    private final LinearLayout colorButtonGroupLayout;
    private final Map<Integer, Integer> colorToIdMap;
    private final Map<Integer, ButtonAndLayoutId> multiColorToIdMap;
    private final ColorButtonClickHandler colorButtonClickHandler;


    public ColorButtonLayoutCreator(MainActivity mainActivity, ButtonLayoutParams buttonLayoutParams, ColorButtonClickHandler colorButtonClickHandler){
        this.context = mainActivity.getApplicationContext();
        colorButtonGroupLayout = mainActivity.findViewById(R.id.colorButtonGroup);
        this.colorButtonClickHandler = colorButtonClickHandler;
        this.viewModel = mainActivity.getViewModel();
        colorToIdMap = new HashMap<>();
        multiColorToIdMap = new HashMap<>();
        this.activity = mainActivity;
        defaultColor = mainActivity.getString(R.string.default_color);
        multiShadeButtonIconDrawer = new MultiShadeButtonIconDrawer(activity, viewModel);
        setupColorShadeCreator();
        this.buttonLayoutParams = buttonLayoutParams;
        buttonUtils = new ButtonUtils(mainActivity);
        reusableShadesLayoutHolder = new ReusableShadesLayoutHolder(mainActivity, buttonUtils);
        setupColorAndShadeButtons();
    }


    public void addUserGeneratedColorAndShadeButtons(int color){
        System.out.println("^^^ ColorButtLayoutCreator, addUserGeneratedColorAndShadeButtons() color: " + color);
    }


    public void removeButtonsFor(int color){
        removeButtonFromLayout(color, colorToIdMap, colorButtonGroupLayout);
        deselectAndRemoveMultiShadeButton(color);
    }


    public View getDefaultColorButton(){
        View defaultButton = colorButtonGroupLayout.findViewWithTag(R.string.tag_button_default_color);
        if(defaultButton == null){
            defaultButton = colorButtonGroupLayout.findViewWithTag(R.string.tag_button_color_button);
        }
        return defaultButton;
    }


    public void addColorButtonLayouts(){
        for(LinearLayout buttonLayout: colorButtonLayouts){
            colorButtonGroupLayout.addView(buttonLayout);
        }
    }


    public void replaceColorButton(int index, int amendedColor){
        int prefixedButtons = 1;
        int actualIndex = index + prefixedButtons;
        colorButtonGroupLayout.removeViewAt(actualIndex);
         addColorButtonAtIndex(actualIndex, amendedColor);
         addShadeButtonsFor(amendedColor);
         replaceMultiShadeButton(index, amendedColor);
    }


    private void addColorButtonAtIndex(int index, int amendedColor){
        LinearLayout buttonLayout = createColorButtonInLayout(amendedColor, index);
        colorButtonGroupLayout.addView(buttonLayout, index);
    }


    private void replaceMultiShadeButton(int index, int amendedColor){
        deselectMultiShadeButton(index);
        getMultiShadesLayout().removeViewAt(index);
        addMultiShadeButtonFor(amendedColor, index);
    }


    private void deselectMultiShadeButton(int index){
        LinearLayout multiShadeButtonLayout = (LinearLayout) getMultiShadesLayout().getChildAt(index);
        Button multiShadeButton = (Button) multiShadeButtonLayout.getChildAt(0);
        colorButtonClickHandler.deselectMultiShadeButtonForRemoval(multiShadeButton);
    }


    private void removeButtonFromLayout(int color, Map<Integer, Integer> colorIdMap, LinearLayout layout){
        Integer id = colorIdMap.get(color);
        if(id != null){
            View view = layout.findViewById(id);
            layout.removeView(view);
        }
    }


    private void deselectAndRemoveMultiShadeButton(int color){
        ButtonAndLayoutId buttonAndLayoutId = multiColorToIdMap.get(color);
        if(buttonAndLayoutId != null) {
            colorButtonClickHandler.deselectMultiShadeButtonForRemoval(buttonAndLayoutId.getButton());
            removeButtonFromLayout(buttonAndLayoutId.getLayoutId(), getMultiShadesLayout());
        }
    }


    private void removeButtonFromLayout(int id, LinearLayout layout){
        View view = layout.findViewById(id);
        layout.removeView(view);
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
        int shadeIncrementForButtons = getInt(R.integer.color_sequences_for_buttons_step_size);
        colorShadeCreator = new ColorShadeCreator(viewModel.numberOfSequenceShadesForButtons, shadeIncrementForButtons);
    }


    private void setupColorAndShadeButtons(){
        addMultiColorButton();
        reusableShadesLayoutHolder.initReusableShadesLayout(colorShadeCreator);
        loadColorsFromPreferences();
        addMultiColorShadeButtons();
    }

    private void loadColorsFromPreferences(){
        List<Integer> colors = UserColorStore.getAllColors(context);
        for(int i=0; i< colors.size(); i++){
            addColorAndShadeButtons(colors.get(i), i);
        }
    }


    private void addColorAndShadeButtons(int color, int index){
        addColorButton(color, index);
        addShadeButtonsFor(color);
    }


    private void addShadeButtonsFor(int color){
        List<Integer> shades = viewModel.buttonShadesStore.getShadesFor(color, colorShadeCreator);
        addMultiColorShades(color, shades);
    }


    private void addMultiColorButton(){
        Button button = buttonUtils.createGenericColorButton(ButtonType.MULTICOLOR, MULTI_SHADE_KEY);
        button.setBackgroundResource(R.drawable.multi_color_button);
        buttonUtils.putButtonInLayoutAndAddToList(button, buttonLayoutParams, colorButtonLayouts);
    }


    private void addMultiColorShadeButtons(){
        LinearLayout shadeLayout = createLayoutWithButtonsFrom(viewModel.mainColors);
        shadeLayout.setId(R.id.multiShadeLayout);
        addMultiShadeButtonsForUserColorsTo(shadeLayout);
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


    private void addMultiShadeButtonsForUserColorsTo(LinearLayout shadeLayout){
        for(int userColor: viewModel.userColors){
            shadeLayout.addView(createMultiShadeButton(userColor));
        }
        for(int recentlyAddedColor: viewModel.recentlyAddedColors){
            shadeLayout.addView(createMultiShadeButton(recentlyAddedColor));
        }
    }


    private LinearLayout createMultiShadeButton(int color){
        LinearLayout buttonLayout = buttonUtils.createShadeButton(color, ButtonType.MULTI_SHADE);

        int id = View.generateViewId();
        buttonLayout.setId(id);
        Button button = (Button)buttonLayout.getChildAt(0);
        multiColorToIdMap.put(color, new ButtonAndLayoutId(button, id));
        addDrawableToMultiShadeButton(buttonLayout, color);
        return buttonLayout;
    }


    private void addDrawableToMultiShadeButton(LinearLayout buttonLayout, int color){
        Button button = (Button) buttonLayout.getChildAt(0);
        multiShadeButtonIconDrawer.drawBackgroundOf(button, multiColorShades.get(color));
    }


    private void addColorButton(int color, int index){
        Button button = createColorButton(color, index);
        buttonUtils.putButtonInLayoutAndAddToList(button, buttonLayoutParams, colorButtonLayouts);
    }


    private LinearLayout createColorButtonInLayout(int color, int index){
        Button button = createColorButton(color, index);
        LinearLayout buttonLayout = buttonUtils.wrapInMarginLayout(buttonLayoutParams, button);
        colorToIdMap.put(color, buttonLayout.getId());
        return buttonLayout;
    }


    private Button createColorButton(int color, int index){
        String key = buttonUtils.createColorKey(color, ButtonType.COLOR);
        Button button = buttonUtils.createButton(color, ButtonType.COLOR, key);
        addLongClickEditListenerTo(button, index);

        if(defaultColor.equals(key)){
            button.setTag(R.string.tag_button_default_color);
        }
        else{
            button.setTag(R.string.tag_button_color_button);
        }
        return button;
    }


    private void addLongClickEditListenerTo(Button button, int index){
        button.setOnLongClickListener(view -> {
            int color = (int)view.getTag(R.string.tag_button_color);
            activity.startEditColorFragment(color, index);
            return true;
        });
    }


    private void addMultiShadeButtonFor(int color, int index){
        LinearLayout multiShadeLayout = shadeLayoutsMap.get(MULTI_SHADE_KEY);
        if(multiShadeLayout == null){
            return;
        }
        multiShadeLayout.addView(createMultiShadeButton(color), index);
    }


    private int getInt(int resId){
        return activity.getResources().getInteger(resId);
    }

}
