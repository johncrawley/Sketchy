package com.jacstuff.sketchy.controls.colorbuttons;

import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.MainActivity;
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
    private HorizontalScrollView shadesScrollView;
    private Map<Integer, List<Integer>> multiColorShades = new HashMap<>();
    private Map<ButtonType, ColorSelector> colorSelectors;
    private MainActivity mainActivity;
    private ColorSelector currentColorSelector;
    private RandomShadeButtonsState randomShadeButtonsState;



    public ColorButtonClickHandler(MainActivity mainActivity, PaintView paintView, ButtonLayoutParams buttonLayoutParams, HorizontalScrollView shadesScrollView ){
        this.mainActivity = mainActivity;
        this.paintView = paintView;
        this.buttonLayoutParams = buttonLayoutParams;
        this.shadesScrollView = shadesScrollView;
        randomShadeButtonsState = new RandomShadeButtonsState();
        setupColorSelectors();
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


    public String getMostRecentButtonKey(){
        return getKey(previouslySelectedColorButton);
    }


    private String getKey(Button button){
        return button == null ? "" : (String)button.getTag(R.string.tag_button_key);
    }


    public String getMostRecentShadeKey(){
        return getKey(previouslySelectedShadeButton);
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


    public void handleColorButtonClicks(View view){

        if(view == null || ButtonCategory.COLOR_SELECTION != view.getTag(R.string.tag_button_category)){
            return;
        }
        Button button = (Button)view;
        ButtonType buttonType = (ButtonType)view.getTag(R.string.tag_button_type);
        button.getParent().requestChildFocus(button, button);
        currentColorSelector = colorSelectors.get(buttonType);
        paintView.setColorSelector(currentColorSelector);
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
                onRandomColorButtonClick(button);
                break;
            case RANDOM_SHADE:
                clickRandomShadeButtonMultiMode(button);
              //  onRandomShadeButtonClick(button);
        }
        previouslySelectedButton = button;
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


    private List<Integer> getShadesFrom(Button button){
        int color = (int)button.getTag(R.string.tag_button_color);
        return multiColorShades.get(color);
    }


    private void onRandomColorButtonClick(Button button){
        deselectPreviousButtons();
        currentColorSelector.set(colors);
        previouslySelectedColorButton = button;
        paintView.setColorSelector(currentColorSelector);
        selectButton(button);
        assignShadeLayoutFrom(button);
        isMostRecentClickAShade = false;
        deselectButtons(randomShadeButtonsState.getSelected());
        randomShadeButtonsState.deselectMulti();
    }


    private void onRandomShadeButtonClick(Button button){
        paintView.setColorSelector(currentColorSelector);
        currentColorSelector.set(getShadesFrom(button));
        selectButtonAndSetRecent(button);
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

    private  int enabledTag = R.string.multi_random_button_checked_tag;

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
            shadesScrollView.removeAllViews();
            shadesScrollView.addView(shadeLayout);
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

}
