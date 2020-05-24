package com.jacstuff.sketchy.controls.settingsbuttons;

import android.graphics.Color;
import android.widget.ImageButton;

import com.jacstuff.sketchy.BrushShape;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.PaintView;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsButtonsConfigurator {

    private MainActivity activity;

    private List<Integer> styleButtonIds = Arrays.asList(R.id.brokenOutlineStyleButton, R.id.fillStyleButton, R.id.outlineStyleButton);
    private List<Integer> shapeButtonIds = Arrays.asList(R.id.squareShapeButton, R.id.circleShapeButton, R.id.lineShapeButton);
    private Map<Integer, Procedure> paintActionsMap;

    public SettingsButtonsConfigurator(MainActivity mainActivity){
        this.activity = mainActivity;
    }


    public void setupShapeAndStyleButtons(PaintView paintView){

        assignCategoryTagTo(styleButtonIds, ButtonCategory.STYLE_SELECTION);
        assignCategoryTagTo(shapeButtonIds, ButtonCategory.SHAPE_SELECTION);
        setupButtonListeners();
        setupPaintActionsMap(paintView);
        setupDefaultSelections();
    }


    private void setupPaintActionsMap(PaintView paintView){
        paintActionsMap = new HashMap<>();
        paintActionsMap.put(R.id.brokenOutlineStyleButton, paintView::setStyleToBrokenOutline);
        paintActionsMap.put(R.id.fillStyleButton,  paintView::setStyleToFill);
        paintActionsMap.put(R.id.outlineStyleButton,paintView::setStyleToOutline);
        paintActionsMap.put(R.id.squareShapeButton, () -> paintView.setBrushShape(BrushShape.SQUARE));
        paintActionsMap.put(R.id.circleShapeButton, () -> paintView.setBrushShape(BrushShape.CIRCLE));
        paintActionsMap.put(R.id.lineShapeButton, () -> paintView.setBrushShape(BrushShape.LINE));
    }


    public void handleButtonClick(int viewId){

        Procedure procedure = paintActionsMap.get(viewId);
        if(procedure != null){
            procedure.execute();
        }
        switchSelection(viewId, styleButtonIds);
        switchSelection(viewId, shapeButtonIds);
    }

    private void assignCategoryTagTo(List<Integer> buttonIds, ButtonCategory buttonCategory){
        for(int buttonId : buttonIds){
            assignCategoryTagToButtonWith(buttonId, buttonCategory);
        }
    }

    private void assignCategoryTagToButtonWith(int id, ButtonCategory buttonCategory){
        findViewById(id).setTag(R.string.tag_button_category, buttonCategory);
    }


    private void setupButtonListeners(){
        setOnClickListenerFor(shapeButtonIds);
        setOnClickListenerFor(styleButtonIds);
    }


    private void setOnClickListenerFor(List<Integer> ids){
        for(int id: ids){
           findViewById(id).setOnClickListener(activity);
        }
    }

    private void setupDefaultSelections(){
        handleButtonClick(R.id.circleShapeButton);
        handleButtonClick(R.id.fillStyleButton);
    }


    private void switchSelection(int viewId, List<Integer> buttons){
        for(int buttonId : buttons){
            if(viewId == buttonId){
                switchSelectionToButton(buttonId, buttons);
                break;
            }
        }
    }


    private void switchSelectionToButton(int buttonId, List<Integer> buttonList){
        selectButton(buttonId);
        deselectOtherButtons(buttonId, buttonList);
    }


    private void selectButton(int buttonId){
        ImageButton button = findViewById(buttonId);
        button.setSelected(true);
        button.setBackgroundColor(Color.DKGRAY);
    }


    private void deselectOtherButtons(int selectedButtonId, List<Integer> buttonList){
        for(int buttonId : buttonList){
            if(buttonId == selectedButtonId){
                continue;
            }
            deselectButton(buttonId);
        }
    }


    private void deselectButton(int buttonId){
        ImageButton button = findViewById(buttonId);
        button.setSelected(false);
        button.setBackgroundColor(Color.LTGRAY);
    }


    private ImageButton findViewById(int id){
        return (ImageButton)activity.findViewById(id);
    }

}
