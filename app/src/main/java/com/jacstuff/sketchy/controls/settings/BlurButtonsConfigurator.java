package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BlurType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

public class BlurButtonsConfigurator extends AbstractButtonConfigurator<BlurType> implements ButtonsConfigurator<BlurType>{


    public BlurButtonsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.BLUR, R.id.blurOptionsLayout);
        buttonConfig.add(R.id.noBlurButton,      R.drawable.button_blur_disabled,      BlurType.NONE);
        buttonConfig.add(R.id.innerBlurButton,   R.drawable.inner_blur_button,   BlurType.INNER);
        buttonConfig.add(R.id.normalBlurButton,  R.drawable.normal_blur_button,  BlurType.NORMAL);
        buttonConfig.add(R.id.outerBlurButton,   R.drawable.outer_blur_button,   BlurType.OUTER);
        buttonConfig.add(R.id.solidBlurButton,   R.drawable.solid_blur_button,   BlurType.SOLID);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.blurButton);
        buttonConfig.setDefaultSelection(R.id.noBlurButton);
    }


    @Override
    public void handleClick(int viewId, BlurType blurType){
        paintHelperManager.getBlurHelper().setBlurType(blurType);
    }


}