package com.jacstuff.sketchy.controls.settings.rotation;

import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.RotationType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonConfigHandler;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;

public class RotationSettings extends AbstractButtonConfigurator<RotationType> implements ButtonsConfigurator<RotationType> {


    private Button parentButton;
    private ButtonUtils buttonUtils;

    public RotationSettings(MainActivity activity, PaintView paintView){
        super(activity,paintView);
    }

    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.ROTATION, R.id.angleOptionsLayout);


        add(R.id.presetRotationButton, RotationType.PRESET );
        add(R.id.preciseRotationButton, RotationType.PRECISE );
        add(R.id.randomRotationButton, RotationType.RANDOM );
        add(R.id.incrementingRotationButton, RotationType.INCREMENTING );
        add(R.id.waveringRotationButton, RotationType.WAVERING );

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.angleButton);
        buttonConfig.setDefaultSelection(R.id.zeroDegreesButton);

        configureSeekBar();
    }


    private void add(int buttonId, RotationType rotationType){
        //buttonConfig.add(buttonId, rotationType.getStr() + activity.getString(R.string.degrees_symbol), rotationType);
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


    private void setAngleParentButtonText(int progress){
        String buttonText = "" + progress + activity.getString(R.string.degrees_symbol);
        parentButton.setText(buttonText);
    }


    @Override
    public void handleClick(int viewId, RotationType rotationType){
        paintHelperManager.getAngleHelper().setRotationType(rotationType);
    }


    @Override
    public void handleDefaultClick(int viewId, RotationType rotationType){
        paintHelperManager.getAngleHelper().setRotationType(rotationType);
    }


}