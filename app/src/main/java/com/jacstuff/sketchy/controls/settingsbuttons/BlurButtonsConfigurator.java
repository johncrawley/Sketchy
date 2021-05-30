package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BlurType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

public class BlurButtonsConfigurator implements ButtonsConfigurator<BlurType>{

    private MainActivity activity;
    private PaintView paintView;


    public BlurButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }


    public void configure(){
        ButtonConfigHandler<BlurType> buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.BLUR, R.id.blurOptionsLayout);
        buttonConfig.add(R.id.noBlurButton,      R.drawable.button_blur_disabled,      BlurType.NONE);
        buttonConfig.add(R.id.innerBlurButton,   R.drawable.inner_blur_button,   BlurType.INNER);
        buttonConfig.add(R.id.normalBlurButton,  R.drawable.normal_blur_button,  BlurType.NORMAL);
        buttonConfig.add(R.id.outerBlurButton,   R.drawable.outer_blur_button,   BlurType.OUTER);
        buttonConfig.add(R.id.solidBlurButton,   R.drawable.solid_blur_button,   BlurType.SOLID);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.blurSelectionButton);
        buttonConfig.setDefaultSelection(R.id.noBlurButton);
    }


    @Override
    public void handleClick(int viewId, BlurType blurType){
        paintView.setBlurType(blurType);
    }


}