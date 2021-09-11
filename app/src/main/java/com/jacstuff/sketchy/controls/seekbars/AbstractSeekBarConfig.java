package com.jacstuff.sketchy.controls.seekbars;

import android.widget.SeekBar;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.paintview.PaintView;

import androidx.core.util.Consumer;

public abstract class AbstractSeekBarConfig {

    protected SeekBar seekBar;
    protected MainActivity mainActivity;
    protected PaintView paintView;
    protected MainViewModel viewModel;
    protected PaintHelperManager paintHelperManager;
    private final int seekBarId;
    protected Consumer<Integer> progressConsumer;


    public AbstractSeekBarConfig(MainActivity mainActivity, int seekBarId, int defaultValueId, Consumer<Integer> progressConsumer){
        this.mainActivity = mainActivity;
        this.seekBarId = seekBarId;
        seekBar = mainActivity.findViewById(seekBarId);
        viewModel = mainActivity.getViewModel();
        paintHelperManager = mainActivity.getPaintHelperManager();
        setupListener();
        this.progressConsumer = progressConsumer;
        int defaultValue = getValueOf(defaultValueId);
        setDefaultValue(defaultValue);
    }


    public AbstractSeekBarConfig(MainActivity mainActivity, PaintView paintView, int seekBarId, int defaultValueId){
        this.mainActivity = mainActivity;
        this.paintView = paintView;
        this.seekBarId = seekBarId;
        seekBar = mainActivity.findViewById(seekBarId);
        viewModel = mainActivity.getViewModel();
        paintHelperManager = mainActivity.getPaintHelperManager();
        setupListener();
        int defaultValue = getValueOf(defaultValueId);
        setDefaultValue(defaultValue);
    }


    private void setupListener(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewModel.seekBarValue.put(seekBarId, seekBar.getProgress());
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
        adjustSetting(getSavedOrDefault(defaultValue));
        onStopTracking(getSavedOrDefault(defaultValue));
    }


    private int getSavedOrDefault(int defaultValue){
        if(!viewModel.isFirstExecution){
            Integer savedValue = viewModel.seekBarValue.get(seekBarId);
            if(savedValue != null) {
                return savedValue;
            }
        }
        return defaultValue;
    }


    protected int getValueOf(int id){
        return mainActivity.getResources().getInteger(id);
    }


}
