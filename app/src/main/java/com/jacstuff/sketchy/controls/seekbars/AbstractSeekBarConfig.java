package com.jacstuff.sketchy.controls.seekbars;

import android.widget.SeekBar;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.PaintView;

public abstract class AbstractSeekBarConfig {

    SeekBar seekBar;
    MainActivity mainActivity;
    PaintView paintView;

    AbstractSeekBarConfig(MainActivity mainActivity, PaintView paintView, int seekBarId){
        this.mainActivity = mainActivity;
        this.paintView = paintView;
        log("AbstractSeekBarConfig constructor, about to look for id: " + seekBarId);
        seekBar = mainActivity.findViewById(seekBarId);

        setupListener();
    }

    private void setupListener(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adjustSetting(progress);
            }
        });
    }

    private void log(String msg){
        System.out.println("AbstractSeekBar: " + msg);
    }

    abstract void adjustSetting(int progress);


    protected int getValueOf(int id){
        return mainActivity.getResources().getInteger(id);
    }




}
