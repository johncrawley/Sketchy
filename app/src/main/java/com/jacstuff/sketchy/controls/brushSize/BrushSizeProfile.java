package com.jacstuff.sketchy.controls.brushSize;

import android.widget.SeekBar;

public class BrushSizeProfile {

    private int min, max;

    public BrushSizeProfile(int min, int max){
        this.min = min;
        this.max = max;
    }

    public void applyTo(SeekBar seekBar){
        seekBar.setMin(min);
        seekBar.setMax(max);
    }
}
