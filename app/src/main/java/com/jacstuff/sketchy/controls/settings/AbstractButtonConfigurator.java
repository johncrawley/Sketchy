package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.controls.childpanel.ChildSettingsPanelManager;
import com.jacstuff.sketchy.paintview.PaintView;

public abstract class AbstractButtonConfigurator<T> implements SelectableDefault {


    public MainActivity activity;
    public MainViewModel viewModel;
    public PaintView paintView;
    public ChildSettingsPanelManager childSettingsPanelManager;
    public ButtonConfigHandler<T> buttonConfig;
    public PaintHelperManager paintHelperManager;

    public AbstractButtonConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.viewModel = activity.getViewModel();
        this.paintView = paintView;
        this.paintHelperManager = activity.getPaintHelperManager();
        childSettingsPanelManager = new ChildSettingsPanelManager(activity);
        configure();
    }


    public abstract void configure();


    public abstract void handleClick(int viewId, T actionType);


    public void selectDefaultSelection(){
        if(buttonConfig != null){
            buttonConfig.selectDefaultSelection();
        }
    }


    public void handleDefaultClick(int viewId, T actionType){
        handleClick(viewId, actionType);
    }


}
