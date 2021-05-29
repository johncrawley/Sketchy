package com.jacstuff.sketchy.controls.seekbars;

import android.widget.SeekBar;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.PaintView;

public abstract class AbstractSeekBarConfig {

    SeekBar seekBar;
    MainActivity mainActivity;
    PaintView paintView;

    AbstractSeekBarConfig(MainActivity mainActivity, PaintView paintView, int seekBarId, int defaultValueId){
        this.mainActivity = mainActivity;
        this.paintView = paintView;
        seekBar = mainActivity.findViewById(seekBarId);
        setupListener();
        adjustSetting(getValueOf(defaultValueId));
    }

    private void setupListener(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
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


    abstract void adjustSetting(int progress);

    void onStartTracking(){
        //do nothing
    }


    protected int getValueOf(int id){
        return mainActivity.getResources().getInteger(id);
    }




}
