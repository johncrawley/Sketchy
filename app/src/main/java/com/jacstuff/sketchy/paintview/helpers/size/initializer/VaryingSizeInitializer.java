package com.jacstuff.sketchy.paintview.helpers.size.initializer;

import android.app.Activity;
import android.widget.SeekBar;

import com.jacstuff.sketchy.R;

public class VaryingSizeInitializer implements SizeInitializer {

    private final SeekBar brushSizeSeekBar;

    public VaryingSizeInitializer(Activity activity){
        brushSizeSeekBar = activity.findViewById(R.id.brushSizeSeekBar);
    }


    public void init(){
        brushSizeSeekBar.setEnabled(false);
    }
}
