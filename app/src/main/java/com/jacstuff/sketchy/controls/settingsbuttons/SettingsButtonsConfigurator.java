package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.PaintView;


public class SettingsButtonsConfigurator {

    public SettingsButtonsConfigurator(MainActivity activity, PaintView paintView){
        new ShapeButtonsConfigurator(activity, paintView);
        new StyleButtonsConfigurator(activity, paintView);
        new SelectionButtonsConfigurator(activity);
        new GradientButtonsConfigurator(activity, paintView);
        new BlurButtonsConfigurator(activity, paintView);
        new ShadowButtonsConfigurator(activity, paintView);
        new KaleidoscopeButtonsConfigurator(activity, paintView);
        new AngleButtonsConfigurator(activity, paintView);
    }


    public void handleButtonClick(int viewId){

    }


    public void clickOnView(int id){

    }

}
