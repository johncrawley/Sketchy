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
        seekBar.setOnSeekBarChangeListener( createSeekBarChangeListener(seekBarId, progressConsumer, progressFinishedConsumer, progressStartedConsumer));
        setDefaultValue(defaultResourceId, seekBarId, progressConsumer, progressFinishedConsumer, progressStartedConsumer);
    }


    public void configure(int seekBarId, int defaultResourceId,
                          Consumer<Integer> progressFinishedConsumer,
                          Consumer<Integer> progressConsumer){
        configure(seekBarId, defaultResourceId,  progressFinishedConsumer,progressConsumer, null);
    }


    public void configure(int seekBarId, int defaultResourceId, Consumer<Integer> progressFinishedConsumer){
        configure(seekBarId, defaultResourceId,  progressFinishedConsumer,null, null);
    }


    private SeekBar.OnSeekBarChangeListener createSeekBarChangeListener(int seekBarId,
                                                                        Consumer<Integer> progressChangedConsumer,
                                                                        Consumer<Integer> progressFinishedConsumer,
                                                                        Consumer<Integer> progressStartedConsumer){
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
                                Consumer<Integer> progressConsumer,
                                Consumer<Integer> progressFinishedConsumer,
                                Consumer<Integer> progressStartedConsumer){
        int defaultValue = getValueOf(defaultResourceId);

        acceptIfNotNull(progressStartedConsumer, defaultValue, seekBarId);
        acceptIfNotNull(progressConsumer, defaultValue, seekBarId);
        acceptIfNotNull(progressFinishedConsumer, defaultValue, seekBarId);
    }


    private void acceptIfNotNull(Consumer<Integer> consumer, int defaultValue, int seekBarId){
        if(consumer != null) {
            consumer.accept(getSavedOrDefault(defaultValue, getSavedOrDefault(defaultValue, seekBarId)));
        }
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
