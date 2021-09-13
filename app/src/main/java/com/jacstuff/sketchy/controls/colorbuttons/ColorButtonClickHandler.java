package com.jacstuff.sketchy.controls.colorbuttons;

import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.multicolor.ShadesStore;
import com.jacstuff.sketchy.paintview.helpers.ColorHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.multicolor.SingleColorSelector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorButtonClickHandler {

    private Button previouslySelectedShadeButton, previouslySelectedColorButton, previouslySelectedButton;
    private List<Integer> colors;
    private final ButtonLayoutParams buttonLayoutParams;
    private Map<String, LinearLayout> shadeLayoutsMap;
    private boolean isMostRecentClickAShade = false; //for use when selecting a button after rotate/resume
    private final LinearLayout shadesLayout;
    private ShadesStore shadesStore;
    private Map<ButtonType, ColorSelector> colorSelectors;
    private final MainActivity mainActivity;
    private ColorSelector currentColorSelector;
    private final RandomShadeButtonsState shadeButtonsState;
    private final int buttonStatusTag = R.string.multi_random_button_checked_tag;
    private enum ButtonStatus { ENABLED, DISABLED }
    private final ButtonReferenceStore buttonReferenceStore;
    private final MainViewModel mainViewModel;
    private final ColorHelper colorHelper;


    public ColorButtonClickHandler(MainActivity mainActivity, ButtonLayoutParams buttonLayoutParams){
        this.mainActivity = mainActivity;
        this.buttonLayoutParams = buttonLayoutParams;

        this.buttonReferenceStore = mainActivity.getButtonReferenceStore();
        this.mainViewModel = mainActivity.getViewModel();
        this.shadesLayout = mainActivity.findViewById(R.id.shadesButtonGroup);
        this.colorHelper = mainActivity.getPaintHelperManager().getColorHelper();

        shadeButtonsState = new RandomShadeButtonsState();
        setupColorSelectors();
        setupPreexistingState();
    }


    private void setupPreexistingState(){
        int buttonId = mainViewModel.lastClickedColorButtonId;
        View view = mainActivity.findViewById(buttonId);
        onClick(view);
    }


    private void setupColorSelectors(){
        ColorSelector singleSelector = new SingleColorSelector();
        colorSelectors = new HashMap<>();
        colorSelectors.put(ButtonType.COLOR, singleSelector);
        colorSelectors.put(ButtonType.SHADE, singleSelector);
        colorSelectors.put(ButtonType.MULTICOLOR, colorHelper.getSequenceColorSelector());
        colorSelectors.put(ButtonType.MULTI_SHADE, colorHelper.getShadeColorSelector());
    }


    public void setColorsMap(final List<Integer> colors){
       this.colors = colors;
    }


    public String getMostRecentColorButtonKey(){
        return buttonReferenceStore.getKeyFrom(previouslySelectedColorButton);
    }


    public String getMostRecentShadeButtonKey(){
        return buttonReferenceStore.getKeyFrom(previouslySelectedShadeButton);
    }


   public boolean isMostRecentClickAShade(){
        return isMostRecentClickAShade;
    }


    public void setShadeLayoutsMap(Map<String, LinearLayout> shadeLayoutsMap){
        this.shadeLayoutsMap = shadeLayoutsMap;
    }


    public void setShadesStore(ShadesStore shadesStore){
        this.shadesStore = shadesStore;
    }


    public void onClick(int id){
       View view = mainActivity.findViewById(id);
       onClick(view);
    }


    public void onClick(View view){

        if(!isColorButton(view)){
            return;
        }
        Button button = (Button)view;
        ButtonType buttonType = (ButtonType)view.getTag(R.string.tag_button_type);
        focusOn(button);
        assignColorSelectorToPaintViewFrom(button);
        switch(buttonType){
            case COLOR:
                onMainColorButtonClick(button);
                break;
            case MULTICOLOR:
                onMultiColorButtonClick(button);
                break;
            case SHADE:
                onShadeButtonClick(button);
                break;
            case MULTI_SHADE:
                clickShadeButtonMultiMode(button);
        }
        previouslySelectedButton = button;
    }


    private boolean isColorButton(View view){
        return view != null && ButtonCategory.COLOR_SELECTION == view.getTag(R.string.tag_button_category);
    }


    private void focusOn(Button button){
        if(button == null){
            return;
        }
        ViewParent parent = button.getParent();
        if(parent != null) {
            button.getParent().requestChildFocus(button, button);
        }
    }


    private void assignColorSelectorToPaintViewFrom(Button button){
        ButtonType buttonType = (ButtonType)button.getTag(R.string.tag_button_type);
        currentColorSelector = colorSelectors.get(buttonType);
        colorHelper.setColorSelector(currentColorSelector);
    }


    private void onMainColorButtonClick(Button button){
        colorHelper.setColorSelector(currentColorSelector);
        setColorAndUpdateButtons(button);
        previouslySelectedColorButton = button;
        assignShadeLayoutFrom(button);
        isMostRecentClickAShade = false;
    }


    private void onShadeButtonClick(Button button){
        setColorAndUpdateButtons(button);
        previouslySelectedShadeButton = button;
        isMostRecentClickAShade = true;
    }


    private void onMultiColorButtonClick(Button button){
        deselectPreviousButtons();
        assignMultiSelector(button, colors);
        previouslySelectedColorButton = button;
        selectButton(button);
        assignShadeLayoutFrom(button);
        deselectButtons(shadeButtonsState.getSelected());
        shadeButtonsState.deselectMulti();
        isMostRecentClickAShade = false;
    }


    private void setColorAndUpdateButtons(Button button){
        setPaintColorFrom(button);
        deselectPreviousButtons();
        selectButton(button);
    }


    private void setPaintColorFrom(Button button){
        int color = getColorOf(button);
        currentColorSelector.setColorList(color);
    }


    private int getColorOf(Button button){
        if(button.getTag(R.string.tag_button_color) == null){
            return -1;
        }
        return (int)button.getTag(R.string.tag_button_color);
    }


    private void assignMultiSelector(Button button, List<Integer> colorList){
        if(button != previouslySelectedButton){
            currentColorSelector.reset();
            colorHelper.setColorSelector(currentColorSelector);
        }
        currentColorSelector.setColorList(colorList);
    }


    private void selectButton(Button button){
        button.setLayoutParams(buttonLayoutParams.getSelected());
    }


    private void clickShadeButtonMultiMode(Button button){
        if(!shadeButtonsState.isMultiSelected()){
            handleClickWhenMultiDisabled(button);
            return;
        }
        if(isEnabled(button)){
           deselectShadeButton(button);
            return;
        }
        selectRandomColorButton(button);
    }


    private void handleClickWhenMultiDisabled(Button button){
        if(!isEnabled(button)){
            selectRandomColorButton(button);
        }
        shadeButtonsState.selectMulti();
        selectButtons(shadeButtonsState.getSelected());
    }


    public List<String> getSelectedRandomShadeKeys(){
        List<String> buttonKeys = new ArrayList<>();
        for(Button button: shadeButtonsState.getSelected()){
            buttonKeys.add(buttonReferenceStore.getKeyFrom(button));
        }
        return buttonKeys;
    }


    private boolean isEnabled(Button button){
       return ButtonStatus.ENABLED.equals(button.getTag(buttonStatusTag));
    }


    private void deselectShadeButton(Button button){
        if(shadeButtonsState.getSelectedCount() == 1){
            return;
        }
        int buttonColor = (int)button.getTag(R.string.tag_button_color);

        currentColorSelector.remove(buttonColor);
        button.setTag(buttonStatusTag, ButtonStatus.DISABLED);
        button.setText("");
        deselectButton(button);
        shadeButtonsState.deselect(button);
    }


    private void selectRandomColorButton(Button button){
        int buttonColor = (int)button.getTag(R.string.tag_button_color);
        currentColorSelector.add(buttonColor, getShadesFrom(button));
        button.setTag(buttonStatusTag, ButtonStatus.ENABLED);
        shadeButtonsState.setSelected(button);
        selectButton(button);
    }


    private void deselectPreviousButtons(){
        deselectButton(previouslySelectedShadeButton);
        deselectButton(previouslySelectedColorButton);
    }


    private void assignShadeLayoutFrom(Button button){
        LinearLayout shadeLayout = shadeLayoutsMap.get(getKeyFrom(button));
        if (shadeLayout != null) {
            shadesLayout.removeAllViews();
            shadesLayout.addView(shadeLayout);
        }
    }


    private String getKeyFrom(Button button){
        return (String)button.getTag(R.string.tag_button_key);
    }


    private void deselectButton(Button button){
        if(button == null){
            return;
        }
        button.setLayoutParams(buttonLayoutParams.getUnselected());
    }


    private void deselectButtons(Collection<Button> buttons){
        for(Button b: buttons){
            deselectButton(b);
        }
    }


    private void selectButtons(Collection<Button> buttons){
        for(Button b: buttons){
            selectButton(b);
        }
    }


    private List<Integer> getShadesFrom(Button button){
        int color = (int)button.getTag(R.string.tag_button_color);
        return shadesStore.getShadesFor(color);
    }


}