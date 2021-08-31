package com.jacstuff.sketchy.controls.settings.size;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.seekbars.AbstractSeekBarConfig;
import com.jacstuff.sketchy.paintview.PaintView;

public class SizeSequenceMinSeekBar extends AbstractSeekBarConfig {

    public SizeSequenceMinSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.sizeSequenceMinSeekBar, R.integer.size_sequence_min_default);
    }

    @Override
    public void adjustSetting(int progress){
            viewModel.sizeSequenceMin = 1 + progress;
    }

}