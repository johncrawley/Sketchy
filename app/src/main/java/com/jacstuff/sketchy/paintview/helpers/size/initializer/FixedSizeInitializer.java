package com.jacstuff.sketchy.paintview.helpers.size.initializer;

import com.jacstuff.sketchy.paintview.helpers.BrushSizeSeekBarManager;

public class FixedSizeInitializer implements SizeInitializer{

    private final BrushSizeSeekBarManager brushSizeSeekBarManager;

    public FixedSizeInitializer(BrushSizeSeekBarManager brushSizeSeekBarManager){
        this.brushSizeSeekBarManager = brushSizeSeekBarManager;
    }

    public void init(){
        brushSizeSeekBarManager.setStandardSizeModeEnabled(true);
    }
}
