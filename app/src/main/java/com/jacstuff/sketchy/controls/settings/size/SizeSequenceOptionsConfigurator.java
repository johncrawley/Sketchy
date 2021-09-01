package com.jacstuff.sketchy.controls.settings.size;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonConfigHandler;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
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

        new SizeSequenceMaxSeekBar(activity, paintView);
        new SizeSequenceMinSeekBar(activity, paintView);
        new SizeSequenceStepSeekBar(activity, paintView);

        setupOtherOptions();
    }


    @Override
    public void handleClick(int viewId, SizeSequenceType sizeSequenceType) {
        paintHelperManager.getSizeHelper().setSequence(sizeSequenceType);
    }

    public void setupOtherOptions(){
        SwitchMaterial repeatSequence = activity.findViewById(R.id.sizeSequenceIsRepeatedSwitch);
        repeatSequence.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isSizeSequenceRepeated = isChecked);

        SwitchMaterial resetOnTouchUp = activity.findViewById(R.id.sizeSequenceIsResetSwitch);
        resetOnTouchUp.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isSizeSequenceResetOnTouchUp = isChecked);
    }


}