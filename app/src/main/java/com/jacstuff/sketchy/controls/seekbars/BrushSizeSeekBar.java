package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;

public class BrushSizeSeekBar extends AbstractSeekBarConfig {

    private final int minBrushSize;

    public BrushSizeSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.brushSizeSeekBar, R.integer.brush_size_default);
        minBrushSize = getValueOf(R.integer.brush_size_min_default);
    }


    @Override
    public void adjustSetting(int progress){
        if(paintView != null){
            paintView.setBrushSize(minBrushSize + progress);
        }
    }

}
