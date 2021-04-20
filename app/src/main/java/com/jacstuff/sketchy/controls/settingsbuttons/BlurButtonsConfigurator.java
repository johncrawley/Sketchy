package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BlurType;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;

public class BlurButtonsConfigurator implements ButtonsConfigurator<BlurType>{

    private MainActivity activity;
    private PaintView paintView;


    public BlurButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }


    public void configure(){
        ButtonConfigHandler<BlurType> buttonConfig = new ButtonConfigHandler<>(activity, this);
        buttonConfig.put(R.id.noBlurButton,             R.drawable.no_gradient_button,              BlurType.NONE);
        buttonConfig.put(R.id.innerBlurButton,          R.drawable.diagonal_mirror_gradient_button, BlurType.INNER);
        buttonConfig.put(R.id.normalBlurButton,         R.drawable.gradient_radial_clamp_button,    BlurType.NORMAL);
        buttonConfig.put(R.id.outerBlurButton,   R.drawable.gradient_radial_repeat_button,   BlurType.OUTER);
        buttonConfig.put(R.id.solidBlurButton,   R.drawable.gradient_radial_mirror_button,   BlurType.SOLID);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.blurSelectionButton);
        buttonConfig.setDefaultSelection(R.id.noBlurButton);
    }


    @Override
    public void handleClick(int viewId, BlurType blurType){
        paintView.set(blurType);
    }


    @Override
    public void saveSelection(int viewId){
        PaintViewSingleton.getInstance().saveShapeSelectionSetting(viewId);
    }

}