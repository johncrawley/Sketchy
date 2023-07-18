package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.settings.menu.MenuButtonsConfigurator;
import com.jacstuff.sketchy.controls.settings.placement.PlacementSettings;
import com.jacstuff.sketchy.controls.settings.shape.ShapeSettings;
import com.jacstuff.sketchy.paintview.PaintView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SettingsButtonsConfigurator {


    private final List<SelectableDefault> configurators;

    public SettingsButtonsConfigurator(MainActivity activity, PaintView paintView){
        // needs to be instantiated first, otherwise parent button icons won't get updated
        new MenuButtonsConfigurator(activity, paintView);
        configurators = new ArrayList<>();
        configurators.add(new ShapeSettings(activity, paintView));
        configurators.add(new StyleSettings(activity, paintView));
        configurators.add(new GradientSettings(activity, paintView));
        configurators.add(new BlurSettings(activity, paintView));
        configurators.add(new ShadowSettings(activity, paintView));
        configurators.add(new KaleidoscopeSettings(activity, paintView));
        configurators.add(new AngleSettings(activity, paintView));
        configurators.add(new SizeSequenceSettings(activity, paintView));
        configurators.add(new PlacementSettings(activity, paintView));
    }


    public void selectDefaults(){
        for(SelectableDefault selectableDefault : configurators){
            selectableDefault.selectDefaultSelection();
        }
    }

}
