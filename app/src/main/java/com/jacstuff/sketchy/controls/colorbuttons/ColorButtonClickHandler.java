package com.jacstuff.sketchy.controls.colorbuttons;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.multicolor.ShadesStore;
import com.jacstuff.sketchy.paintview.helpers.ColorHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonLayoutParams;
import com.jacstuff.sketchy.multicolor.RandomMultiColorSelector;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.multicolor.ColorSequenceSelector;
import com.jacstuff.sketchy.multicolor.RandomColorSelector;
import com.jacstuff.sketchy.multicolor.SingleColorSelector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorButtonClickHandler {

    private final PaintView paintView;
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
    private final RandomShadeButtonsState randomShadeButtonsState;
    private final int enabledTag = R.string.multi_random_button_checked_tag;
    private Button randomColorButton;
    private final ButtonReferenceStore buttonReferenceStore;
    private final MainViewModel mainViewModel;
    private final ColorHelper colorHelper;


    public ColorButtonClickHandler(MainActivity mainActivity, PaintView paintView, ButtonLayoutParams buttonLayoutParams){
        this.mainActivity = mainActivity;
        this.buttonReferenceStore = mainActivity.getButtonReferenceStore();
        this.mainViewModel = mainActivity.getViewModel();
        this.paintView = paintView;
        this.buttonLayoutParams = buttonLayoutParams;
        this.shadesLayout = mainActivity.findViewById(R.id.shadesButtonGroup);
        this.colorHelper = mainActivity.getPaintHelperManager().getColorHelper();

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

        ColorSelector randomMultiColor = new RandomMultiColorSelector();
        colorSelectors = new HashMap<>();
        colorSelectors.put(ButtonType.COLOR, singleSelector);
        colorSelectors.put(ButtonType.SHADE, singleSelector);
        colorSelectors.put(ButtonType.MULTICOLOR, new ColorSequenceSelector(colorPatternsFactory.createColorPatterns()));
        colorSelectors.put(ButtonType.MULTISHADE, new ColorSequenceSelector(colorPatternsFactory.createShadePatterns()));
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


    public void setShadesStore(ShadesStore shadesStore){
        this.shadesStore = shadesStore;
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
        isMostRecentClickAShade = false;
    }


    private void onRandomColorButtonClick(){
        deselectPreviousButtons();
        currentColorSelector.set(colors);
        if(randomColorButton == null){
            return;
        }
        previouslySelectedColorButton = randomColorButton;
        colorHelper.setColorSelector(currentColorSelector);
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
            colorHelper.setColorSelector(currentColorSelector);
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
        //button.setText(mainActivity.getResources().getString(R.string.color_checked));
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
        return shadesStore.getShadesFor(color);
    }


}