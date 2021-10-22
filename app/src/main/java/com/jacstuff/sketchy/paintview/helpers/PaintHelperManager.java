package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.color.ColorHelper;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowHelper;
import com.jacstuff.sketchy.paintview.helpers.size.SizeHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class PaintHelperManager {

    private final GradientHelper gradientHelper;
    private final BlurHelper blurHelper;
    private final ShadowHelper shadowHelper;
    private final AngleHelper angleHelper;
    private final KaleidoscopeHelper kaleidoscopeHelper;
    private final StyleHelper styleHelper;
    private final SizeHelper sizeHelper;
    private final ColorHelper colorHelper;
    private final TileHelper tileHelper;

    public PaintHelperManager(MainActivity mainActivity, PaintView paintView, MainViewModel viewModel){
        gradientHelper = new GradientHelper(viewModel, mainActivity.getResources().getInteger(R.integer.gradient_radius_max));
        blurHelper = new BlurHelper();
        shadowHelper = new ShadowHelper(viewModel);
        angleHelper = new AngleHelper();
        kaleidoscopeHelper = new KaleidoscopeHelper(paintView, viewModel);
        styleHelper = new StyleHelper(mainActivity);
        sizeHelper = new SizeHelper(mainActivity, viewModel, paintView);
        colorHelper = new ColorHelper(viewModel, kaleidoscopeHelper);
        tileHelper = new TileHelper(viewModel, this);
    }


    public void init(Paint paint, Paint shadowPaint, Paint previewPaint, PaintGroup paintGroup){
        gradientHelper.init(paint);
        blurHelper.init(paint);
        shadowHelper.init(shadowPaint);
        styleHelper.init(paintGroup);
        colorHelper.init(paint, shadowPaint);
        tileHelper.init(paint, previewPaint);
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

    public SizeHelper getSizeHelper() { return sizeHelper;}

    public ColorHelper getColorHelper(){ return colorHelper;}

    public TileHelper getTileHelper(){ return tileHelper;}

}
