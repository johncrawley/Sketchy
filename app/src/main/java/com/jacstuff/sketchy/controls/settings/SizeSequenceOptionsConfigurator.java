package com.jacstuff.sketchy.controls.settings;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.size.SizeSequenceType;

public class SizeSequenceOptionsConfigurator extends AbstractButtonConfigurator<SizeSequenceType> implements ButtonsConfigurator<SizeSequenceType> {


    public SizeSequenceOptionsConfigurator(MainActivity activity, PaintView paintView) {
        super(activity, paintView);
        //setupSpinners();
    }


    @Override
    public void configure() {
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.SIZE_SEQUENCE,
                R.id.sizeSequenceOptionsLayout);

        buttonConfig.add(R.id.sizeSequenceStationaryButton, R.drawable.button_size_sequence_stationary, SizeSequenceType.STATIONARY);
        buttonConfig.add(R.id.sizeSequenceIncreasingButton, R.drawable.button_size_sequence_increasing, SizeSequenceType.INCREASING);
        buttonConfig.add(R.id.sizeSequenceDecreasingButton, R.drawable.button_size_sequence_decreasing, SizeSequenceType.DECREASING);
        buttonConfig.add(R.id.sizeSequenceStrobeIncreasingButton, R.drawable.button_size_sequence_strobe_increasing, SizeSequenceType.STROBE_INCREASING);
        buttonConfig.add(R.id.sizeSequenceStrobeDecreasingButton, R.drawable.button_size_sequence_strobe_decreasing, SizeSequenceType.STROBE_DECREASING);
        buttonConfig.add(R.id.sizeSequenceRandomButton, R.drawable.button_size_sequence_random, SizeSequenceType.RANDOM);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.sizeSequenceButton);
        buttonConfig.setDefaultSelection(R.id.fillStyleButton);

        //new SizeSequenceMaxSeekBar(activity, paintView);
        //new SizeSequenceMinSeekBar(activity, paintView);
        //new SizeSequenceStepSeekBar(activity, paintView);

        configureSeekBars();
        setupOtherOptions();
    }


    @Override
    public void handleClick(int viewId, SizeSequenceType sizeSequenceType) {
        paintHelperManager.getSizeHelper().setSequence(sizeSequenceType);
    }


    private void configureSeekBars(){
        seekBarConfigurator.configure(R.id.sizeSequenceMaxSeekBar,
                R.integer.size_sequence_max_default,
                progress -> viewModel.sizeSequenceMax = 1 + progress
        );

        seekBarConfigurator.configure(R.id.sizeSequenceMinSeekBar,
                R.integer.size_sequence_min_default,
                progress ->  viewModel.sizeSequenceMin = 1 + progress
        );

        seekBarConfigurator.configure(R.id.sizeSequenceStepSizeSeekBar,
                R.integer.size_sequence_step_default,
                progress ->  viewModel.sizeSequenceIncrement = 1 + progress
        );

    }


    public void setupOtherOptions(){
        SwitchMaterial repeatSequence = activity.findViewById(R.id.sizeSequenceIsRepeatedSwitch);
        repeatSequence.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isSizeSequenceRepeated = isChecked);

        SwitchMaterial resetOnTouchUp = activity.findViewById(R.id.sizeSequenceIsResetSwitch);
        resetOnTouchUp.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isSizeSequenceResetOnTouchUp = isChecked);
    }


}