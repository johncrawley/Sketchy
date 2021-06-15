package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.MainViewModel;
import com.jacstuff.sketchy.controls.childpanel.ChildSettingsPanelManager;
import com.jacstuff.sketchy.paintview.PaintView;

public abstract class AbstractButtonConfigurator<T> implements SelectableDefault {


    MainActivity activity;
    MainViewModel viewModel;
    PaintView paintView;
    ChildSettingsPanelManager childSettingsPanelManager;
    ButtonConfigHandler<T> buttonConfig;


    public AbstractButtonConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.viewModel = activity.getViewModel();
        this.paintView = paintView;
        childSettingsPanelManager = new ChildSettingsPanelManager(activity);
        configure();
    }

    public abstract void configure();

    public void selectDefaultSelection(){
        buttonConfig.selectDefaultSelection();
    }
}
