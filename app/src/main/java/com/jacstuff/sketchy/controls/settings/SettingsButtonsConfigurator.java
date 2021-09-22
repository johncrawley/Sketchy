package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.settings.color.ColorConfigOptionsConfigurator;
import com.jacstuff.sketchy.controls.settings.shape.ShapeButtonsConfigurator;
import com.jacstuff.sketchy.controls.settings.size.SizeSequenceOptionsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;

import java.util.ArrayList;
import java.util.List;


public class SettingsButtonsConfigurator {


    private final List<SelectableDefault> configurators;

    public SettingsButtonsConfigurator(MainActivity activity, PaintView paintView){

        // needs to be instantiated first, otherwise parent button icons won't get updated
        new MenuButtonsConfigurator(activity);

        configurators = new ArrayList<>();
        configurators.add(new ShapeButtonsConfigurator(activity, paintView));
        configurators.add(new StyleButtonsConfigurator(activity, paintView));
        configurators.add(new GradientButtonsConfigurator(activity, paintView));
        configurators.add(new BlurButtonsConfigurator(activity, paintView));
        configurators.add(new ShadowButtonsConfigurator(activity, paintView));
        configurators.add(new KaleidoscopeButtonsConfigurator(activity, paintView));
        configurators.add(new AngleButtonsConfigurator(activity, paintView));
        configurators.add(new SizeSequenceOptionsConfigurator(activity, paintView));
        configurators.add(new ColorConfigOptionsConfigurator(activity, paintView));
    }


    public void selectDefaults(){
        for(SelectableDefault selectableDefault : configurators){
            selectableDefault.selectDefaultSelection();
        }
    }

}
