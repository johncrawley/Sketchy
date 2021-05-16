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
        ButtonConfigHandler<Integer> buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.CATEGORIES,
                R.id.controlPanelLayout);
        buttonConfig.put(R.id.shapeSelectionButton, R.drawable.square_shape_button,    R.id.includeShapeControls);
        buttonConfig.put(R.id.styleSelectionButton,  R.drawable.fill_style_button,      R.id.includeStyleControls);
        buttonConfig.put(R.id.gradientSelectionButton, R.drawable.no_gradient_button,  R.id.includeGradientControls);
        buttonConfig.put(R.id.angleSelectionButton, R.drawable.zero_degrees_button,     R.id.includeAngleControls);
        buttonConfig.put(R.id.blurSelectionButton,  R.drawable.no_blur_button,          R.id.includeBlurControls);
        buttonConfig.put(R.id.shadowSelectionButton, R.drawable.no_shadow_button,       R.id.includeShadowControls);
        buttonConfig.put(R.id.kaleidoscopeSelectionButton, R.drawable.k_off_button,     R.id.includeKaleidoScopeControls);
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
