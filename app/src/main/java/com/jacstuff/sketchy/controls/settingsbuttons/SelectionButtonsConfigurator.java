package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.settings.PaintViewSingleton;
import com.jacstuff.sketchy.ui.SettingsPopup;

import java.util.Collection;

public class SelectionButtonsConfigurator implements ButtonsConfigurator<Integer> {

    private MainActivity activity;
    private Collection<Integer> layoutIds;
    private SettingsPopup settingsPopup;


    public SelectionButtonsConfigurator(MainActivity activity){
        this.activity = activity;
        configure();
        settingsPopup = activity.getSettingsPopup();
    }


    public void configure(){
        ButtonConfigHandler<Integer> buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.CATEGORIES,
                R.id.controlPanelLayout);
        buttonConfig.add(R.id.shapeSelectionButton,     R.drawable.circle_shape_button,     R.id.includeShapeControls);
        buttonConfig.add(R.id.styleSelectionButton,     R.drawable.fill_style_button,       R.id.includeStyleControls);
        buttonConfig.add(R.id.gradientSelectionButton,  R.drawable.button_gradient_disabled,      R.id.includeGradientControls);
        buttonConfig.add(R.id.angleSelectionButton, R.id.includeAngleControls, "0" + activity.getString(R.string.degrees_symbol));
        buttonConfig.add(R.id.blurSelectionButton,      R.drawable.button_blur_disabled,    R.id.includeBlurControls);
        buttonConfig.add(R.id.shadowSelectionButton,    R.drawable.button_shadow_disabled,        R.id.includeShadowControls);
        buttonConfig.add(R.id.kaleidoscopeSelectionButton, R.drawable.k_off_button,         R.id.includeKaleidoScopeControls);
        buttonConfig.setupClickHandler();
        layoutIds = buttonConfig.getEntries();
        buttonConfig.setDefaultSelection(R.id.shapeSelectionButton);
    }


    @Override
    public void handleClick(int viewId, Integer layoutId) {
        hideAllPanels();
        if(settingsPopup != null) {
            settingsPopup.click(viewId);
        }
        activity.findViewById(layoutId).setVisibility(View.VISIBLE);
        PaintViewSingleton paintViewSingleton = PaintViewSingleton.getInstance();
        paintViewSingleton.saveSelectedCategoryButton(viewId);

    }


    private void hideAllPanels(){
        for(int layoutId : layoutIds){
            activity.findViewById(layoutId).setVisibility(View.GONE);
        }
    }


}
