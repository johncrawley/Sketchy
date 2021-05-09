package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.settings.PaintViewSingleton;

import java.util.Collection;

public class SelectionButtonsConfigurator implements ButtonsConfigurator<Integer> {

    private MainActivity activity;
    private Collection<Integer> layoutIds;


    public SelectionButtonsConfigurator(MainActivity activity){
        this.activity = activity;
        configure();
    }


    public void configure(){
        ButtonConfigHandler<Integer> buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.CATEGORIES);
        buttonConfig.put(R.id.gradientSelectionButton,  R.id.includeGradientControls);
        buttonConfig.put(R.id.shapeSelectionButton,     R.id.includeShapeControls);
        buttonConfig.put(R.id.styleSelectionButton,     R.id.includeStyleControls);
        buttonConfig.put(R.id.angleSelectionButton,     R.id.includeAngleControls);
        buttonConfig.put(R.id.blurSelectionButton,      R.id.includeBlurControls);
        buttonConfig.put(R.id.shadowSelectionButton,    R.id.includeShadowControls);
        buttonConfig.put(R.id.kaleidoscopeSelectionButton, R.id.includeKaleidoScopeControls);
        buttonConfig.put(R.id.angleSelectionButton,     R.id.includeAngleControls);
        buttonConfig.setupClickHandler();
        layoutIds = buttonConfig.getEntries();
        buttonConfig.setDefaultSelection(R.id.shapeSelectionButton);
    }


    @Override
    public void handleClick(int viewId, Integer layoutId) {
        hideAllPanels();
        activity.findViewById(layoutId).setVisibility(View.VISIBLE);
        PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
        paintViewSingleton.saveSelectedCategoryButton(viewId);
    }


    private void hideAllPanels(){
        for(int layoutId : layoutIds){
            activity.findViewById(layoutId).setVisibility(View.INVISIBLE);
        }
    }



}
