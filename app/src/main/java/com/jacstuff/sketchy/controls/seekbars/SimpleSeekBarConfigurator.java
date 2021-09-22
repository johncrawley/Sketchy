package com.jacstuff.sketchy.controls.seekbars;

import android.widget.SeekBar;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import androidx.core.util.Consumer;

public class SimpleSeekBarConfigurator {

    private final MainActivity activity;
    private MainViewModel viewModel;

    public SimpleSeekBarConfigurator(MainActivity activity){
        this.activity = activity;
        viewModel = activity.getViewModel();
    }


    public void configure(int seekBarId, int defaultResourceId,
                          Consumer<Integer> progressFinishedConsumer,
                          Consumer<Integer> progressConsumer,
                          Consumer<Integer> progressStartedConsumer){

        SeekBar seekBar = activity.findViewById(seekBarId);
        viewModel = activity.getViewModel();
        seekBar.setOnSeekBarChangeListener( createSeekBarChangeListener(seekBarId, progressStartedConsumer, progressConsumer, progressFinishedConsumer));
        setDefaultValue(defaultResourceId, seekBarId, progressStartedConsumer, progressConsumer, progressFinishedConsumer);
    }


    public void configure(int seekBarId, int defaultResourceId,
                          Consumer<Integer> progressFinishedConsumer,
                          Consumer<Integer> progressConsumer){
        configure( seekBarId, defaultResourceId,  progressFinishedConsumer,progressConsumer, null);
    }


    public void configure(int seekBarId, int defaultResourceId, Consumer<Integer> progressFinishedConsumer){
        configure(seekBarId, defaultResourceId,  progressFinishedConsumer,null, null);
    }


    private SeekBar.OnSeekBarChangeListener createSeekBarChangeListener(int seekBarId,
                                                                        Consumer<Integer> progressStartedConsumer,
                                                                        Consumer<Integer> progressChangedConsumer,
                                                                        Consumer<Integer> progressFinishedConsumer){
        return new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progressChangedConsumer != null){
                    progressChangedConsumer.accept(progress);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewModel.seekBarValue.put(seekBarId, seekBar.getProgress());
                if(progressFinishedConsumer != null){
                    progressFinishedConsumer.accept(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(progressStartedConsumer != null){
                    progressStartedConsumer.accept(seekBar.getProgress());
                }
            }
        };
    }


    public void setDefaultValue(int defaultResourceId, int seekBarId,
                                Consumer<Integer> progressStartedConsumer,
                                Consumer<Integer> progressConsumer,
                                Consumer<Integer> progressFinishedConsumer){

        int progress = getSavedOrDefault(defaultResourceId, seekBarId);
        acceptIfNotNull(progressStartedConsumer, progress);
        acceptIfNotNull(progressConsumer, progress);
        acceptIfNotNull(progressFinishedConsumer, progress);
    }


    private void acceptIfNotNull(Consumer<Integer> consumer, int value){
        if(consumer != null) {
            consumer.accept(value);
        }
    }


    private int getSavedOrDefault(int defaultResourceId, int seekBarId){
        int defaultValue = getValueOf(defaultResourceId);
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
