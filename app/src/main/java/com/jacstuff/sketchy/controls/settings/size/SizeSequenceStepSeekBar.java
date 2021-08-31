package com.jacstuff.sketchy.controls.settings.size;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.seekbars.AbstractSeekBarConfig;
import com.jacstuff.sketchy.paintview.PaintView;

public class SizeSequenceStepSeekBar extends AbstractSeekBarConfig {

    public SizeSequenceStepSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.sizeSequenceStepSizeSeekBar, R.integer.size_sequence_step_default);
    }

    @Override
    public void adjustSetting(int progress){
        viewModel.sizeSequenceIncrement = 1 + progress;
    }

}