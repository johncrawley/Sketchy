package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.PaintView;


public class SettingsButtonsConfigurator {

    private MainActivity activity;

    public SettingsButtonsConfigurator(MainActivity mainActivity){
        this.activity = mainActivity;
    }


    public void setupShapeAndStyleButtons(PaintView paintView){


        new ShapeButtonsConfigurator(activity, paintView).configure();
        new StyleButtonsConfigurator(activity, paintView).configure();
        new SelectionButtonsConfigurator(activity).configure();
        new GradientButtonsConfigurator(activity,paintView).configure();
    }


    public void handleButtonClick(int viewId){

    }


    public void clickOnView(int id){

    }

}
