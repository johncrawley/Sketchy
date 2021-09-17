package com.jacstuff.sketchy.controls.seekbars;

import android.widget.SeekBar;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import androidx.core.util.Consumer;

public class SimpleSeekBarConfigurator {

    private MainActivity activity;
    private MainViewModel viewModel;

    public SimpleSeekBarConfigurator(MainActivity activity){
        this.activity = activity;
        viewModel = activity.getViewModel();
    }



    public void configure(int seekBarId, int defaultResourceId, Consumer<Integer> progressConsumer){

        SeekBar seekBar = activity.findViewById(seekBarId);
        viewModel = activity.getViewModel();
        PaintHelperManager paintHelperManager = activity.getPaintHelperManager();
        seekBar.setOnSeekBarChangeListener( createSeekBarChangeListener(seekBarId, progressConsumer));
        int defaultValue = getValueOf(defaultResourceId);
        setDefaultValue(defaultValue, seekBarId);

    }


    private SeekBar.OnSeekBarChangeListener createSeekBarChangeListener(int seekBarId, Consumer<Integer> progressConsumer){
        return new SeekBar.OnSeekBarChangeListener() {
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
        };
    }

    public void onStartTracking(){

    }

    public void setDefaultValue(int defaultValue, int seekBarId){
        adjustSetting(getSavedOrDefault(defaultValue, seekBarId));
        onStopTracking(getSavedOrDefault(defaultValue, seekBarId));
    }

    private void adjustSetting(int value){

    }

    private void onStopTracking(int value){

    }


    private int getSavedOrDefault(int defaultValue, int seekBarId){
        if(!viewModel.isFirstExecution){
            Integer savedValue = viewModel.seekBarValue.get(seekBarId);
            if(savedValue != null) {
                return savedValue;
            }
        }
        return defaultValue;
    }


    protected int getValueOf(int id){
        return activity.getResources().getInteger(id);
    }

}
