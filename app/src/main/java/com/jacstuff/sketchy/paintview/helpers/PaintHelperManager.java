package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class PaintHelperManager {

    private final GradientHelper gradientHelper;


    public PaintHelperManager(MainActivity mainActivity, MainViewModel viewModel){
        gradientHelper = new GradientHelper(viewModel, mainActivity.getResources().getInteger(R.integer.gradient_radius_max));
        log("PaintHelperManager()... gradientHelper is not null!");
    }


    private void log(String msg){
        System.out.println("PaintHelperManager: " + msg);
    }

    public void init(Paint paint){
        gradientHelper.init(paint);
    }

    public GradientHelper getGradientHelper(){
        log("Entered getGradientHelper()");
        return gradientHelper;
    }

}
