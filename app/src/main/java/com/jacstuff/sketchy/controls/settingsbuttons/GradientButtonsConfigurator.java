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
        ButtonClickHandler<GradientType> clickHandler = new ButtonClickHandler<>(activity, this);
        clickHandler.put(R.id.noGradientButton, GradientType.NONE);
        clickHandler.put(R.id.diagonalMirrorGradientButton, GradientType.DIAGONAL_MIRROR);
        clickHandler.setupClickHandler();
        clickHandler.setDefaultSelection(R.id.noGradientButton);
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