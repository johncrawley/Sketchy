package com.jacstuff.sketchy;

import android.widget.Button;

import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonClickHandler;
import com.jacstuff.sketchy.controls.colorbuttons.ColorButtonLayoutPopulator;
import com.jacstuff.sketchy.controls.settingsbuttons.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;

public class ResumedActionsHelper {

    private ColorButtonClickHandler buttonClickHandler;
    private ColorButtonLayoutPopulator layoutPopulator;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private PaintView paintView;

    public ResumedActionsHelper(ColorButtonClickHandler buttonClickHandler,
                                ColorButtonLayoutPopulator layoutPopulator,
                                SettingsButtonsConfigurator settingsButtonsConfigurator,
                                PaintView paintView) {
        this.buttonClickHandler = buttonClickHandler;
        this.layoutPopulator = layoutPopulator;
        this.settingsButtonsConfigurator = settingsButtonsConfigurator;
        this.paintView = paintView;
    }


    void onResume() {
        PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
        boolean wasMostRecentClickAShade = paintViewSingleton.wasMostRecentClickAShade();
        selectRecentColorButton(paintViewSingleton);
        paintViewSingleton.setWasMostRecentClickAShade(wasMostRecentClickAShade);
        selectRecentShadeButton(paintViewSingleton);
        selectRecentShapeAndStyle(paintViewSingleton);
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
        settingsButtonsConfigurator.clickOnView(pvs.getMostRecentBrushShapeId());
        settingsButtonsConfigurator.clickOnView(pvs.getMostRecentBrushStyleId());
    }


    void onPause(){
        PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
        paintViewSingleton.setBitmap(paintView.getBitmap());
        paintViewSingleton.setMostRecentColor(buttonClickHandler.getMostRecentButtonKey());
        paintViewSingleton.setMostRecentShade(buttonClickHandler.getMostRecentShadeKey());
        paintViewSingleton.setWasMostRecentClickAShade(buttonClickHandler.isMostRecentClickAShade());
    }

}
