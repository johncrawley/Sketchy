package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.ShadowType;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;

public class ShadowButtonsConfigurator implements ButtonsConfigurator<ShadowType>{

    private MainActivity activity;
    private PaintView paintView;


    public ShadowButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }


    public void configure(){
        ButtonConfigHandler<ShadowType> buttonConfig = new ButtonConfigHandler<>(activity, this);
        buttonConfig.put(R.id.noShadowButton,             R.drawable.no_shadow_button,              ShadowType.NONE);
        buttonConfig.put(R.id.centreShadowButton,         R.drawable.center_shadow_button,          ShadowType.CENTER);
        buttonConfig.put(R.id.northShadowButton,          R.drawable.north_shadow_button,           ShadowType.NORTH);
        buttonConfig.put(R.id.northEastShadowButton,      R.drawable.north_east_shadow_button,      ShadowType.NORTH_EAST);
        buttonConfig.put(R.id.eastShadowButton,           R.drawable.east_shadow_button,            ShadowType.EAST);
        buttonConfig.put(R.id.southEastShadowButton,      R.drawable.south_east_shadow_button,      ShadowType.SOUTH_EAST);
        buttonConfig.put(R.id.southShadowButton,          R.drawable.south_shadow_button,           ShadowType.SOUTH);
        buttonConfig.put(R.id.southWestShadowButton,      R.drawable.south_west_shadow_button,      ShadowType.SOUTH_WEST);
        buttonConfig.put(R.id.westShadowButton,           R.drawable.west_shadow_button,            ShadowType.WEST);
        buttonConfig.put(R.id.northWestShadowButton,      R.drawable.north_west_shadow_button,      ShadowType.NORTH_WEST);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.shadowSelectionButton);
        buttonConfig.setDefaultSelection(R.id.noShadowButton);
    }


    @Override
    public void handleClick(int viewId, ShadowType shadowType){
        paintView.set(shadowType);
    }


    @Override
    public void saveSelection(int viewId){
        PaintViewSingleton.getInstance().saveShapeSelectionSetting(viewId);
    }

}