package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.PaintView;


public class SettingsButtonsConfigurator {

    private MainActivity activity;

    public SettingsButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        new ShapeButtonsConfigurator(activity, paintView);
        new StyleButtonsConfigurator(activity, paintView);
        new SelectionButtonsConfigurator(activity);
        new GradientButtonsConfigurator(activity, paintView);
        new BlurButtonsConfigurator(activity, paintView);
        new ShadowButtonsConfigurator(activity, paintView);
        new KaleidoscopeButtonsConfigurator(activity, paintView);
        new AngleButtonsConfigurator(activity, paintView);
    }


    public void clickOnView(int id){
        if(activity == null){
            return;
        }
        View view = activity.findViewById(id);
        if(view != null){
            view.callOnClick();
        }
    }

}
