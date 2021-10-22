package com.jacstuff.sketchy.paintview.helpers.size.initializer;

import android.app.Activity;
import android.widget.SeekBar;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.helpers.BrushSizeSeekBarManager;

public class VaryingSizeInitializer implements SizeInitializer {

    private final BrushSizeSeekBarManager brushSizeSeekBarManager;

    public VaryingSizeInitializer(BrushSizeSeekBarManager brushSizeSeekBarManager){
        this.brushSizeSeekBarManager = brushSizeSeekBarManager;
    }

    public void init(){
        brushSizeSeekBarManager.setStandardSizeModeEnabled(false);
    }
}
