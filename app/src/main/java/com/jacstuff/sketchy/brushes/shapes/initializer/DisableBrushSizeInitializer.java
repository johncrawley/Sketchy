package com.jacstuff.sketchy.brushes.shapes.initializer;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.SeekBar;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DisableBrushSizeInitializer implements BrushInitializer{

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
        viewModel.shadowOffsetType = ShadowOffsetType.USE_STROKE_WIDTH;
        paintHelperManager.getShadowHelper().updateOffsetFactor();
    }


    @Override
    public void deInitialize(){
        //do nothing
    }
}