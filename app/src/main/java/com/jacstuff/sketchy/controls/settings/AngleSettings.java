package com.jacstuff.sketchy.controls.settings;

import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.paintview.PaintView;

public class AngleSettings extends AbstractButtonConfigurator<AngleType> implements ButtonsConfigurator<AngleType>{

    private Button parentButton;
    private ButtonUtils buttonUtils;

    public AngleSettings(MainActivity activity, PaintView paintView){
        super(activity,paintView);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.ANGLE, R.id.angleOptionsLayout);
        add(R.id.zeroDegreesButton, AngleType.ZERO );
        add(R.id.degrees30Button, AngleType.THIRTY );
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
        if(!viewModel.useSeekBarAngle) {
            buttonConfig.setDefaultSelection(R.id.zeroDegreesButton);
        }
        configureSeekBar();
    }


    private void add(int buttonId, AngleType angleType){
        buttonConfig.add(buttonId, angleType.getStr() + activity.getString(R.string.degrees_symbol), angleType);
    }


    private void configureSeekBar(){

        buttonUtils = new ButtonUtils(activity);
        parentButton = activity.findViewById(R.id.angleButton);

        seekBarConfigurator.configure(R.id.angleSeekBar,
                R.integer.angle_default,
                null,
                progress -> {
                    paintHelperManager.getAngleHelper().setOtherAngle(progress);
                    setAngleParentButtonText(progress);
                },

                progress-> {
                    viewModel.useSeekBarAngle = true;
                    buttonUtils.switchSelection(R.id.angleSeekBar, buttonConfig.getButtonIds());
                });
    }


    @Override
    public void handleClick(int viewId, AngleType angleType){
        paintHelperManager.getAngleHelper().setAngle(angleType);
        viewModel.useSeekBarAngle = false;
    }


    @Override
    public void handleDefaultClick(int viewId, AngleType angleType){
        if(angleType == null){
            angleType = AngleType.OTHER;
        }
        paintHelperManager.getAngleHelper().setAngle(angleType);
    }


    private void setAngleParentButtonText(int progress){
        String buttonText = "" + progress + activity.getString(R.string.degrees_symbol);
        parentButton.setText(buttonText);
    }
}