package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;

public class ShadowRadiusSeekBar extends AbstractSeekBarConfig {


    public ShadowRadiusSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.shadowRadiusSeekBar, R.integer.shadow_radius_default);
    }


    void adjustSetting(int progress){
        if(paintView != null){
            paintView.setShadowSize(progress);
        }
    }
}