package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.ShadowType;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.utils.ColorUtils;

public class ShadowSettings extends AbstractButtonConfigurator<ShadowType> implements ButtonsConfigurator<ShadowType>{


    public ShadowSettings(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        subPanelManager.setOffButtonAndDefaultLayout(R.id.noShadowButton, R.id.shadowMainSettingsLayout);
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

        configureSeekBars();
    }


    @Override
    public void handleClick(int viewId, ShadowType shadowType){
        paintHelperManager.getShadowHelper().setType(shadowType);
    }


    private void configureSeekBars(){
        seekBarConfigurator.configure( R.id.shadowRadiusSeekBar,
                R.integer.shadow_radius_default,
                progress -> paintHelperManager.getShadowHelper().setShadowSize(progress));

        seekBarConfigurator.configure( R.id.shadowDistanceSeekBar,
                R.integer.shadow_distance_default,
                progress -> paintHelperManager.getShadowHelper().setShadowDistance(progress));

        seekBarConfigurator.configure(R.id.shadowIntensitySeekBar,
                R.integer.shadow_intensity_default,
                progress -> {
                    viewModel.shadowIntensity = progress;
                    paintHelperManager.getShadowHelper().setShadowLayer();
                });
        seekBarConfigurator.configure(R.id.shadowColorPickerSeekBar,
                R.integer.shadow_color_default_progress,
                progress -> {
                    viewModel.shadowColor = ColorUtils.getColorFromSlider(progress);
                    paintHelperManager.getShadowHelper().setShadowLayer();
                });
    }

}