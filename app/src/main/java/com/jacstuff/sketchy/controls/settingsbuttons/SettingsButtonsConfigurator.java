package com.jacstuff.sketchy.controls.settingsbuttons;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.PaintView;
import com.jacstuff.sketchy.PaintViewSingleton;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SettingsButtonsConfigurator {

    private MainActivity activity;

    private Set<Integer> styleButtonIds;
    private Set<Integer> shapeButtonIds;
    private Map<Integer, Procedure> shapeActionsMap, styleActionsMap;
    private PaintView paintView;

    public SettingsButtonsConfigurator(MainActivity mainActivity){
        this.activity = mainActivity;
    }


    public void setupShapeAndStyleButtons(PaintView paintView){

        this.paintView = paintView;
        setupActionsMaps();
        assignCategoryTagTo(styleButtonIds, ButtonCategory.STYLE_SELECTION);
        assignCategoryTagTo(shapeButtonIds, ButtonCategory.SHAPE_SELECTION);
        setupButtonListeners();
        setupDefaultSelections();
    }


    private void setupActionsMaps(){
        styleActionsMap = new HashMap<>();
        styleActionsMap.put(R.id.brokenOutlineStyleButton,  () -> set(BrushStyle.BROKEN_OUTLINE));
        styleActionsMap.put(R.id.fillStyleButton,           () -> set(BrushStyle.FILL));
        styleActionsMap.put(R.id.outlineStyleButton,        () -> set(BrushStyle.OUTLINE));
        styleActionsMap.put(R.id.thickOutlineStyleButton,   () -> set(BrushStyle.THICK_OUTLINE));
        styleButtonIds = styleActionsMap.keySet();

        shapeActionsMap = new HashMap<>();
        shapeActionsMap.put(R.id.squareShapeButton, () -> set(BrushShape.SQUARE));
        shapeActionsMap.put(R.id.circleShapeButton, () -> set(BrushShape.CIRCLE));
        shapeActionsMap.put(R.id.lineShapeButton,   () -> set(BrushShape.LINE));
        shapeActionsMap.put(R.id.roundedRectangleShapeButton, () -> set(BrushShape.ROUNDED_RECTANGLE));
        shapeActionsMap.put(R.id.triangleShapeButton, () -> set(BrushShape.TRIANGLE));
        shapeButtonIds = shapeActionsMap.keySet();
    }


    private void set(BrushShape brushShape){
        paintView.set(brushShape);
        updateBrushSize();
    }


    private void set(BrushStyle brushStyle){
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
        executeProcedureFor(viewId, shapeActionsMap);
        executeProcedureFor(viewId, styleActionsMap);

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


    private void executeProcedureFor(int viewId, Map<Integer, Procedure> procedureMap){
        Procedure procedure = procedureMap.get(viewId);
        if(procedure != null){
            procedure.execute();
        }
    }


    private void saveSelectionToSingleton(int viewId, ButtonCategory buttonCategory ){
        PaintViewSingleton pvs = PaintViewSingleton.getInstance();
        pvs.saveSetting(viewId, buttonCategory);
    }



    private void assignCategoryTagTo(Set<Integer> buttonIds, ButtonCategory buttonCategory){
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


    private void setOnClickListenerFor(Set<Integer> ids){
        for(int id: ids){
           findViewById(id).setOnClickListener(activity);
        }
    }

    private void setupDefaultSelections(){
        handleButtonClick(R.id.circleShapeButton, true);
        handleButtonClick(R.id.fillStyleButton, true);
    }


    private void switchSelection(int viewId, Set<Integer> buttons){
        for(int buttonId : buttons){
            if(viewId == buttonId){
                switchSelectionToButton(buttonId, buttons);
                return;
            }
        }
    }


    private void switchSelectionToButton(int buttonId, Set<Integer> buttonList){
        selectButton(buttonId);
        deselectOtherButtons(buttonId, buttonList);
    }


    private void selectButton(int buttonId){
        ImageButton button = findViewById(buttonId);
        button.setSelected(true);
        button.setBackgroundColor(activity.getResources().getColor(R.color.selected_button_border, null));
    }


    private void deselectOtherButtons(int selectedButtonId, Set<Integer> buttonList){
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
