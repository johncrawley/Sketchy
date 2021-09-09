package com.jacstuff.sketchy.controls.settings.color;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.seekbars.AbstractSeekBarConfig;
import com.jacstuff.sketchy.paintview.PaintView;

public class ColorTransparencySeekBar extends AbstractSeekBarConfig {

    public ColorTransparencySeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.colorTransparencySeekBar, R.integer.color_transparency_default);
    }

    @Override
    public void adjustSetting(int progress){
        paintHelperManager.getColorHelper().updateTransparency(progress);
    }
}