package com.jacstuff.sketchy.brushes.shapes.initializer;

import android.app.Activity;
import android.content.Context;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DefaultInitializer implements BrushInitializer {

    private MainViewModel viewModel;


    @Override
    public void init(Activity activity, MainViewModel viewModel, PaintHelperManager paintHelperManager){
        this.viewModel = viewModel;
    }

    public void initialize(){
        viewModel.useStrokeWidthForShadowDistance = false;
    }

    public void deInitialize(){

    }
}
