package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.GradientType;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;

public class GradientButtonsConfigurator implements ButtonsConfigurator<GradientType>{

    private MainActivity activity;
    private PaintView paintView;


    public GradientButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }


    public void configure(){
        ButtonConfigHandler<GradientType> buttonConfig = new ButtonConfigHandler<>(activity, this);
        buttonConfig.put(R.id.noGradientButton,             R.drawable.no_gradient_button,              GradientType.NONE);
        buttonConfig.put(R.id.diagonalMirrorGradientButton, R.drawable.diagonal_mirror_gradient_button, GradientType.DIAGONAL_MIRROR);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.gradientSelectionButton);
        buttonConfig.setDefaultSelection(R.id.noGradientButton);
    }


    @Override
    public void handleClick(int viewId, GradientType gradientType){
        paintView.set(gradientType);
    }


    @Override
    public void saveSelection(int viewId){
        PaintViewSingleton.getInstance().saveShapeSelectionSetting(viewId);
    }

}