package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class PaintHelperManager {

    private final GradientHelper gradientHelper;
    private final BlurHelper blurHelper;
    private final ShadowHelper shadowHelper;
    private final AngleHelper angleHelper;
    private final KaleidoscopeHelper kaleidoscopeHelper;
    private final StyleHelper styleHelper;


    public PaintHelperManager(MainActivity mainActivity, MainViewModel viewModel){
        gradientHelper = new GradientHelper(viewModel, mainActivity.getResources().getInteger(R.integer.gradient_radius_max));
        blurHelper = new BlurHelper();
        shadowHelper = new ShadowHelper();
        angleHelper = new AngleHelper();
        kaleidoscopeHelper = new KaleidoscopeHelper(viewModel);
        styleHelper = new StyleHelper();
    }


    public void init(Paint paint, Paint shadowPaint, PaintGroup paintGroup){
        gradientHelper.init(paint);
        blurHelper.init(paint);
        shadowHelper.init(shadowPaint);
        styleHelper.init(paintGroup);
    }

    public GradientHelper getGradientHelper(){
        return gradientHelper;
    }


    public BlurHelper getBlurHelper(){
        return blurHelper;
    }


    public ShadowHelper getShadowHelper(){
        return shadowHelper;
    }


    public StyleHelper getStyleHelper(){
        return styleHelper;
    }


    public KaleidoscopeHelper getKaleidoscopeHelper(){
        return kaleidoscopeHelper;
    }


    public AngleHelper getAngleHelper(){
        return angleHelper;
    }

}
