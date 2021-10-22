package com.jacstuff.sketchy.brushes.shapes.initializer;

import android.app.Activity;
import android.widget.SeekBar;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DragRectInitializer implements BrushInitializer{

    private MainViewModel viewModel;
    private PaintHelperManager paintHelperManager;

    @Override
    public void init(Activity activity, MainViewModel viewModel, PaintHelperManager paintHelperManager){
        this.viewModel = viewModel;
        this.paintHelperManager = paintHelperManager;
    }


    @Override
    public void initialize(){
        paintHelperManager.getBrushSizeSeekBarManager().setCurrentShapeAffectedByBrushSize(false);
        viewModel.shadowOffsetType = ShadowOffsetType.USE_SET_VALUE;
        paintHelperManager.getShadowHelper().updateOffsetFactor();
    }


    @Override
    public void deInitialize(){
        //do nothing
    }
}