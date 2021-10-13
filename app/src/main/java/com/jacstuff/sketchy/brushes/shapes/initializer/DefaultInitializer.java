package com.jacstuff.sketchy.brushes.shapes.initializer;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DefaultInitializer implements BrushInitializer {

    private MainViewModel viewModel;


    @Override
    public void init(MainViewModel viewModel, PaintHelperManager paintHelperManager){
        this.viewModel = viewModel;
    }

    public void initialize(){
        viewModel.useStrokeWidthForShadowDistance = false;
    }
}
