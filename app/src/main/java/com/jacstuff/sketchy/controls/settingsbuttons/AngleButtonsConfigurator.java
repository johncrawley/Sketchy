package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

public class AngleButtonsConfigurator implements ButtonsConfigurator<AngleType>{

    private MainActivity activity;
    private PaintView paintView;


    public AngleButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }


    public void configure(){
        ButtonConfigHandler<AngleType> buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.ANGLE, R.id.angleOptionsLayout);
        buttonConfig.put(R.id.zeroDegreesButton, R.drawable.zero_degrees_button, AngleType.ZERO);
        buttonConfig.put(R.id.degrees30Button,   R.drawable.degrees_30_button,   AngleType.THIRTY);
        buttonConfig.put(R.id.degrees45Button,   R.drawable.degrees_45_button,   AngleType.FORTY_FIVE);
        buttonConfig.put(R.id.degrees60Button,   R.drawable.degrees_60_button,   AngleType.SIXTY);
        buttonConfig.put(R.id.degrees90Button,   R.drawable.degrees_90_button,   AngleType.NINTY);
        buttonConfig.put(R.id.degrees135Button,  R.drawable.degrees_135_button,  AngleType.ONE_THREE_FIVE);
        buttonConfig.put(R.id.degrees150Button,  R.drawable.degrees_150_button,  AngleType.ONE_FIFTY);
        buttonConfig.put(R.id.degrees180Button,  R.drawable.degrees_180_button,  AngleType.ONE_EIGHTY);
        buttonConfig.put(R.id.degrees225Button,  R.drawable.degrees_225_button,  AngleType.TWO_TWENTY_FIVE);
        buttonConfig.put(R.id.degrees270Button,  R.drawable.degrees_270_button,  AngleType.TWO_SEVENTY);
        buttonConfig.put(R.id.degrees315Button,  R.drawable.degrees_315_button,  AngleType.THREE_FIFTEEN);
        buttonConfig.put(R.id.degreesPlus1Button,  R.drawable.degrees_plus_1_button,  AngleType.PLUS_ONE);
        buttonConfig.put(R.id.degreesMinus1Button, R.drawable.degrees_minus_1_button, AngleType.MINUS_ONE);
        buttonConfig.put(R.id.degreesMinus15Button, R.drawable.degrees_minus_15_button, AngleType.MINUS_FIFTEEN);
      //  buttonConfig.putButtonWithText(R.id.degreesMinus15Button, R.drawable.degrees_minus_15_button, AngleType.MINUS_FIFTEEN, "-15");
        buttonConfig.put(R.id.degreesPlus15Button,  R.drawable.degrees_plus_15_button,  AngleType.PLUS_FIFTEEN);
        buttonConfig.put(R.id.degreesMinus30Button, R.drawable.degrees_minus_30_button, AngleType.MINUS_THIRTY);
        buttonConfig.put(R.id.degreesPlus30Button,  R.drawable.degrees_plus_30_button,  AngleType.PLUS_THIRTY);
        buttonConfig.put(R.id.degreesMinus90Button, R.drawable.degrees_minus_90_button, AngleType.MINUS_NINTY);
        buttonConfig.put(R.id.degreesPlus90Button,  R.drawable.degrees_plus_90_button,  AngleType.PLUS_NINTY);
        buttonConfig.put(R.id.degreesRandomButton,  R.drawable.degrees_random_button,   AngleType.RANDOM);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.angleSelectionButton);
        buttonConfig.setDefaultSelection(R.id.zeroDegreesButton);
    }


    @Override
    public void handleClick(int viewId, AngleType angleType){
        paintView.setAnglePreset(angleType, viewId);
    }
}