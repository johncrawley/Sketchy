package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;

public class GradientSizeSeekBar extends AbstractSeekBarConfig {

    public GradientSizeSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.gradientSizeSeekBar);
    }

    void adjustSetting(int progress){
        if(paintView != null){
            paintView.setRadialGradientRadius(progress);
        }
    }
}