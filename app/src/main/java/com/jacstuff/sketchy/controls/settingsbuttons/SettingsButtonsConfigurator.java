package com.jacstuff.sketchy.controls.settingsbuttons;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.jacstuff.sketchy.BrushShape;
import com.jacstuff.sketchy.BrushStyle;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.PaintView;
import com.jacstuff.sketchy.PaintViewSingleton;
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
    private PaintView paintView;

    public SettingsButtonsConfigurator(MainActivity mainActivity){
        this.activity = mainActivity;
    }


    public void setupShapeAndStyleButtons(PaintView paintView){

        this.paintView = paintView;
        assignCategoryTagTo(styleButtonIds, ButtonCategory.STYLE_SELECTION);
        assignCategoryTagTo(shapeButtonIds, ButtonCategory.SHAPE_SELECTION);
        setupButtonListeners();
        setupPaintActionsMap();
        setupDefaultSelections();
    }


    private void setupPaintActionsMap(){
        paintActionsMap = new HashMap<>();
        paintActionsMap.put(R.id.brokenOutlineStyleButton,  () -> set(BrushStyle.BROKEN_OUTLINE));
        paintActionsMap.put(R.id.fillStyleButton,           () -> set(BrushStyle.FILL));
        paintActionsMap.put(R.id.outlineStyleButton,        () -> set(BrushStyle.OUTLINE));

        paintActionsMap.put(R.id.squareShapeButton, () -> set(BrushShape.SQUARE));
        paintActionsMap.put(R.id.circleShapeButton, () -> set(BrushShape.CIRCLE));
        paintActionsMap.put(R.id.lineShapeButton,   () -> set(BrushShape.LINE));
    }

    private void set(BrushShape brushShape){
        paintView.set(brushShape);
        updateBrushSize();
    }

    private void log(String msg){

        Log.i("SettingsButtonsCfg", msg);
    }
    private void set(BrushStyle brushStyle){
        log("entered se");
        paintView.set(brushStyle);
        updateBrushSize();
    }

    private void updateBrushSize(){
        paintView.setBrushSize(activity.getBrushSize());
    }


    public void handleButtonClick(int viewId){
        handleButtonClick(viewId, false);
    }


    private void handleButtonClick(int viewId, boolean isDefaultClick){
        ButtonCategory buttonCategory = getCategory(viewId);

        switch (buttonCategory){

            case SHAPE_SELECTION:
                switchSelection(viewId, shapeButtonIds);
                break;
            case STYLE_SELECTION:
                switchSelection(viewId, styleButtonIds);
                break;
            default:
                return;
        }
        executeProcedureFor(viewId);
        if(!isDefaultClick){
            saveSelectionToSingleton(viewId, buttonCategory);
        }
    }


    private ButtonCategory getCategory(int viewId){
        View view = findViewById(viewId);
        if(view == null ){
            return ButtonCategory.NULL;
        }
        return (ButtonCategory)view.getTag(R.string.tag_button_category);
    }


    private void executeProcedureFor(int viewId){
        Procedure procedure = paintActionsMap.get(viewId);
        if(procedure != null){
            procedure.execute();
        }
    }


    private void saveSelectionToSingleton(int viewId, ButtonCategory buttonCategory ){
        PaintViewSingleton pvs = PaintViewSingleton.getInstance();
        pvs.saveSetting(viewId, buttonCategory);
    }



    private void assignCategoryTagTo(List<Integer> buttonIds, ButtonCategory buttonCategory){
        for(int buttonId : buttonIds){
            assignCategoryTagToButtonWith(buttonId, buttonCategory);
        }
    }


    private void assignCategoryTagToButtonWith(int id, ButtonCategory buttonCategory){
        findViewById(id).setTag(R.string.tag_button_category, buttonCategory);
    }


    public void clickOnView(int id){
        handleButtonClick(id);
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
        handleButtonClick(R.id.circleShapeButton, true);
        handleButtonClick(R.id.fillStyleButton, true);
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
