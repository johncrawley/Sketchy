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
    }

    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.CATEGORIES,
                R.id.controlPanelLayoutGroup1);

        ButtonConfigHandler<Integer> buttonConfigSet2 = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.CATEGORIES,
                R.id.controlPanelLayoutGroup2);

        String angleStr = "0" + activity.getString(R.string.degrees_symbol);

        buttonConfig.add(R.id.shapeButton,     R.drawable.button_shape_circle,     R.id.includeShapeControls);
        buttonConfig.add(R.id.styleButton,     R.drawable.fill_style_button,       R.id.includeStyleControls);
        buttonConfig.add(R.id.gradientButton,  R.drawable.button_gradient_disabled,R.id.includeGradientControls);
        buttonConfig.add(R.id.angleButton,     angleStr,                           R.id.includeAngleControls);
        buttonConfigSet2.add(R.id.blurButton,      R.drawable.button_blur_disabled,    R.id.includeBlurControls);
        buttonConfigSet2.add(R.id.shadowButton,    R.drawable.button_shadow_disabled,  R.id.includeShadowControls);
        buttonConfigSet2.add(R.id.kaleidoButton,   R.drawable.k_off_button,            R.id.includeKaleidoScopeControls);
        buttonConfig.setupClickHandler();
        buttonConfigSet2.setupClickHandler();
        layoutIds = buttonConfig.getEntries();
        layoutIds.addAll(buttonConfigSet2.getEntries());
        buttonConfig.setDefaultSelection(R.id.shapeButton);
    }


    @Override
    public void handleClick(int viewId, Integer layoutId) {
        hideAllPanels();
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
