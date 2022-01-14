package com.jacstuff.sketchy.controls.settings;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.View;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.ui.SettingsPopup;
import com.jacstuff.sketchy.utils.ActivityUtils;

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
        if(ActivityUtils.isInLandscapeOrientation(activity)){
            buttonConfig.add(R.id.colorMenuButton, R.drawable.button_color,     R.id.includeColorControls);
        }
        buttonConfig.add(R.id.styleButton,     R.drawable.button_style_fill,       R.id.includeStyleControls);
        buttonConfig.add(R.id.gradientButton,  R.drawable.button_gradient_off,     R.id.includeGradientControls);

        buttonConfig.setParentLayout(R.id.controlPanelLayoutGroup2);
        buttonConfig.add(R.id.angleButton,     angleStr,                           R.id.includeAngleControls);
        buttonConfig.add(R.id.blurButton,       R.drawable.button_blur_off,      R.id.includeBlurControls);
        buttonConfig.add(R.id.shadowButton,     R.drawable.button_shadow_off,    R.id.includeShadowControls);

        buttonConfig.setParentLayout(R.id.controlPanelLayoutGroup3);
        buttonConfig.add(R.id.kaleidoscopeButton,   "K: 1", R.id.includeKaleidoscopeControls);
        buttonConfig.add(R.id.sizeSequenceButton, R.drawable.button_size_sequence_stationary,  R.id.includeSizeSequenceControls);
        buttonConfig.add(R.id.placementButton, R.drawable.button_placement_normal,  R.id.includePlacementControls);


        buttonConfig.setParentLayout(activity.getColorButtonLayoutCreator().getMultiShadesLayout());
        buttonConfig.add(R.id.colorConfigButton, R.drawable.button_color_config, R.id.includeColorConfigControls, activity.getColorButtonLayoutParams());

        buttonConfig.setupClickHandler();
        layoutIds = buttonConfig.getEntries();
        buttonConfig.setDefaultSelection(R.id.shapeButton);
    }


    @Override
    public void handleClick(int viewId, Integer layoutId) {
        log("Entered handleClick()");
        hideAllPanels();
        if(settingsPopup != null) {
            log(" settings popup is not null, calling settingsPopup.click()");
            settingsPopup.click(viewId);
        }
        activity.findViewById(layoutId).setVisibility(View.VISIBLE);
        log("Just after setting the view to visible");
    }

    private void log(String msg){
        System.out.println("^^^ MenuButtonsConfigurator: " + msg);
    }

    private void hideAllPanels(){
        for(int layoutId : layoutIds){
            activity.findViewById(layoutId).setVisibility(View.GONE);
        }
    }

}
