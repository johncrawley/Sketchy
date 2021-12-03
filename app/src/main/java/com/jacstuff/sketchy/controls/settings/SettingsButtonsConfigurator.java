package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.settings.color.ColorSettings;
import com.jacstuff.sketchy.controls.settings.placement.PlacementSettings;
import com.jacstuff.sketchy.controls.settings.shape.ShapeSettings;
import com.jacstuff.sketchy.paintview.PaintView;

import java.util.ArrayList;
import java.util.List;


public class SettingsButtonsConfigurator {


    private final List<SelectableDefault> configurators;

    public SettingsButtonsConfigurator(MainActivity activity, PaintView paintView){

        // needs to be instantiated first, otherwise parent button icons won't get updated
        new MenuButtonsConfigurator(activity);

        configurators = new ArrayList<>();
        configurators.add(new ShapeSettings(activity, paintView));
        configurators.add(new StyleSettings(activity, paintView));
        configurators.add(new GradientSettings(activity, paintView));
        configurators.add(new BlurSettings(activity, paintView));
        configurators.add(new ShadowSettings(activity, paintView));
        configurators.add(new KaleidoscopeSettings(activity, paintView));
        configurators.add(new AngleSettings(activity, paintView));
        configurators.add(new SizeSequenceSettings(activity, paintView));
        configurators.add(new ColorSettings(activity, paintView));
        configurators.add(new PlacementSettings(activity, paintView));
    }


    public void selectDefaults(){
        for(SelectableDefault selectableDefault : configurators){
            selectableDefault.selectDefaultSelection();
        }
    }

}
