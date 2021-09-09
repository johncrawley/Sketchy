package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;

import androidx.core.util.Consumer;

public class SimpleSeekBar extends AbstractSeekBarConfig {

    public SimpleSeekBar(MainActivity mainActivity, int seekBarId, int defaultResourceId, Consumer<Integer> progressConsumer){
        super(mainActivity, seekBarId, defaultResourceId, progressConsumer );
    }


    @Override
    public void adjustSetting(int progress){
        if(progressConsumer != null) {
            progressConsumer.accept(progress);
        }
    }
}
