package com.jacstuff.sketchy.controls.seekbars;

import android.widget.SeekBar;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.paintview.PaintView;

public abstract class AbstractSeekBarConfig {

    public SeekBar seekBar;
    public MainActivity mainActivity;
    public PaintView paintView;
    public MainViewModel viewModel;
    public PaintHelperManager paintHelperManager;

    public AbstractSeekBarConfig(MainActivity mainActivity, PaintView paintView, int seekBarId, int defaultValueId){
        this.mainActivity = mainActivity;
        this.paintView = paintView;
        seekBar = mainActivity.findViewById(seekBarId);
        viewModel = mainActivity.getViewModel();
        setupListener();
        int defaultValue = getValueOf(defaultValueId);
        setDefaultValue(defaultValue);
        paintHelperManager = mainActivity.getPaintHelperManager();
    }

    private void setupListener(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                onStopTracking(seekBar.getProgress());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                onStartTracking();
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adjustSetting(progress);
            }

        });
    }


    public void onStartTracking(){
        //do nothing
    }

    public void onStopTracking(int progress){
        //do nothing
    }

    public void adjustSetting(int progress){
        //do nothing
    }

    public void setDefaultValue(int defaultValue){
        adjustSetting(defaultValue);
        onStopTracking(defaultValue);
    }


    protected int getValueOf(int id){
        return mainActivity.getResources().getInteger(id);
    }




}
