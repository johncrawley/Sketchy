package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.PaintView;

public class SeekBarConfigurator {

    public SeekBarConfigurator(MainActivity mainActivity, PaintView paintView){
        new LineWidthSeekBar(mainActivity, paintView);
        new BrushSizeSeekBar(mainActivity, paintView);
        new GradientSizeSeekBar(mainActivity, paintView);
        new BlurSeekBar(mainActivity, paintView);
    }
}
