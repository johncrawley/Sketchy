package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;

public class BrushSizeSeekBar extends AbstractSeekBarConfig {

    private int minBrushSize;

    public BrushSizeSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.brushSizeSeekBar);
        minBrushSize = getValueOf(R.integer.brush_size_min_default);
        paintView.setBrushSize(getValueOf(R.integer.brush_size_default));
    }

    void adjustSetting(int progress){
        if(paintView != null){
            paintView.setBrushSize(minBrushSize + progress);
        }
    }

    private int getValueOf(int id){
        return mainActivity.getResources().getInteger(id);
    }
}
