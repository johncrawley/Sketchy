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
        buttonConfig.add(R.id.noShadowButton,             R.drawable.button_shadow_off,             ShadowType.NONE);
        buttonConfig.add(R.id.centreShadowButton,         R.drawable.button_shadow_centre,          ShadowType.CENTER);
        buttonConfig.add(R.id.northShadowButton,          R.drawable.button_shadow_north,           ShadowType.NORTH);
        buttonConfig.add(R.id.northEastShadowButton,      R.drawable.button_shadow_north_east,      ShadowType.NORTH_EAST);
        buttonConfig.add(R.id.eastShadowButton,           R.drawable.button_shadow_east,            ShadowType.EAST);
        buttonConfig.add(R.id.southEastShadowButton,      R.drawable.button_shadow_south_east,      ShadowType.SOUTH_EAST);
        buttonConfig.add(R.id.southShadowButton,          R.drawable.button_shadow_south,           ShadowType.SOUTH);
        buttonConfig.add(R.id.southWestShadowButton,      R.drawable.button_shadow_south_west,      ShadowType.SOUTH_WEST);
        buttonConfig.add(R.id.westShadowButton,           R.drawable.button_shadow_west,            ShadowType.WEST);
        buttonConfig.add(R.id.northWestShadowButton,      R.drawable.button_shadow_north_west,      ShadowType.NORTH_WEST);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.shadowButton);
        buttonConfig.setDefaultSelection(R.id.noShadowButton);
    }


    @Override
    public void handleClick(int viewId, ShadowType shadowType){
        paintHelperManager.getShadowHelper().setType(shadowType);
    }


}