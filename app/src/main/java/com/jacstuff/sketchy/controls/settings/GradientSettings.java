package com.jacstuff.sketchy.controls.settings;


import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.gradient.GradientColorType;


public class GradientSettings extends AbstractButtonConfigurator<GradientType> implements ButtonsConfigurator<GradientType> {

    private final View gradientColorPickerSeekBar;
    private Button resetButton;

    public GradientSettings(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        gradientColorPickerSeekBar = activity.findViewById(R.id.gradientColorPickerSeekBar);
        resetButton = activity.findViewById(R.id.resetRadialGradientOffsetsButton);
        setVisibilityOfColorPickerSeekBar();
        configureSubPanels();
    }


    private void configureSubPanels(){
        subPanelManager.setOffButtonAndDefaultLayout(R.id.noGradientButton, R.id.gradientMainSettingsLayout);
        subPanelManager.add(R.id.gradientRadialClampButton, R.id.radialGradientSettingsLayout);
        subPanelManager.add(R.id.gradientRadialMirrorButton, R.id.radialGradientSettingsLayout);
        subPanelManager.add(R.id.gradientRadialRepeatButton, R.id.radialGradientSettingsLayout);
        subPanelManager.add(R.id.gradientHorizontalMirrorButton, R.id.linearGradientSettingsLayout);
        subPanelManager.add(R.id.gradientVerticallMirrorButton, R.id.linearGradientSettingsLayout);
        subPanelManager.add(R.id.diagonalMirrorGradientButton, R.id.linearGradientSettingsLayout);
    }


    private void setVisibilityOfColorPickerSeekBar(){
        int visibility = GradientColorType.SELECTED == viewModel.gradientColorType ? View.VISIBLE : View.INVISIBLE;
        gradientColorPickerSeekBar.setVisibility(visibility);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.GRADIENT, R.id.gradientOptionsLayout);
        buttonConfig.add(R.id.noGradientButton,                 R.drawable.button_gradient_off,            GradientType.NONE);
        buttonConfig.add(R.id.gradientVerticallMirrorButton,    R.drawable.button_gradient_vertical,     GradientType.LINEAR_VERTICAL);
        buttonConfig.add(R.id.gradientHorizontalMirrorButton,   R.drawable.button_gradient_horizontal,   GradientType.LINEAR_HORIZONTAL);
        buttonConfig.add(R.id.diagonalMirrorGradientButton, R.drawable.button_gradient_diagonal, GradientType.LINEAR_DIAGONAL);
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

        resetButton = activity.findViewById(R.id.resetRadialGradientOffsetsButton);

        seekBarConfigurator.configure( R.id.gradientSizeSeekBar,
                R.integer.gradient_radius_default,
                progress ->paintHelperManager.getGradientHelper().setLength(progress) );

        seekBarConfigurator.configure( R.id.gradientOffsetXSeekBar,
                R.integer.gradient_radius_offset_x_default,
                progress ->  {
                    paintHelperManager.getGradientHelper().setRadialOffsetX(progress);
                    resetButton.setEnabled(true);
                });

        seekBarConfigurator.configure( R.id.gradientOffsetYSeekBar,
                R.integer.gradient_radius_offset_y_default,
                progress -> {
                    paintHelperManager.getGradientHelper().setRadialOffsetY(progress);
                    resetButton.setEnabled(true);
                } );

        seekBarConfigurator.configure( R.id.gradientColorPickerSeekBar,
                R.integer.gradient_color_picker_seek_bar_default,
                progress -> paintHelperManager.getGradientHelper().setColor(progress));

        seekBarConfigurator.configure( R.id.gradientLinearOffsetSeekBar,
                R.integer.gradient_linear_offset_seek_bar_default,
                progress -> paintHelperManager.getGradientHelper().setLinearOffset(progress));
    }


    private void setupSwitches(){
        View includeLinearOffsetLayout = activity.findViewById(R.id.gradientLinearOffsetSeekBarInclude);
        setupSwitch(R.id.gradientLinearRepeatSwitch, b ->{
            includeLinearOffsetLayout.setVisibility(b ? View.INVISIBLE : View.VISIBLE);
            viewModel.isLinearGradientRepeated = b;
        });
    }


    private void setupSpinner(){
        SettingsUtils.setupSpinnerWithLabels(activity,
                R.id.gradientColorSpinner,
                R.array.gradient_color_array,
                R.array.gradient_color_values,
                x -> {
                    paintHelperManager.getGradientHelper().setGradientColorType(x);
                    setVisibilityOfColorPickerSeekBar();
                });
    }


    private void setupRadialGradientOffsetResetButton(){
        SeekBar offsetXSeekBar=  activity.findViewById(R.id.gradientOffsetXSeekBar);
        SeekBar offsetYSeekBar=  activity.findViewById(R.id.gradientOffsetYSeekBar);
        int offsetXDefault = activity.getResources().getInteger(R.integer.gradient_radius_offset_x_default);
        int offsetYDefault = activity.getResources().getInteger(R.integer.gradient_radius_offset_y_default);

        resetButton.setOnClickListener( v-> {
            offsetXSeekBar.setProgress(offsetXDefault);
            paintHelperManager.getGradientHelper().setRadialOffsetX(offsetXDefault);
            offsetYSeekBar.setProgress(offsetYDefault);
            paintHelperManager.getGradientHelper().setRadialOffsetY(offsetYDefault);
            resetButton.setEnabled(false);
        });
        resetButton.setEnabled(false);
    }

}