package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class StationarySizeSequence implements SizeSequence{

    private final MainViewModel viewModel;
    private boolean hasChanged = false;

    public StationarySizeSequence(MainViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public int getNextBrushSize() {
        return viewModel.brushSizeSetBySeekBar;
    }

    @Override
    public void init() {
        // do nothing
    }


    @Override
    public void reset() {
        hasChanged = true;
    }


    @Override
    public boolean hasSizeChanged(){
        boolean ret = hasChanged;
        hasChanged = false;
        return ret;
    }
}
