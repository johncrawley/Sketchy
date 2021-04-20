package com.jacstuff.sketchy.controls.seekbars;

import android.widget.SeekBar;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;

public abstract class AbstractSeekBarConfig {

    SeekBar seekBar;
    MainActivity mainActivity;
    PaintView paintView;

    AbstractSeekBarConfig(MainActivity mainActivity, PaintView paintView, int seekBarId){
        this.mainActivity = mainActivity;
        this.paintView = paintView;
        seekBar = mainActivity.findViewById(seekBarId);
        setupLineWidthSeekBar();
    }

    private void setupLineWidthSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                adjustSetting(progress);
            }
        });
    }

    abstract void adjustSetting(int progress);




}
