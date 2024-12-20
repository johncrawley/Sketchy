package com.jacstuff.sketchy.controls.settings.rotation;


import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonConfigHandler;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;

public class PresetControls extends AbstractButtonConfigurator<AngleType> implements ButtonsConfigurator<AngleType> {


    private Button parentButton;
    private ButtonUtils buttonUtils;

    public PresetControls(MainActivity activity, PaintView paintView) {
        super(activity, paintView);
    }

    @Override
    public void configure() {
        buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.ANGLE, R.id.settingsRotationPresetInclude);
        add(R.id.zeroDegreesButton, AngleType.ZERO);
        add(R.id.degrees30Button, AngleType.THIRTY);
        add(R.id.degrees45Button, AngleType.FORTY_FIVE);
        add(R.id.degrees60Button, AngleType.SIXTY);
        add(R.id.degrees90Button, AngleType.NINTY);
        add(R.id.degrees135Button, AngleType.ONE_THREE_FIVE);
        add(R.id.degrees150Button, AngleType.ONE_FIFTY);
        add(R.id.degrees180Button, AngleType.ONE_EIGHTY);
        add(R.id.degrees225Button, AngleType.TWO_TWENTY_FIVE);
        add(R.id.degrees270Button, AngleType.TWO_SEVENTY);
        add(R.id.degrees315Button, AngleType.THREE_FIFTEEN);
        add(R.id.degreesMinus1Button, AngleType.MINUS_ONE);
        add(R.id.degreesPlus1Button, AngleType.PLUS_ONE);
        add(R.id.degreesMinus5Button, AngleType.MINUS_FIVE);
        add(R.id.degreesPlus5Button, AngleType.PLUS_FIVE);
        add(R.id.degreesMinus15Button, AngleType.MINUS_FIFTEEN);
        add(R.id.degreesPlus15Button, AngleType.PLUS_FIFTEEN);
        add(R.id.degreesMinus30Button, AngleType.MINUS_THIRTY);
        add(R.id.degreesPlus30Button, AngleType.PLUS_THIRTY);
        add(R.id.degreesRandomButton, AngleType.RANDOM);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.angleButton);
        buttonConfig.setDefaultSelection(R.id.zeroDegreesButton);
    }


    private void add(int buttonId, AngleType angleType) {
        buttonConfig.add(buttonId, angleType.getStr() + activity.getString(R.string.degrees_symbol), angleType);
    }


    private void configureSeekBar() {

        buttonUtils = new ButtonUtils(activity);
        parentButton = activity.findViewById(R.id.angleButton);

        seekBarConfigurator.configure(R.id.angleSeekBar,
                R.integer.angle_default,
                null,
                progress -> {
                    paintHelperManager.getAngleHelper().setOtherAngle(progress);
                    setAngleParentButtonText(progress);
                },

                progress -> {
                    viewModel.useSeekBarAngle = true;
                    buttonUtils.switchSelection(R.id.angleSeekBar, buttonConfig.getButtonIds());
                });
    }


    private void setAngleParentButtonText(int progress) {
        String buttonText = "" + progress + activity.getString(R.string.degrees_symbol);
        parentButton.setText(buttonText);
    }


    @Override
    public void handleClick(int viewId, AngleType angleType) {
        paintHelperManager.getAngleHelper().setAngle(angleType);
        viewModel.useSeekBarAngle = false;
    }


    @Override
    public void handleDefaultClick(int viewId, AngleType angleType) {
        paintHelperManager.getAngleHelper().setAngle(angleType);
    }


}
