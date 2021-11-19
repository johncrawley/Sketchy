package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BlurType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

public class BlurSettings extends AbstractButtonConfigurator<BlurType> implements ButtonsConfigurator<BlurType>{


    public BlurSettings(MainActivity activity, PaintView paintView){
        super(activity, paintView);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.BLUR, R.id.blurOptionsLayout);
        buttonConfig.add(R.id.noBlurButton,      R.drawable.button_blur_off,     BlurType.NONE);
        buttonConfig.add(R.id.innerBlurButton,   R.drawable.button_blur_inner,   BlurType.INNER);
        buttonConfig.add(R.id.normalBlurButton,  R.drawable.button_blur_normal,  BlurType.NORMAL);
        buttonConfig.add(R.id.outerBlurButton,   R.drawable.button_blur_outer,   BlurType.OUTER);
        buttonConfig.add(R.id.solidBlurButton,   R.drawable.button_blur_solid,   BlurType.SOLID);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.blurButton);
        buttonConfig.setDefaultSelection(R.id.noBlurButton);

        configureSeekBar();
    }


    @Override
    public void handleClick(int viewId, BlurType blurType){
        paintHelperManager.getBlurHelper().setBlurType(blurType);
    }


    private void configureSeekBar(){
        seekBarConfigurator.configure( R.id.blurSeekBar,
                R.integer.blur_radius_default,
                progress -> paintHelperManager.getBlurHelper().setBlurRadius(progress));

    }

}