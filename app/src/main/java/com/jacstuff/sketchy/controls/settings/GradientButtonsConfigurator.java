package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

public class GradientButtonsConfigurator extends AbstractButtonConfigurator<GradientType> implements ButtonsConfigurator<GradientType> {


    public GradientButtonsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.GRADIENT, R.id.gradientOptionsLayout);
        buttonConfig.add(R.id.noGradientButton,                 R.drawable.button_gradient_off,            GradientType.NONE);
        buttonConfig.add(R.id.gradientVerticallMirrorButton,    R.drawable.button_gradient_vertical,     GradientType.VERTICAL_MIRROR);
        buttonConfig.add(R.id.gradientHorizontalMirrorButton,   R.drawable.button_gradient_horizontal,   GradientType.HORIZONTAL_MIRROR);
        buttonConfig.add(R.id.diagonalMirrorGradientButton, R.drawable.button_gradient_diagonal, GradientType.DIAGONAL_MIRROR);
        buttonConfig.add(R.id.gradientRadialClampButton,    R.drawable.button_gradient_radial_clamp,    GradientType.RADIAL_CLAMP);
        buttonConfig.add(R.id.gradientRadialRepeatButton,   R.drawable.button_gradient_radial_repeat,   GradientType.RADIAL_REPEAT);
        buttonConfig.add(R.id.gradientRadialMirrorButton,   R.drawable.button_gradient_radial_mirror,   GradientType.RADIAL_MIRROR);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.gradientButton);
        buttonConfig.setDefaultSelection(R.id.noGradientButton);

        configureSeekBar();
    }


    @Override
    public void handleClick(int viewId, GradientType gradientType){
        paintHelperManager.getGradientHelper().setGradientType(gradientType);
    }

    private void configureSeekBar(){
        seekBarConfigurator.configure( R.id.gradientSizeSeekBar,
                R.integer.gradient_radius_default,
                progress ->  paintHelperManager.getGradientHelper().setGradientRadius(progress));
    }

}