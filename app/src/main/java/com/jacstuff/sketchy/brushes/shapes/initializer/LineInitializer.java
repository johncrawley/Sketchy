package com.jacstuff.sketchy.brushes.shapes.initializer;

import android.app.Activity;
import android.content.Context;

import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
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
        viewModel.useStrokeWidthForShadowDistance = true;
        paintHelperManager.getShadowHelper().updateOffsetFactor(1);
        disableBrushSizeInitializer.initialize();
    }

    public void deInitialize(){
        disableBrushSizeInitializer.deInitialize();
    }
}
