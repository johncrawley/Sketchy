package com.jacstuff.sketchy;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ButtonClickHandler {

    private PaintView paintView;
    private Button previouslySelectedShadeButton, previouslySelectedColorButton;
    private Map<String, Color> colors;
    private ButtonLayoutParams buttonLayoutParams;
    private Map<String, LinearLayout> shadeLayoutsMap;
    private boolean isMostRecentClickAShade = false; //for use when selecting a button after rotate/resume
    private HorizontalScrollView shadesScrollView;
    private Map<Color, List<Color>> multiColorShades = new HashMap<>();


    ButtonClickHandler(PaintView paintView,ButtonLayoutParams buttonLayoutParams, HorizontalScrollView shadesScrollView ){
        this.paintView = paintView;
        this.buttonLayoutParams = buttonLayoutParams;
        this.shadesScrollView = shadesScrollView;
    }


    void setColors(Map<String, Color> colors){
        this.colors = colors;
    }


    String getMostRecentButtonKey(){
        return getKey(previouslySelectedColorButton);
    }


    private String getKey(Button button){
        if(button == null){
            return "";
        }
        return (String)button.getTag(R.string.tag_button_key);
    }


    String getMostRecentShadeKey(){
        return getKey(previouslySelectedShadeButton);
    }


    boolean isMostRecentClickAShade(){
        return isMostRecentClickAShade;
    }


    void setShadeLayoutsMap(Map<String, LinearLayout> shadeLayoutsMap){
        this.shadeLayoutsMap = shadeLayoutsMap;
    }


    void setMultiColorShades(Map<Color, List<Color>> multiColorShades){
        this.multiColorShades = multiColorShades;
    }


    void handleColorButtonClicks(View view){
        if(ButtonCategory.COLOR_SELECTION_BUTTON != view.getTag(R.string.tag_button_category)){
            return;
        }
        Button button = (Button)view;
        ButtonType type = (ButtonType)view.getTag(R.string.tag_button_type);
        switch(type){
            case COLOR:
                onMainColorButtonClick(button);
                break;

            case MULTICOLOR:
                onMultiButtonClick(button);
                break;

            case SHADE:
                onShadeButtonClick(button);
                break;

            case MULTISHADE:
                onMultiShadeButtonClick(button);
                break;
        }
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
        paintView.setCurrentColor(color.toArgb());
    }


    private Color getColorOf(Button button){
        return (Color)button.getTag(R.string.tag_button_color);
    }


    private void onMultiButtonClick(Button button){
        isMostRecentClickAShade = false;
        deselectPreviousButtons();
        previouslySelectedColorButton = button;
        selectButton(button);
        List<Color> colorList = new ArrayList<>(colors.values());
        assignShadeLayoutFrom(button);
        paintView.setMultiColor(colorList);
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


    private void onShadeButtonClick(Button button){
        isMostRecentClickAShade = true;
        setColorAndUpdateButtons(button);
        previouslySelectedShadeButton = button;
    }


    private void deselectButton(Button button){
        if(button == null){
            return;
        }
        button.setLayoutParams(buttonLayoutParams.getUnselected());
    }


    private void onMultiShadeButtonClick(Button button){
        isMostRecentClickAShade = true;
        deselectPreviousButtons();
        previouslySelectedShadeButton = button;
        selectButton(button);
        assignShadeLayoutFrom(button);
        Color color = (Color)button.getTag(R.string.tag_button_color);
        paintView.setMultiColor(multiColorShades.get(color));
    }


    private void onMainColorButtonClick(Button button){
        isMostRecentClickAShade = false;
        paintView.setSingleColorMode();
        setColorAndUpdateButtons(button);
        previouslySelectedColorButton = button;
        assignShadeLayoutFrom(button);
    }


}
