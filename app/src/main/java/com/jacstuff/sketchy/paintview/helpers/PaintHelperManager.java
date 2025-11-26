package com.jacstuff.sketchy.paintview.helpers;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.color.ColorHelper;
import com.jacstuff.sketchy.paintview.helpers.gradient.GradientHelper;
import com.jacstuff.sketchy.paintview.helpers.placement.PlacementHelper;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowHelper;
import com.jacstuff.sketchy.paintview.helpers.size.SizeHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class PaintHelperManager {

    private GradientHelper gradientHelper;
    private final BlurHelper blurHelper;
    private final ShadowHelper shadowHelper;
    private final AngleHelper angleHelper;
    private KaleidoscopeHelper kaleidoscopeHelper;
    private StyleHelper styleHelper;
    private SizeHelper sizeHelper;
    private ColorHelper colorHelper;
    private final TileHelper tileHelper;
    private BrushSizeSeekBarManager brushSizeSeekBarManager;
    private PlacementHelper placementHelper;
    private final SensitivityHelper sensitivityHelper;
    private final MainViewModel viewModel;


    public PaintHelperManager(MainViewModel viewModel){
        this.viewModel = viewModel;
        blurHelper = new BlurHelper();
        shadowHelper = new ShadowHelper(viewModel);
        angleHelper = new AngleHelper(viewModel);
        tileHelper = new TileHelper(viewModel, this);
        sensitivityHelper = new SensitivityHelper(viewModel, angleHelper);
    }


    public void initBrushSizeManager(View parentView){
        brushSizeSeekBarManager = new BrushSizeSeekBarManager(parentView);
    }


    public void setPaintView(PaintView paintView, Context context){
        styleHelper = new StyleHelper(context, paintView, viewModel);
        kaleidoscopeHelper = new KaleidoscopeHelper(paintView, viewModel);
        gradientHelper = new GradientHelper(viewModel, kaleidoscopeHelper);
        sizeHelper = new SizeHelper(viewModel, paintView, brushSizeSeekBarManager);
        colorHelper = new ColorHelper(paintView, viewModel, kaleidoscopeHelper);
        placementHelper = new PlacementHelper(viewModel, context, sizeHelper);

    }


    public void init(PaintGroup paintGroup){
        gradientHelper.init(paintGroup.getDrawPaint());
        blurHelper.init(paintGroup.getDrawPaint());
        shadowHelper.init(paintGroup.getShadowPaint());
        styleHelper.init(paintGroup);
        colorHelper.init(paintGroup.getDrawPaint(), paintGroup.getShadowPaint());
        tileHelper.init(paintGroup.getDrawPaint(), paintGroup.getPreviewPaint());
        placementHelper.init(paintGroup.getDrawPaint());
    }


    public void initDimensions(int width, int height){
        gradientHelper.initDimensions(width, height);
        placementHelper.initDimensions(width, height);
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


    public BrushSizeSeekBarManager getBrushSizeSeekBarManager(){
        return brushSizeSeekBarManager;
    }


    public StyleHelper getStyleHelper(){
        return styleHelper;
    }


    public PlacementHelper getPlacementHelper(){
        return this.placementHelper;
    }


    public SensitivityHelper getSensitivityHelper(){
        return this.sensitivityHelper;
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
