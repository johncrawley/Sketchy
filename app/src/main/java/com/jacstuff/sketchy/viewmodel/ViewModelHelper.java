package com.jacstuff.sketchy.viewmodel;

import android.view.View;
import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.controls.colorbuttons.ButtonReferenceStore;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonClickHandler;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.gradient.GradientHelper;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

import java.util.HashMap;


public class ViewModelHelper {

    private ColorButtonClickHandler buttonClickHandler;
    private PaintView paintView;
    private final MainViewModel viewModel;
    private final ButtonReferenceStore buttonReferenceStore;
    private final MainActivity mainActivity;
    private final ButtonUtils buttonUtils;
    private PaintHelperManager paintHelperManager;


    public ViewModelHelper(MainViewModel viewModel,
                           MainActivity mainActivity) {

        this.viewModel = viewModel;
        this.mainActivity = mainActivity;
        this.buttonReferenceStore = mainActivity.getButtonReferenceStore();
        this.buttonUtils = new ButtonUtils(mainActivity);
        initControls();
    }


    public void init(ColorButtonClickHandler buttonClickHandler,
                     PaintView paintView){
        this.buttonClickHandler = buttonClickHandler;
        this.paintView = paintView;
        initViewModelSettings();
    }


    private void initControls(){
        if(viewModel.getColorSequenceControls() == null){
            viewModel.setColorSequenceControls(new ColorSequenceControls());
        }
    }


    public void setPaintHelperManager(PaintHelperManager paintHelperManager){
        this.paintHelperManager = paintHelperManager;
    }


    public void onPause(){
        viewModel.bitmapHistoryItems = paintView.getBitmapHistory().getAll();
        viewModel.mostRecentColorButtonKey = buttonClickHandler.getMostRecentColorButtonKey();
        viewModel.mostRecentShadeButtonKey = buttonClickHandler.getMostRecentShadeButtonKey();
        viewModel.isMostRecentClickAShade = buttonClickHandler.isMostRecentClickAShade();
        viewModel.selectedShadeButtonKeys = buttonClickHandler.getSelectedRandomShadeKeys();
    }


    public void saveRecentClick(ButtonCategory buttonCategory, int viewId){
        viewModel.settingsButtonsClickMap.put(buttonCategory, viewId);
    }


    public void onResume() {
        retrieveBitmapHistory();
        retrieveRecentButtonSettings();
        retrieveColorAndShade();
        restoreSeekBarSettings();
        setDefaultPropertiesFromResources();
        mainActivity.loadStoredImage();
        viewModel.isFirstExecution = false;
    }


    private void setDefaultPropertiesFromResources(){
        if(!viewModel.isFirstExecution){
            return;
        }
        viewModel.textBrushText = mainActivity.getString(R.string.text_edit_text_default);
        viewModel.gradientMaxLength = mainActivity.getResources().getInteger(R.integer.brush_size_default);
    }


    private void initViewModelSettings(){
        if(viewModel.settingsButtonsClickMap == null){
            viewModel.settingsButtonsClickMap = new HashMap<>();
        }
    }


    private void retrieveBitmapHistory(){
        if(viewModel.bitmapHistoryItems != null) {
            paintView.getBitmapHistory().setAll(viewModel.bitmapHistoryItems);
            paintView.assignMostRecentBitmap();
        }
    }


    private void retrieveColorAndShade(){
        int mostRecentColorButtonId = buttonReferenceStore.getIdFor(viewModel.mostRecentColorButtonKey);
        buttonClickHandler.onClick(mostRecentColorButtonId);
        if(viewModel.isMostRecentClickAShade){
            int mostRecentShadeButtonId = buttonReferenceStore.getIdFor(viewModel.mostRecentShadeButtonKey);
            buttonClickHandler.onClick(mostRecentShadeButtonId);
        }
        if(viewModel.selectedShadeButtonKeys != null) {
            for (String randomShadeButtonKey : viewModel.selectedShadeButtonKeys) {
                int buttonId = buttonReferenceStore.getIdFor(randomShadeButtonKey);
                buttonClickHandler.onClick(buttonId);
            }
        }
    }


    private void retrieveRecentButtonSettings(){

        for(ButtonCategory buttonCategory : viewModel.settingsButtonsClickMap.keySet()){
            Integer buttonId = viewModel.settingsButtonsClickMap.get(buttonCategory);
            clickOn(buttonId);
        }
        if(viewModel.useSeekBarAngle){
            setAngleBasedOnSeekBar();
            deselectCurrentlySelectedAngleButton();
        }
        mainActivity.getSettingsPopup().dismiss();
    }


    private void restoreSeekBarSettings(){
        if(!viewModel.isFirstExecution) {
            if(viewModel.gradient != 0) {
                GradientHelper gradientHelper = paintHelperManager.getGradientHelper();
                gradientHelper.setGradientLength(viewModel.gradient);
            }
        }
    }


    private void setAngleBasedOnSeekBar(){
        int angle = viewModel.angle;
        paintHelperManager.getAngleHelper().setAngle(AngleType.OTHER);
        paintHelperManager.getAngleHelper().setAngle(angle);
        String buttonText = "" + angle + mainActivity.getString(R.string.degrees_symbol);
        Button angleButton =  mainActivity.findViewById(R.id.angleButton);
        angleButton.setText(buttonText);
    }


    private void deselectCurrentlySelectedAngleButton(){
        Integer selectedPresetButtonId = viewModel.settingsButtonsClickMap.get(ButtonCategory.ANGLE);
        if(selectedPresetButtonId == null){
            return;
        }
        buttonUtils.deselectButton(selectedPresetButtonId);
    }


    private void clickOn(Integer id){
        if(id == null){
            return;
        }
        View view = mainActivity.findViewById(id);
        if(view != null){
            view.callOnClick();
        }
    }

}
