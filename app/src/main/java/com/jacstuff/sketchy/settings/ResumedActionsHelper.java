package com.jacstuff.sketchy.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.MainViewModel;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.colorbuttons.ButtonReferenceStore;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonClickHandler;
import com.jacstuff.sketchy.controls.settingsbuttons.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;


public class ResumedActionsHelper {

    private ColorButtonClickHandler buttonClickHandler;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private PaintView paintView;
    private MainViewModel viewModel;
    private ButtonReferenceStore buttonReferenceStore;

    public ResumedActionsHelper(MainViewModel viewModel,
                                MainActivity mainActivity,
                                ColorButtonClickHandler buttonClickHandler,
                                SettingsButtonsConfigurator settingsButtonsConfigurator,
                                PaintView paintView) {

        this.viewModel = viewModel;
        this.buttonReferenceStore = mainActivity.getButtonReferenceStore();
        this.buttonClickHandler = buttonClickHandler;
        this.settingsButtonsConfigurator = settingsButtonsConfigurator;
        this.paintView = paintView;
    }


    public void onPause(){
        viewModel.bitmapHistory = paintView.getBitmapHistory().getAll();
        viewModel.mostRecentColorButtonKey = buttonClickHandler.getMostRecentColorButtonKey();
        viewModel.mostRecentShadeButtonKey = buttonClickHandler.getMostRecentShadeButtonKey();
        viewModel.isMostRecentClickAShade = buttonClickHandler.isMostRecentClickAShade();
        viewModel.selectedShadeButtonKeys = buttonClickHandler.getSelectedRandomShadeKeys();
    }


    public void onResume() {

        if(viewModel.bitmapHistory != null) {
            paintView.getBitmapHistory().setAll(viewModel.bitmapHistory);
            paintView.useMostRecentHistory();
        }

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



    private void selectRecentShapeAndStyle(PaintViewSingleton pvs) {
        for(ButtonCategory buttonCategory : ButtonCategory.values()){
            if(buttonCategory == ButtonCategory.COLOR_SELECTION){
                continue;
            }
            settingsButtonsConfigurator.clickOnView(pvs.getMostRecentSettingButtonId(buttonCategory));
        }
    }

}
