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
    private LinearLayout colorButtonGroupLayout, colorButtonParentLayout, multiShadeButtonLayout;
    private final ColorButtonClickHandler colorButtonClickHandler;

    public ColorButtonLayoutCreator(MainActivity mainActivity, ButtonLayoutParams buttonLayoutParams, ColorButtonClickHandler colorButtonClickHandler){
        this.context = mainActivity.getApplicationContext();
        this.activity = mainActivity;
        setupLayoutViews();
        this.colorButtonClickHandler = colorButtonClickHandler;
        this.viewModel = mainActivity.getViewModel();
        defaultColor = mainActivity.getString(R.string.default_color);
        multiShadeButtonIconDrawer = new MultiShadeButtonIconDrawer(activity, viewModel);
        setupColorShadeCreator();
        this.buttonLayoutParams = buttonLayoutParams;
        buttonUtils = new ButtonUtils(mainActivity);
        reusableShadesLayoutHolder = new ReusableShadesLayoutHolder(mainActivity, buttonUtils);
        addMultiColorButton();
        setupColorAndShadeButtons();
    }


    private void setupLayoutViews(){
        colorButtonGroupLayout = activity.findViewById(R.id.colorButtonGroup);
        colorButtonParentLayout = activity.findViewById(R.id.colorButtonParentLayout);
        multiShadeButtonLayout = new LinearLayout(context);
        LinearLayout multiShadeParentLayout = new LinearLayout(context);
        multiShadeParentLayout.addView(multiShadeButtonLayout);
        shadeLayoutsMap.put(MULTI_SHADE_KEY, multiShadeParentLayout);
    }


    public void reloadButtons(){
        removeAllColorAndShadeButtons();
        setupColorAndShadeButtons();
        addColorButtonLayouts();
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
        colorButtonGroupLayout.removeViewAt(index);
         addColorButtonAtIndex(index, amendedColor);
         addShadeButtonsFor(amendedColor);
         replaceMultiShadeButton(index, amendedColor);
         clickOnButton(index);
    }


    private void clickOnButton(int index){
        LinearLayout buttonLayout = (LinearLayout) colorButtonGroupLayout.getChildAt(index);
        Button button = (Button)buttonLayout.getChildAt(0);
        colorButtonClickHandler.onClick(button);
    }


    private void addColorButtonAtIndex(int index, int amendedColor){
        LinearLayout buttonLayout = createColorButtonInLayout(amendedColor, index);
        colorButtonGroupLayout.addView(buttonLayout, index);
    }


    private void replaceMultiShadeButton(int index, int amendedColor){
        deselectMultiShadeButton(index);
        multiShadeButtonLayout.removeViewAt(index);
        addMultiShadeButtonFor(amendedColor, index);
    }


    private void deselectMultiShadeButton(int index){
        LinearLayout buttonWrapper = (LinearLayout) multiShadeButtonLayout.getChildAt(index);
        Button multiShadeButton = (Button)buttonWrapper.getChildAt(0);
        colorButtonClickHandler.deselectMultiShadeButtonForRemoval(multiShadeButton);
    }


    private void deselectAllMultiShadeButtons(){
        for(int i=0; i< multiShadeButtonLayout.getChildCount(); i++){
            deselectMultiShadeButton(i);
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
        int shadeIncrementForButtons = getInt(R.integer.color_sequences_for_buttons_step_size);
        colorShadeCreator = new ColorShadeCreator(viewModel.numberOfSequenceShadesForButtons, shadeIncrementForButtons);
    }


    private void setupColorAndShadeButtons(){
        reusableShadesLayoutHolder.initReusableShadesLayout(colorShadeCreator);
        List<Integer> colors = UserColorStore.getAllColors(context);
        loadColorsFromPreferences(colors);
        addMultiColorShadeButtons(colors);
    }


    private void removeAllColorAndShadeButtons(){
        colorButtonGroupLayout.removeAllViews();
        colorButtonLayouts.clear();
        deselectAllMultiShadeButtons();
        multiShadeButtonLayout.removeAllViews();
    }


    private void loadColorsFromPreferences(List<Integer> colors ){
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
        LinearLayout multiColorButtonLayout = buttonUtils.wrapInMarginLayout(buttonLayoutParams, button);
        colorButtonParentLayout.addView(multiColorButtonLayout, 0);
    }


    private void addMultiColorShadeButtons( List<Integer> colors ){
        for(int color: colors){
            multiShadeButtonLayout.addView(createMultiShadeButton(color));
        }
    }


    private void addMultiColorShades(int color, List<Integer> shades){
        multiColorShades.put(color, shades);
    }


    private LinearLayout createMultiShadeButton(int color){
        Button button =  buttonUtils.createShadeButtonOnly(color, ButtonType.MULTI_SHADE);
        button.setOnLongClickListener(colorButtonClickHandler::onLongClick);
        LinearLayout buttonLayout = buttonUtils.wrapInMarginLayout(buttonLayoutParams, button);
        int id = View.generateViewId();
        buttonLayout.setId(id);
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
        return buttonUtils.wrapInMarginLayout(buttonLayoutParams, button);
    }


    private Button createColorButton(int color, int index){
        String key = buttonUtils.createColorKey(color, ButtonType.COLOR);
        Button button = buttonUtils.createButton(color, ButtonType.COLOR, key);
        addLongClickEditListenerTo(button, index);
        setTagOnButton(button, key);
        return button;
    }


    private void setTagOnButton(Button button, String key){
        if(defaultColor.equals(key)){
            button.setTag(R.string.tag_button_default_color);
        }
        else{
            button.setTag(R.string.tag_button_color_button);
        }
    }


    private void addLongClickEditListenerTo(Button button, int index){
        button.setOnLongClickListener(view -> {
            int color = (int)view.getTag(R.string.tag_button_color);
            activity.startEditColorFragment(color, index);
            colorButtonClickHandler.onClick(view);
            return true;
        });
    }


    private void addMultiShadeButtonFor(int color, int index){
        LinearLayout buttonLayout = createMultiShadeButton(color);
        multiShadeButtonLayout.addView(buttonLayout, index);
    }


    private int getInt(int resId){
        return activity.getResources().getInteger(resId);
    }

}
