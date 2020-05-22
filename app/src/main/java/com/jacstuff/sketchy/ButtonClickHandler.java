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

    private final int NO_TAG_FOUND = -1;
    private PaintView paintView;
    private Button previouslySelectedShadeButton, previouslySelectedColorButton;
    private Map<String, Color> colors;
    private ButtonLayoutParams buttonLayoutParams;
    private Map<String, LinearLayout> shadesLayoutMap;

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


    void setShadesLayoutMap(Map<String, LinearLayout> shadesLayoutMap){
        this.shadesLayoutMap = shadesLayoutMap;
    }


    void setMultiColorShades(Map<Color, List<Color>> multiColorShades){
        this.multiColorShades = multiColorShades;
    }



    void handleColorButtonClicks(View view){
        int tag = getButtonTypeTag(view);
        if(tag == NO_TAG_FOUND){
            return;
        }
        Button button = (Button)view;
        if (tag == R.string.button_type_color) {
            handleMainColorButtonClick(button);
        }
        else if(tag ==  R.string.button_type_shade){
            handleShadeButtonClick(button);
        }
        else if(tag == R.string.button_type_multi_color){
            handleMultiButtonClick(button);
        }
        else if(tag == R.string.button_type_multi_shade){
            handleMultiShadeButtonClick(button);
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



    private int getButtonTypeTag(View view){
        Object tagObj = view.getTag(R.string.tag_button_type);
        if(tagObj == null) {
            return NO_TAG_FOUND;
        }
        return (int)tagObj;
    }




    private void handleMultiButtonClick(Button button){
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
        String key = (String)button.getTag();
        LinearLayout shadeLayout = shadesLayoutMap.get(key);
        if (shadeLayout != null) {
            shadesScrollView.removeAllViews();
            shadesScrollView.addView(shadeLayout);
        }
    }


    private void handleShadeButtonClick(Button button){
        setColorAndUpdateButtons(button);
        previouslySelectedShadeButton = button;
    }



    private void deselectButton(Button button){
        if(button == null){
            return;
        }
        button.setLayoutParams(buttonLayoutParams.getUnselected());
    }


    private void handleMultiShadeButtonClick(Button button){
        deselectPreviousButtons();
        previouslySelectedColorButton = button;
        selectButton(button);
        assignShadeLayoutFrom(button);
        Color color = (Color)button.getTag(R.string.tag_button_color);
        paintView.setMultiColor(multiColorShades.get(color));
    }


    private void handleMainColorButtonClick(Button button){
        paintView.setSingleColorMode();
        setColorAndUpdateButtons(button);
        previouslySelectedColorButton = button;
        assignShadeLayoutFrom(button);
    }


}
