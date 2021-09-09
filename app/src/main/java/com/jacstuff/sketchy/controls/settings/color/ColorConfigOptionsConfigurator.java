package com.jacstuff.sketchy.controls.settings.color;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.seekbars.SimpleSeekBar;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.multicolor.SequenceColorSelector;
import com.jacstuff.sketchy.paintview.PaintView;

public class ColorConfigOptionsConfigurator  extends AbstractButtonConfigurator<Void> implements ButtonsConfigurator<Void> {

    public ColorConfigOptionsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
    }


    @Override
    public void configure(){
        SequenceColorSelector sequenceColorSelector = paintHelperManager.getColorHelper().getSequenceColorSelector();
        ColorTransparencySeekBar colorTransparencySeekBar = new ColorTransparencySeekBar(activity, paintView);
        SimpleSeekBar sequenceRangeMax =
                new SimpleSeekBar(activity,
                        R.id.colorSequenceMaxIndexSeekBar,
                        R.integer.seek_bar_color_sequence_max_range_default,
                        progress -> {
                            viewModel.getColorSequenceControls().colorSequenceMaxPercentage = progress;
                            sequenceColorSelector.updateRangeIndexes();
                        });

        SimpleSeekBar sequenceRangeMin =
                new SimpleSeekBar(activity,
                        R.id.colorSequenceMinIndexSeekBar,
                        R.integer.seek_bar_color_sequence_min_range_default,
                        progress -> {
                            viewModel.getColorSequenceControls().colorSequenceMinPercentage = progress;
                            sequenceColorSelector.updateRangeIndexes();
                        });

    }


    @Override
    public void handleClick(int viewId, Void actionType) {
        //do nothing
    }

}
