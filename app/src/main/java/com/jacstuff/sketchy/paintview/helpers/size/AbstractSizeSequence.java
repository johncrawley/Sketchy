package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.paintview.helpers.BrushSizeSeekBarManager;
import com.jacstuff.sketchy.paintview.helpers.size.initializer.SizeInitializer;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public abstract  class AbstractSizeSequence {
    SizeInitializer sizeInitializer;
    MainViewModel viewModel;

    public AbstractSizeSequence(SizeInitializer sizeInitializer, MainViewModel mainViewModel){
        this.sizeInitializer = sizeInitializer;
        this.viewModel = mainViewModel;
    }

    public void init(){
        sizeInitializer.init();
    }
}
