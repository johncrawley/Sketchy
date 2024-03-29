package com.jacstuff.sketchy.controls.settings;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.controls.seekbars.SeekBarConfigurator;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.controls.subpanel.SubPanelManager;
import com.jacstuff.sketchy.paintview.PaintView;

import androidx.core.util.Consumer;

public abstract class AbstractButtonConfigurator<T> implements SelectableDefault {

    public MainActivity activity;
    public MainViewModel viewModel;
    public PaintView paintView;
    public SubPanelManager subPanelManager;
    public ButtonConfigHandler<T> buttonConfig;
    public PaintHelperManager paintHelperManager;
    protected final SeekBarConfigurator seekBarConfigurator;


    public AbstractButtonConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.viewModel = activity.getViewModel();
        this.paintView = paintView;
        seekBarConfigurator = activity.getSeekBarConfigurator();
        this.paintHelperManager = activity.getPaintHelperManager();
        subPanelManager = new SubPanelManager(activity);
        configure();
    }


    public abstract void configure();


    public void onClick(int viewId, T actionType){
        if(subPanelManager!= null){
            subPanelManager.select(viewId);
        }
        handleClick(viewId, actionType);
    }


    public abstract void handleClick(int viewId, T actionType);


    public void selectDefaultSelection(){
        if(buttonConfig != null){
            buttonConfig.selectDefaultSelection();
        }
    }


    public void setupSwitch(int switchId, Consumer<Boolean> consumer){
        SwitchMaterial switchMaterial = activity.findViewById(switchId);
        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> consumer.accept(isChecked));
    }


    public void setupSwitch(int switchId, boolean initialValue, Consumer<Boolean> consumer){
        SwitchMaterial switchMaterial = activity.findViewById(switchId);
        switchMaterial.setChecked(initialValue);
        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> consumer.accept(isChecked));
    }


    public void handleDefaultClick(int viewId, T actionType){
        onClick(viewId, actionType);
    }


}
