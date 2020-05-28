package com.jacstuff.sketchy.controls.colorbuttons;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.PaintView;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.multicolor.MulticolorSelector;
import com.jacstuff.sketchy.multicolor.RandomColorSelector;
import com.jacstuff.sketchy.multicolor.SingleColorSelector;
import com.jacstuff.sketchy.multicolor.pattern.EvenNumbersPattern;
import com.jacstuff.sketchy.multicolor.pattern.FirstToLastPattern;
import com.jacstuff.sketchy.multicolor.pattern.FourColoursStartingAt;
import com.jacstuff.sketchy.multicolor.pattern.MiddleToEndPattern;
import com.jacstuff.sketchy.multicolor.pattern.MiddleToStartPattern;
import com.jacstuff.sketchy.multicolor.pattern.MulticolorPattern;
import com.jacstuff.sketchy.multicolor.pattern.OddNumbersPattern;
import com.jacstuff.sketchy.multicolor.pattern.ReversiblePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ButtonClickHandler {

    private PaintView paintView;
    private Button previouslySelectedShadeButton, previouslySelectedColorButton, previouslySelectedButton;
    private Map<String, Color> colorsMap;
    private List<Color> colors;
    private ButtonLayoutParams buttonLayoutParams;
    private Map<String, LinearLayout> shadeLayoutsMap;
    private boolean isMostRecentClickAShade = false; //for use when selecting a button after rotate/resume
    private HorizontalScrollView shadesScrollView;
    private Map<Color, List<Color>> multiColorShades = new HashMap<>();
    private Map<ButtonType, ColorSelector> colorSelectors;


    public ButtonClickHandler(PaintView paintView,ButtonLayoutParams buttonLayoutParams, HorizontalScrollView shadesScrollView ){
        this.paintView = paintView;
        this.buttonLayoutParams = buttonLayoutParams;
        this.shadesScrollView = shadesScrollView;
        setupColorSelectors();
    }


    private void setupColorSelectors(){
        ColorSelector singleSelector = new SingleColorSelector();

        List<MulticolorPattern> colorPatterns = new ArrayList<>();

        colorPatterns.add(new FirstToLastPattern());
        colorPatterns.add(new ReversiblePattern());
        colorPatterns.add(new MiddleToEndPattern());
        colorPatterns.add(new MiddleToStartPattern());
        colorPatterns.add(new OddNumbersPattern());
        colorPatterns.add(new EvenNumbersPattern());
        colorPatterns.add(new FourColoursStartingAt(0));
        colorPatterns.add(new FourColoursStartingAt(1));
        colorPatterns.add(new FourColoursStartingAt(2));
        colorPatterns.add(new FourColoursStartingAt(3));
        colorPatterns.add(new FourColoursStartingAt(4));


        List<MulticolorPattern> shadePatterns = new ArrayList<>();

        shadePatterns.add(new FirstToLastPattern());
        shadePatterns.add(new ReversiblePattern());
        shadePatterns.add(new MiddleToEndPattern());
        shadePatterns.add(new MiddleToStartPattern());
        shadePatterns.add(new OddNumbersPattern());
        shadePatterns.add(new FourColoursStartingAt(0));

        ColorSelector randomSelector = new RandomColorSelector();
        colorSelectors = new HashMap<>();
        colorSelectors.put(ButtonType.COLOR, singleSelector);
        colorSelectors.put(ButtonType.SHADE, singleSelector);
        colorSelectors.put(ButtonType.MULTICOLOR, new MulticolorSelector(colorPatterns));
        colorSelectors.put(ButtonType.MULTISHADE, new MulticolorSelector(shadePatterns));
        colorSelectors.put(ButtonType.RANDOM_COLOR, randomSelector);
        colorSelectors.put(ButtonType.RANDOM_SHADE, randomSelector);
    }


    public void setColorsMap(Map<String, Color> colorsMap){
        this.colorsMap = colorsMap;
        colors = new ArrayList<>(colorsMap.values());
    }


    public String getMostRecentButtonKey(){
        return getKey(previouslySelectedColorButton);
    }


    private String getKey(Button button){
        if(button == null){
            return "";
        }
        return (String)button.getTag(R.string.tag_button_key);
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


   public void setMultiColorShades(Map<Color, List<Color>> multiColorShades){
        this.multiColorShades = multiColorShades;
    }

    private void log(String msg){

        Log.i("ButtonClickHandler", msg);
    }

    public void handleColorButtonClicks(View view){
        if(ButtonCategory.COLOR_SELECTION != view.getTag(R.string.tag_button_category)){
            return;
        }
        Button button = (Button)view;
        ButtonType type = (ButtonType)view.getTag(R.string.tag_button_type);
        log("Entered handleColorButtonClicks():  type: "  + type.toString());
        currentColorSelector = colorSelectors.get(type);
        switch(type){
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
                onRandomShadeButtonClick(button);
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


    private void onMultiShadeButtonClick(Button button){
        deselectPreviousButtons();
        assignMultiSelector(button, getShadesFrom(button));
        selectButton(button);
        previouslySelectedShadeButton = button;
        isMostRecentClickAShade = true;
    }


    private List<Color> getShadesFrom(Button button){
        Color color = (Color)button.getTag(R.string.tag_button_color);
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
    }

    private void onRandomShadeButtonClick(Button button){
        log("Entered onRandomShadeButtonClick()");
        deselectPreviousButtons();
        currentColorSelector.set(getShadesFrom(button));
        previouslySelectedColorButton = button;
        selectButton(button);
        isMostRecentClickAShade = true;
    }


    private void setColorAndUpdateButtons(Button button){
        setPaintColorFrom(button);
        deselectPreviousButtons();
        selectButton(button);
    }


    private void setPaintColorFrom(Button button){
        Color color = getColorOf(button);
        if(color == null){
            return;
        }
        currentColorSelector.set(color);
    }


    private Color getColorOf(Button button){
        return (Color)button.getTag(R.string.tag_button_color);
    }

    private ColorSelector currentColorSelector;


    private void assignMultiSelector(Button button, List<Color> colorList){
        log("Entered assignMultiColorSelector(), currentColorSelector : " + currentColorSelector.getClass().getName());
        if(button == previouslySelectedButton){
            log("button is the same as previously selected button, selecting the next pattern in the same colorSelectorrr");
            currentColorSelector.nextPattern();
        }else{
            log("button clicked is not the same as previous button, color selector should already be changed, reseting it and setting paint");
            currentColorSelector.reset();
            paintView.setColorSelector(currentColorSelector);
        }
        currentColorSelector.set(colorList);
    }


    private void selectButton(Button button){
        button.setLayoutParams(buttonLayoutParams.getSelected());
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

}
