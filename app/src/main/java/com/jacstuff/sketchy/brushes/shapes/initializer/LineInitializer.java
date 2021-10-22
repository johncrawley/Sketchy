package com.jacstuff.sketchy.brushes.shapes.initializer;

import android.app.Activity;

import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class LineInitializer implements BrushInitializer{

    private MainViewModel viewModel;
    private PaintHelperManager paintHelperManager;
    private DisableBrushSizeInitializer disableBrushSizeInitializer;

    @Override
    public void init(Activity activity, MainViewModel viewModel, PaintHelperManager paintHelperManager){
        this.viewModel = viewModel;
        this.paintHelperManager = paintHelperManager;
        disableBrushSizeInitializer = new DisableBrushSizeInitializer();
        disableBrushSizeInitializer.init(activity, viewModel, paintHelperManager);
    }


    @Override
    public void initialize(){
        viewModel.shadowOffsetType = ShadowOffsetType.USE_STROKE_WIDTH;
        paintHelperManager.getShadowHelper().updateOffsetFactor();
        disableBrushSizeInitializer.initialize();
    }


    @Override
    public void deInitialize(){
        disableBrushSizeInitializer.deInitialize();
    }

}
