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
    private SeekBar seekBar;

    @Override
    public void init(Activity activity, MainViewModel viewModel, PaintHelperManager paintHelperManager){
        this.viewModel = viewModel;
        this.paintHelperManager = paintHelperManager;
        seekBar = activity.findViewById(R.id.brushSizeSeekBar);
    }


    @Override
    public void initialize(){
        seekBar.setEnabled(false);
        viewModel.shadowOffsetType = ShadowOffsetType.USE_SET_VALUE;
        paintHelperManager.getShadowHelper().updateOffsetFactor();
    }


    @Override
    public void deInitialize(){
        seekBar.setEnabled(true);
    }
}