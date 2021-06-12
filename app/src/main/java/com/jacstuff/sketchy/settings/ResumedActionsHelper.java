package com.jacstuff.sketchy.settings;

import android.graphics.Bitmap;
import android.widget.Button;

import com.jacstuff.sketchy.MainViewModel;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonClickHandler;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonLayoutPopulator;
import com.jacstuff.sketchy.controls.settingsbuttons.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.model.TextControlsDto;
import com.jacstuff.sketchy.paintview.BitmapHistory;
import com.jacstuff.sketchy.paintview.PaintView;

import java.util.ArrayDeque;

public class ResumedActionsHelper {

    private ColorButtonClickHandler buttonClickHandler;
    private ColorButtonLayoutPopulator layoutPopulator;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private PaintView paintView;
    private MainViewModel viewModel;

    public ResumedActionsHelper(MainViewModel viewModel,
                                ColorButtonClickHandler buttonClickHandler,
                                ColorButtonLayoutPopulator layoutPopulator,
                                SettingsButtonsConfigurator settingsButtonsConfigurator,
                                PaintView paintView) {

        this.viewModel = viewModel;
        this.buttonClickHandler = buttonClickHandler;
        this.layoutPopulator = layoutPopulator;
        this.settingsButtonsConfigurator = settingsButtonsConfigurator;
        this.paintView = paintView;
    }


    public void onPause(){
        BitmapHistory bitmapHistory = paintView.getBitmapHistory();
        ArrayDeque<Bitmap> history = bitmapHistory.getAll();
        viewModel.bitmapHistory = history;
        log("About to save history with "  + history.size() + " elements to viewModel");
        long time = System.currentTimeMillis();
        System.out.println("ResumedActionsHelper.onPause() time: " + time);
        viewModel.time = time;

        /*
        PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();

        paintViewSingleton.setMostRecentColor(buttonClickHandler.getMostRecentButtonKey());
        paintViewSingleton.setMostRecentShade(buttonClickHandler.getMostRecentShadeKey());
        paintViewSingleton.setWasMostRecentClickAShade(buttonClickHandler.isMostRecentClickAShade());

         */
    }

    private void log(String msg){
        System.out.println("ResumedActionsHelper: " + msg);
    }


    public void onResume() {
        System.out.println("ResumedActionsHelper.onResume() time: " + viewModel.time);

        if(viewModel.bitmapHistory != null) {
            ArrayDeque<Bitmap> history = viewModel.bitmapHistory;
           // paintView.setBitmap(viewModel.bitmap, new TextControlsDto());
            log("Retrieved history from viewModel with " + history.size() + " elements");
            paintView.getBitmapHistory().setAll(viewModel.bitmapHistory);
            paintView.useMostRecentHistory();
        }
        else{
            log("viewModel.bitmapHistory is null!");
        }
        /*
        PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
        boolean wasMostRecentClickAShade = paintViewSingleton.wasMostRecentClickAShade();
        selectRecentColorButton(paintViewSingleton);
        paintViewSingleton.setWasMostRecentClickAShade(wasMostRecentClickAShade);
        selectRecentShadeButton(paintViewSingleton);
        selectRecentShapeAndStyle(paintViewSingleton);

         */
    }


    private void selectRecentColorButton(PaintViewSingleton pvs) {
        Button mostRecentColorButton = layoutPopulator.getButton(pvs.getMostRecentColor());
        buttonClickHandler.handleColorButtonClicks(mostRecentColorButton);
    }


    private void selectRecentShadeButton(PaintViewSingleton pvs) {
        if (pvs.wasMostRecentClickAShade()) {
            Button mostRecentShadeButton = layoutPopulator.getButton(pvs.getMostRecentShade());
            buttonClickHandler.handleColorButtonClicks(mostRecentShadeButton);
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
