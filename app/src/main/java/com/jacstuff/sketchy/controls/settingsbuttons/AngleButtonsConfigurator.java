package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.seekbars.AngleSeekBar;
import com.jacstuff.sketchy.paintview.PaintView;

public class AngleButtonsConfigurator extends AbstractButtonConfigurator<AngleType> implements ButtonsConfigurator<AngleType>{


    public AngleButtonsConfigurator(MainActivity activity, PaintView paintView){
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
        add(R.id.degreesMinus90Button, AngleType.MINUS_NINTY);
        add(R.id.degreesPlus90Button, AngleType.PLUS_NINTY);
        add(R.id.degreesRandomButton, AngleType.RANDOM);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.angleSelectionButton);
        buttonConfig.setDefaultSelection(R.id.zeroDegreesButton);

        new AngleSeekBar(activity, paintView, buttonConfig.getButtonIds());
    }




    private void add(int buttonId, AngleType angleType){
        buttonConfig.add(buttonId, angleType, angleType.getStr() + activity.getString(R.string.degrees_symbol));
    }


    @Override
    public void handleClick(int viewId, AngleType angleType){
        paintView.setAnglePreset(angleType, viewId);
    }
}