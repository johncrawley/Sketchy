package com.jacstuff.sketchy.brushes.shapes.initializer;

import android.app.Activity;

import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DefaultInitializer implements BrushInitializer {

    private MainViewModel viewModel;


    @Override
    public void init(Activity activity, MainViewModel viewModel, PaintHelperManager paintHelperManager){
        paintHelperManager.getBrushSizeSeekBarManager().setCurrentShapeAffectedByBrushSize(true);
        this.viewModel = viewModel;
    }


    @Override
    public void initialize(){
        viewModel.shadowOffsetType = ShadowOffsetType.USE_SHAPE_WIDTH;
    }


    @Override
    public void deInitialize(){
        // do nothing
    }
}
