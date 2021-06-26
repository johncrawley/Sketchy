package com.jacstuff.sketchy.controls.settings.gradient;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.seekbars.AbstractSeekBarConfig;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.GradientHelper;

public class GradientSizeSeekBar extends AbstractSeekBarConfig {


    public GradientSizeSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.gradientSizeSeekBar, R.integer.gradient_radius_default);
    }


    @Override
    public void onStopTracking(int progress){
        setValue(progress);
        viewModel.gradient = progress;
    }


    @Override
    public void setDefaultValue(int value){
        setValue(value);
    }


    private void setValue(int value){
        if(paintHelperManager == null){
            log("paintHelperManager is null!");
            return;
        }
        GradientHelper gradientHelper = paintHelperManager.getGradientHelper();
        if(gradientHelper == null){
            log("gradientHelper is null!");
        }else{
            gradientHelper.setGradientRadius(value);
        }

    }

    private void log(String msg){
        System.out.println("GradientSizeSeekBar: " + msg);
    }
}