package com.jacstuff.sketchy.settings;

import android.view.View;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.MainViewModel;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.colorbuttons.ButtonReferenceStore;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonClickHandler;
import com.jacstuff.sketchy.paintview.PaintView;

import java.util.HashMap;


public class ViewModelHelper {

    private ColorButtonClickHandler buttonClickHandler;
    private PaintView paintView;
    private MainViewModel viewModel;
    private ButtonReferenceStore buttonReferenceStore;
    private MainActivity mainActivity;


    public ViewModelHelper(MainViewModel viewModel,
                           MainActivity mainActivity) {

        this.viewModel = viewModel;
        this.mainActivity = mainActivity;
        this.buttonReferenceStore = mainActivity.getButtonReferenceStore();
    }


    public void init(ColorButtonClickHandler buttonClickHandler,
                     PaintView paintView){
        this.buttonClickHandler = buttonClickHandler;
        this.paintView = paintView;
        initViewModelSettings();
    }


    public void onPause(){
        viewModel.bitmapHistory = paintView.getBitmapHistory().getAll();
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

        reinstateWidgetSettings();
    }


    private void initViewModelSettings(){
        if(viewModel.settingsButtonsClickMap == null){
            viewModel.settingsButtonsClickMap = new HashMap<>();
        }
    }


    private void reinstateWidgetSettings(){


    }


    private void retrieveBitmapHistory(){
        if(viewModel.bitmapHistory != null) {
            paintView.getBitmapHistory().setAll(viewModel.bitmapHistory);
            paintView.useMostRecentHistory();
        }
    }


    private void retrieveColorAndShade(){
        int mostRecentColorButtonId = buttonReferenceStore.getIdFor(viewModel.mostRecentColorButtonKey);
        buttonClickHandler.handleColorButtonClicks(mostRecentColorButtonId);
        if(viewModel.isMostRecentClickAShade){
            int mostRecentShadeButtonId = buttonReferenceStore.getIdFor(viewModel.mostRecentShadeButtonKey);
            buttonClickHandler.handleColorButtonClicks(mostRecentShadeButtonId);
        }
        if(viewModel.selectedShadeButtonKeys != null) {
            for (String randomShadeButtonKey : viewModel.selectedShadeButtonKeys) {
                int buttonId = buttonReferenceStore.getIdFor(randomShadeButtonKey);
                buttonClickHandler.handleColorButtonClicks(buttonId);
            }
        }
    }


    private void retrieveRecentButtonSettings(){
        for(ButtonCategory buttonCategory : viewModel.settingsButtonsClickMap.keySet()){

            Integer id = viewModel.settingsButtonsClickMap.get(buttonCategory);
            clickOn(id);
        }
        mainActivity.getSettingsPopup().dismiss();
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
