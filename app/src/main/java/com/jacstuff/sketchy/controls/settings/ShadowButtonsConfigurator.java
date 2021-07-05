package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.ShadowType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

public class ShadowButtonsConfigurator extends AbstractButtonConfigurator<ShadowType> implements ButtonsConfigurator<ShadowType>{


    public ShadowButtonsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
    }


    @Override
    public void configure(){
       buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.SHADOW,
                R.id.shadowOptionsLayout);
        buttonConfig.add(R.id.noShadowButton,             R.drawable.button_shadow_disabled,        ShadowType.NONE);
        buttonConfig.add(R.id.centreShadowButton,         R.drawable.center_shadow_button,          ShadowType.CENTER);
        buttonConfig.add(R.id.northShadowButton,          R.drawable.north_shadow_button,           ShadowType.NORTH);
        buttonConfig.add(R.id.northEastShadowButton,      R.drawable.north_east_shadow_button,      ShadowType.NORTH_EAST);
        buttonConfig.add(R.id.eastShadowButton,           R.drawable.east_shadow_button,            ShadowType.EAST);
        buttonConfig.add(R.id.southEastShadowButton,      R.drawable.south_east_shadow_button,      ShadowType.SOUTH_EAST);
        buttonConfig.add(R.id.southShadowButton,          R.drawable.south_shadow_button,           ShadowType.SOUTH);
        buttonConfig.add(R.id.southWestShadowButton,      R.drawable.south_west_shadow_button,      ShadowType.SOUTH_WEST);
        buttonConfig.add(R.id.westShadowButton,           R.drawable.west_shadow_button,            ShadowType.WEST);
        buttonConfig.add(R.id.northWestShadowButton,      R.drawable.north_west_shadow_button,      ShadowType.NORTH_WEST);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.shadowSelectionButton);
        buttonConfig.setDefaultSelection(R.id.noShadowButton);
    }


    @Override
    public void handleClick(int viewId, ShadowType shadowType){
        paintHelperManager.getShadowHelper().setType(shadowType);
    }


}