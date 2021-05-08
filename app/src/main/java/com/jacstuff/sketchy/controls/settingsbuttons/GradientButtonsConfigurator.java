package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;

public class GradientButtonsConfigurator implements ButtonsConfigurator<GradientType>{

    private MainActivity activity;
    private PaintView paintView;


    public GradientButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }


    public void configure(){
        ButtonConfigHandler<GradientType> buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.GRADIENT);
        buttonConfig.put(R.id.noGradientButton,             R.drawable.no_gradient_button,              GradientType.NONE);
        buttonConfig.put(R.id.gradientVerticallMirrorButton,   R.drawable.gradient_vertical_mirror_button,   GradientType.VERTICAL_MIRROR);
        buttonConfig.put(R.id.gradientHorizontalMirrorButton,   R.drawable.gradient_horizontal_mirror_button,   GradientType.HORIZONTAL_MIRROR);
        buttonConfig.put(R.id.diagonalMirrorGradientButton, R.drawable.diagonal_mirror_gradient_button, GradientType.DIAGONAL_MIRROR);
        buttonConfig.put(R.id.gradientRadialClampButton,    R.drawable.gradient_radial_clamp_button,    GradientType.RADIAL_CLAMP);
        buttonConfig.put(R.id.gradientRadialRepeatButton,   R.drawable.gradient_radial_repeat_button,   GradientType.RADIAL_REPEAT);
        buttonConfig.put(R.id.gradientRadialMirrorButton,   R.drawable.gradient_radial_mirror_button,   GradientType.RADIAL_MIRROR);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.gradientSelectionButton);
        buttonConfig.setDefaultSelection(R.id.noGradientButton);
    }


    @Override
    public void handleClick(int viewId, GradientType gradientType){
        paintView.setGradientType(gradientType);
    }

}