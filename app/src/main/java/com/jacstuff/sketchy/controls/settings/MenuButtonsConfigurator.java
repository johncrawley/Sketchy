package com.jacstuff.sketchy.controls.settings;

import android.view.View;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.ui.SettingsPopup;
import java.util.Set;

public class MenuButtonsConfigurator extends AbstractButtonConfigurator<Integer> implements ButtonsConfigurator<Integer> {

    private Set<Integer> layoutIds;
    private final SettingsPopup settingsPopup;


    public MenuButtonsConfigurator(MainActivity activity){
        super(activity, null);
        settingsPopup = activity.getSettingsPopup();
        settingsPopup.registerParentButton(R.id.colorConfigButton);
    }

    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.CATEGORIES,
                R.id.controlPanelLayoutGroup1);

        String angleStr = "0" + activity.getString(R.string.degrees_symbol);

        buttonConfig.add(R.id.shapeButton,     R.drawable.button_shape_circle,     R.id.includeShapeControls);
        buttonConfig.add(R.id.styleButton,     R.drawable.button_style_fill,       R.id.includeStyleControls);
        buttonConfig.add(R.id.gradientButton,  R.drawable.button_gradient_off,     R.id.includeGradientControls);
        buttonConfig.add(R.id.angleButton,     angleStr,                           R.id.includeAngleControls);

        buttonConfig.setParentLayout(R.id.controlPanelLayoutGroup2);
        buttonConfig.add(R.id.blurButton,       R.drawable.button_blur_off,      R.id.includeBlurControls);
        buttonConfig.add(R.id.shadowButton,     R.drawable.button_shadow_off,    R.id.includeShadowControls);
        buttonConfig.add(R.id.kaleidoButton,   "K: 1",                      R.id.includeKaleidoscopeControls);
        buttonConfig.add(R.id.sizeSequenceButton, R.drawable.button_size_sequence_stationary,  R.id.includeSizeSequenceControls);

        buttonConfig.setParentLayout(R.id.colorButtonGroup);
        buttonConfig.add(R.id.colorConfigButton, R.drawable.button_color_config, R.id.includeColorConfigControls, activity.getColorButtonLayoutParams());

        buttonConfig.setupClickHandler();
        layoutIds = buttonConfig.getEntries();
        buttonConfig.setDefaultSelection(R.id.shapeButton);
    }


    @Override
    public void handleClick(int viewId, Integer layoutId) {
        hideAllPanels();
        System.out.println("MenuButtonsConfigurator.handleClick() settingsPopup==null: " + (settingsPopup == null));
        if(settingsPopup != null) {
            settingsPopup.click(viewId);
        }
        activity.findViewById(layoutId).setVisibility(View.VISIBLE);
    }


    private void hideAllPanels(){
        for(int layoutId : layoutIds){
            activity.findViewById(layoutId).setVisibility(View.GONE);
        }
    }

}
