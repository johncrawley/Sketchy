package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class StationarySizeSequence implements SizeSequence{

    public StationarySizeSequence(){
    }

    @Override
    public int getNextBrushSize() {
        return 10;
    }

    @Override
    public void init(int initialSize) {
        // do nothing
    }

    @Override
    public boolean hasSizeChanged(){
        return false;
    }
}
