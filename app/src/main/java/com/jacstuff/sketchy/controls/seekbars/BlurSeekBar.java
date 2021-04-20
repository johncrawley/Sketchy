package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;

public class BlurSeekBar  extends AbstractSeekBarConfig {


    public BlurSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.lineWidthSeekBar);
    }


    void adjustSetting(int progress){
        if(paintView != null){
            paintView.setBlurRadius(progress);
        }
    }
}