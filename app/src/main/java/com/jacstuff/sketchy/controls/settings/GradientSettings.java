package com.jacstuff.sketchy.controls.settings;


import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.gradient.GradientColorType;


public class GradientSettings extends AbstractButtonConfigurator<GradientType> implements ButtonsConfigurator<GradientType> {

    private final View gradientColorPickerSeekBar;

    public GradientSettings(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        gradientColorPickerSeekBar = activity.findViewById(R.id.gradientColorPickerSeekBar);
        setVisibilityOfColorPickerSeekBar();
        subPanelManager.setOffButtonAndDefaultLayout(R.id.noGradientButton, R.id.gradientMainSettingsLayout);
        subPanelManager.add(R.id.gradientRadialClampButton, R.id.radialGradientSettingsLayout);
        subPanelManager.add(R.id.gradientRadialMirrorButton, R.id.radialGradientSettingsLayout);
        subPanelManager.add(R.id.gradientRadialRepeatButton, R.id.radialGradientSettingsLayout);
        subPanelManager.add(R.id.gradientHorizontalMirrorButton, R.id.linearGradientSettingsLayout);
        subPanelManager.add(R.id.gradientVerticallMirrorButton, R.id.linearGradientSettingsLayout);
        subPanelManager.add(R.id.diagonalMirrorGradientButton, R.id.linearGradientSettingsLayout);

        // linearGradientSettingsLayout
    }


    private void setVisibilityOfColorPickerSeekBar(){
        int visibility = GradientColorType.SELECTED == viewModel.gradientColorType ? View.VISIBLE : View.INVISIBLE;
        gradientColorPickerSeekBar.setVisibility(visibility);
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
        setupRadialGradientOffsetResetButton();
        setupSwitches();
    }


    @Override
    public void handleClick(int viewId, GradientType gradientType){
        paintHelperManager.getGradientHelper().setGradientType(gradientType);
    }


    private void configureSeekBars(){
        seekBarConfigurator.configure( R.id.gradientSizeSeekBar,
                R.integer.gradient_radius_default,
                progress ->paintHelperManager.getGradientHelper().setLength(progress) );

        seekBarConfigurator.configure( R.id.gradientOffsetXSeekBar,
                R.integer.gradient_radius_offset_x_default,
                progress ->  paintHelperManager.getGradientHelper().setRadialOffsetX(progress));

        seekBarConfigurator.configure( R.id.gradientOffsetYSeekBar,
                R.integer.gradient_radius_offset_y_default,
                progress ->  paintHelperManager.getGradientHelper().setRadialOffsetY(progress));

        seekBarConfigurator.configure( R.id.gradientColorPickerSeekBar,
                R.integer.gradient_color_picker_seek_bar_default,
                progress -> paintHelperManager.getGradientHelper().setColor(progress));

        seekBarConfigurator.configure( R.id.gradientLinearOffsetSeekBar,
                R.integer.gradient_linear_offset_seek_bar_default,
                progress -> paintHelperManager.getGradientHelper().setLinearOffset(progress));
    }


    private void setupSwitches(){
        View includeLinearOffsetLayout = activity.findViewById(R.id.gradientLinearOffsetSeekBarInclude);
        SwitchMaterial linearRepeatSwitch = activity.findViewById(R.id.gradientLinearRepeatSwitch);
        linearRepeatSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->{
            includeLinearOffsetLayout.setVisibility(isChecked ? View.INVISIBLE : View.VISIBLE);
            viewModel.isLinearGradientRepeated = isChecked;
        });
    }


    private void setupSpinner(){
        SettingsUtils.setupSpinner2(activity,
                R.id.gradientColorSpinner,
                R.array.gradient_color_array,
                R.array.gradient_color_values,
                x -> {
                    paintHelperManager.getGradientHelper().setGradientColorType(x);
                    setVisibilityOfColorPickerSeekBar();
                });
    }


    private void setupRadialGradientOffsetResetButton(){
        Button resetButton = activity.findViewById(R.id.resetRadialGradientOffsetsButton);
        SeekBar offsetXSeekBar=  activity.findViewById(R.id.gradientOffsetXSeekBar);
        SeekBar offsetYSeekBar=  activity.findViewById(R.id.gradientOffsetYSeekBar);
        int offsetXDefault = activity.getResources().getInteger(R.integer.gradient_radius_offset_x_default);
        int offsetYDefault = activity.getResources().getInteger(R.integer.gradient_radius_offset_y_default);

        resetButton.setOnClickListener( v-> {
            offsetXSeekBar.setProgress(offsetXDefault);
            paintHelperManager.getGradientHelper().setRadialOffsetX(offsetXDefault);
            offsetYSeekBar.setProgress(offsetYDefault);
            paintHelperManager.getGradientHelper().setRadialOffsetY(offsetYDefault);
        });
    }

}