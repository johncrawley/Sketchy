package com.jacstuff.sketchy.controls.settings.size;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.seekbars.AbstractSeekBarConfig;
import com.jacstuff.sketchy.paintview.PaintView;

public class SizeSequenceMaxSeekBar  extends AbstractSeekBarConfig {

    public SizeSequenceMaxSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.sizeSequenceMaxSeekBar, R.integer.size_sequence_max_default);
    }


    @Override
    public void adjustSetting(int progress){
        viewModel.sizeSequenceMax = 1 + progress;
    }

}