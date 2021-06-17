package com.jacstuff.sketchy.controls.colorbuttons;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.MainViewModel;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.multicolor.RandomMultiColorSelector;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.multicolor.MulticolorSelector;
import com.jacstuff.sketchy.multicolor.RandomColorSelector;
import com.jacstuff.sketchy.multicolor.SingleColorSelector;
import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorButtonClickHandler {

    private PaintView paintView;
    private Button previouslySelectedShadeButton, previouslySelectedColorButton, previouslySelectedButton;
    private List<Integer> colors;
    private ButtonLayoutParams buttonLayoutParams;
    private Map<String, LinearLayout> shadeLayoutsMap;
    private boolean isMostRecentClickAShade = false; //for use when selecting a button after rotate/resume
    private LinearLayout shadesLayout;
    private Map<Integer, List<Integer>> multiColorShades = new HashMap<>();
    private Map<ButtonType, ColorSelector> colorSelectors;
    private MainActivity mainActivity;
    private ColorSelector currentColorSelector;
    private RandomShadeButtonsState randomShadeButtonsState;
    private int enabledTag = R.string.multi_random_button_checked_tag;
    private Button randomColorButton;
    private ButtonReferenceStore buttonReferenceStore;

    private MainViewModel mainViewModel;


    public ColorButtonClickHandler(MainActivity mainActivity, PaintView paintView, ButtonLayoutParams buttonLayoutParams, LinearLayout shadesLayout ){
        this.mainActivity = mainActivity;
        this.buttonReferenceStore = mainActivity.getButtonReferenceStore();
        this.mainViewModel = mainActivity.getViewModel();
        this.paintView = paintView;
        this.buttonLayoutParams = buttonLayoutParams;
        this.shadesLayout = shadesLayout;
        randomShadeButtonsState = new RandomShadeButtonsState();
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
        ColorSelector randomSelector = new RandomColorSelector();
        ColorPatternsFactory colorPatternsFactory = new ColorPatternsFactory(mainActivity.getApplicationContext());
        List<MulticolorPattern> colorPatterns = colorPatternsFactory.createColorPatterns();
        List<MulticolorPattern> shadePatterns = colorPatternsFactory.createShadePatterns();
        ColorSelector randomMultiColor = new RandomMultiColorSelector();
        colorSelectors = new HashMap<>();
        colorSelectors.put(ButtonType.COLOR, singleSelector);
        colorSelectors.put(ButtonType.SHADE, singleSelector);
        colorSelectors.put(ButtonType.MULTICOLOR, new MulticolorSelector(colorPatterns));
        colorSelectors.put(ButtonType.MULTISHADE, new MulticolorSelector(shadePatterns));
        colorSelectors.put(ButtonType.RANDOM_COLOR, randomSelector);
        colorSelectors.put(ButtonType.RANDOM_SHADE, randomMultiColor );
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


   public void setMultiColorShades(Map<Integer, List<Integer>> multiColorShades){
        this.multiColorShades = multiColorShades;
    }


    public void onClick(int id){
       View v = mainActivity.findViewById(id);
       if(v != null){
           onClick(v);
       }
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
            case MULTISHADE:
                onMultiShadeButtonClick(button);
                break;
            case RANDOM_COLOR:
                randomColorButton = button;
                onRandomColorButtonClick();
                break;
            case RANDOM_SHADE:
                clickRandomShadeButtonMultiMode(button);
        }
        previouslySelectedButton = button;
    }


    private boolean isColorButton(View view){
        return view != null && ButtonCategory.COLOR_SELECTION == view.getTag(R.string.tag_button_category);
    }


    private void focusOn(Button button){
        button.getParent().requestChildFocus(button, button);
    }


    private void assignColorSelectorToPaintViewFrom(Button button){

        ButtonType buttonType = (ButtonType)button.getTag(R.string.tag_button_type);
        currentColorSelector = colorSelectors.get(buttonType);
        paintView.setColorSelector(currentColorSelector);
    }


    private void onMainColorButtonClick(Button button){
        paintView.setColorSelector(currentColorSelector);
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
        isMostRecentClickAShade = false;
    }


    private void onRandomColorButtonClick(){
        deselectPreviousButtons();
        currentColorSelector.set(colors);
        if(randomColorButton == null){
            return;
        }
        previouslySelectedColorButton = randomColorButton;
        paintView.setColorSelector(currentColorSelector);
        selectButton(randomColorButton);
        assignShadeLayoutFrom(randomColorButton);
        isMostRecentClickAShade = false;
        deselectButtons(randomShadeButtonsState.getSelected());
        randomShadeButtonsState.deselectMulti();
    }


    private void selectButtonAndSetRecent(Button button){
        deselectButton(previouslySelectedShadeButton);
        previouslySelectedShadeButton = button;
        selectButton(button);
        isMostRecentClickAShade = true;
    }


    private void onMultiShadeButtonClick(Button button){
        assignMultiSelector(button, getShadesFrom(button));
        selectButtonAndSetRecent(button);
    }


    private void setColorAndUpdateButtons(Button button){
        setPaintColorFrom(button);
        deselectPreviousButtons();
        selectButton(button);
    }


    private void setPaintColorFrom(Button button){
        int color = getColorOf(button);
        currentColorSelector.set(color);
    }


    private int getColorOf(Button button){
        if(button.getTag(R.string.tag_button_color) == null){
            return -1;
        }
        return (int)button.getTag(R.string.tag_button_color);
    }


    private void assignMultiSelector(Button button, List<Integer> colorList){
        if(button == previouslySelectedButton){
            currentColorSelector.nextPattern();
            mainActivity.toastPattern(currentColorSelector.getCurrentPatternLabel());
        }else{
            currentColorSelector.reset();
            paintView.setColorSelector(currentColorSelector);
        }
        currentColorSelector.set(colorList);
    }


    private void selectButton(Button button){
        button.setLayoutParams(buttonLayoutParams.getSelected());
    }


    private void clickRandomShadeButtonMultiMode(Button button){
        if(!randomShadeButtonsState.isMultiSelected()){
            handleClickWhenMultiDisabled(button);
            return;
        }
        if(isEnabled(button)){
           deselectRandomColorButton(button);
            return;
        }
        selectRandomColorButton(button);
    }


    private void handleClickWhenMultiDisabled(Button button){
        if(!isEnabled(button)){
            selectRandomColorButton(button);
        }
        randomShadeButtonsState.selectMulti();
        selectButtons(randomShadeButtonsState.getSelected());
    }


    public List<String> getSelectedRandomShadeKeys(){
        List<String> buttonKeys = new ArrayList<>();
        for(Button button: randomShadeButtonsState.getSelected()){
            buttonKeys.add(buttonReferenceStore.getKeyFrom(button));
        }
        return buttonKeys;
    }


    private boolean isEnabled(Button button){
       return "enabled".equals(button.getTag(enabledTag));
    }


    private void deselectRandomColorButton(Button button){
        int buttonColor = (int)button.getTag(R.string.tag_button_color);
        currentColorSelector.remove(buttonColor);
        button.setTag(enabledTag, "disabled");
        button.setText("");
        deselectButton(button);
        randomShadeButtonsState.deselect(button);
        if(!randomShadeButtonsState.isAnySelected()){
            switchBackToMainRandomColorButton();
        }
    }


    private void switchBackToMainRandomColorButton(){
        focusOn(randomColorButton);
        assignColorSelectorToPaintViewFrom(randomColorButton);
        onRandomColorButtonClick();
    }


    private void selectRandomColorButton(Button button){
        int buttonColor = (int)button.getTag(R.string.tag_button_color);
        currentColorSelector.add(buttonColor, getShadesFrom(button));
        button.setText(mainActivity.getResources().getString(R.string.color_checked));
        button.setTag(enabledTag, "enabled");
        randomShadeButtonsState.setSelected(button);
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
        return multiColorShades.get(color);
    }


}
