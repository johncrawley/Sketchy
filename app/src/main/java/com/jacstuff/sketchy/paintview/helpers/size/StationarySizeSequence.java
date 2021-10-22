package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.paintview.helpers.size.initializer.SizeInitializer;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class StationarySizeSequence extends AbstractSizeSequence implements SizeSequence{

    private boolean hasChanged = false;


    public StationarySizeSequence(SizeInitializer sizeInitializer, MainViewModel viewModel){
        super(sizeInitializer, viewModel);
    }


    @Override
    public int getNextBrushSize() {
        return viewModel.brushSizeSetBySeekBar;
    }


    @Override
    public int getBrushSize(){
        return viewModel.brushSizeSetBySeekBar;
    }


    @Override
    public void init() {
       super.init();
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
