package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

public class AngleButtonsConfigurator implements ButtonsConfigurator<AngleType>{

    private MainActivity activity;
    private PaintView paintView;
    private ButtonConfigHandler<AngleType> buttonConfig;


    public AngleButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }




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

        buttonConfig.add(R.id.degreesPlus1Button,  R.drawable.degrees_plus_1_button,  AngleType.PLUS_ONE);
        buttonConfig.add(R.id.degreesMinus1Button, R.drawable.degrees_minus_1_button, AngleType.MINUS_ONE);
        buttonConfig.add(R.id.degreesMinus15Button, R.drawable.degrees_minus_15_button, AngleType.MINUS_FIFTEEN);
      //  buttonConfig.putButtonWithText(R.id.degreesMinus15Button, R.drawable.degrees_minus_15_button, AngleType.MINUS_FIFTEEN, "-15");
        buttonConfig.add(R.id.degreesPlus15Button,  R.drawable.degrees_plus_15_button,  AngleType.PLUS_FIFTEEN);
        buttonConfig.add(R.id.degreesMinus30Button, R.drawable.degrees_minus_30_button, AngleType.MINUS_THIRTY);
        buttonConfig.add(R.id.degreesPlus30Button,  R.drawable.degrees_plus_30_button,  AngleType.PLUS_THIRTY);
        buttonConfig.add(R.id.degreesMinus90Button, R.drawable.degrees_minus_90_button, AngleType.MINUS_NINTY);
        buttonConfig.add(R.id.degreesPlus90Button,  R.drawable.degrees_plus_90_button,  AngleType.PLUS_NINTY);
        buttonConfig.add(R.id.degreesRandomButton,  R.drawable.degrees_random_button,   AngleType.RANDOM);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.angleSelectionButton);
        buttonConfig.setDefaultSelection(R.id.zeroDegreesButton);
    }

    private void add(int buttonId, AngleType angleType, int angleNumber){
        buttonConfig.add(buttonId, angleType, getAngleFor(angleNumber));
    }


    private void add(int buttonId, AngleType angleType){
        buttonConfig.add(buttonId, angleType, getAngleFor(angleType.get()));
    }


    private String getAngleFor(int angle){
        return "" + angle;
    }


    @Override
    public void handleClick(int viewId, AngleType angleType){
        paintView.setAnglePreset(angleType, viewId);
    }
}