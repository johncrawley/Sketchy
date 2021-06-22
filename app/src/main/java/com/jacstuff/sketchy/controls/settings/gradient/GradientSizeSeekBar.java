package com.jacstuff.sketchy.controls.settings.gradient;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.seekbars.AbstractSeekBarConfig;
import com.jacstuff.sketchy.paintview.PaintView;

public class GradientSizeSeekBar extends AbstractSeekBarConfig {


    public GradientSizeSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.gradientSizeSeekBar, R.integer.gradient_radius_default);
    }


    @Override
    public void onStopTracking(int progress){
        if(paintView != null){
            paintView.setRadialGradientRadius(progress);
        }
    }
}