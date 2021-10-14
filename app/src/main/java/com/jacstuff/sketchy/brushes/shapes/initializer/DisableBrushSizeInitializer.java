package com.jacstuff.sketchy.brushes.shapes.initializer;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.SeekBar;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DisableBrushSizeInitializer implements BrushInitializer{

    private MainViewModel viewModel;
    private PaintHelperManager paintHelperManager;
    private SeekBar seekBar;

    @Override
    public void init(Activity activity, MainViewModel viewModel, PaintHelperManager paintHelperManager){
        seekBar = activity.findViewById(R.id.brushSizeSeekBar);
        this.viewModel = viewModel;
        this.paintHelperManager = paintHelperManager;
    }

    @Override
    public void initialize(){
        seekBar.setEnabled(false);
        viewModel.useStrokeWidthForShadowDistance = true;
        paintHelperManager.getShadowHelper().updateOffsetFactor(1);
    }

    @Override
    public void deInitialize(){
        seekBar.setEnabled(true);
    }
}