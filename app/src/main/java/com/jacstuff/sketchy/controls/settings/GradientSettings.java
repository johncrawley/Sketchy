package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;


public class GradientSettings extends AbstractButtonConfigurator<GradientType> implements ButtonsConfigurator<GradientType> {


    public GradientSettings(MainActivity activity, PaintView paintView){
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
        configureSeekBars();
        setupSpinner();
    }


    @Override
    public void handleClick(int viewId, GradientType gradientType){
        paintHelperManager.getGradientHelper().setGradientType(gradientType);
    }

    private void configureSeekBars(){
        seekBarConfigurator.configure( R.id.gradientSizeSeekBar,
                R.integer.gradient_radius_default,
                progress ->  paintHelperManager.getGradientHelper().setGradientRadius(progress));

        seekBarConfigurator.configure( R.id.gradientOffsetXSeekBar,
                R.integer.gradient_radius_offset_x_default,
                progress ->  paintHelperManager.getGradientHelper().setGradientOffsetX(progress));

        seekBarConfigurator.configure( R.id.gradientOffsetYSeekBar,
                R.integer.gradient_radius_offset_y_default,
                progress ->  paintHelperManager.getGradientHelper().setGradientOffsetY(progress));


        seekBarConfigurator.configure( R.id.colorPickerSeekBar,
                R.integer.gradient_color_picker_seek_bar_default,
                progress ->  paintHelperManager.getGradientHelper().setGradientColor(progress));

    }


    private void setupSpinner(){
        SettingsUtils.setupSpinner2(activity,
                R.id.gradientColorSpinner,
                R.array.gradient_color_array,
                R.array.gradient_color_values,
                x -> paintHelperManager.getGradientHelper().setGradientColorType(x));
    }


}