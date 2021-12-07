package com.jacstuff.sketchy.controls.settings.placement;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonConfigHandler;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.PlacementHelper;

public class PlacementSettings  extends AbstractButtonConfigurator<PlacementType> implements ButtonsConfigurator<PlacementType> {


    public PlacementSettings(MainActivity activity, PaintView paintView) {
        super(activity, paintView);

        childSettingsPanelManager.add(R.id.quantizationPlacementButton, R.id.placementQuantizationSettingsPanel);
        childSettingsPanelManager.add(R.id.randomPlacementButton, R.id.placementRandomSettingsPanel);

    }


    @Override
    public void configure() {

        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.PLACEMENT,
                R.id.placementOptionsLayout);
        setupSwitches();
        setupSeekBars();

        buttonConfig.add(R.id.normalPlacementButton, R.drawable.button_blur_off, PlacementType.NONE);
        buttonConfig.add(R.id.quantizationPlacementButton, R.drawable.button_blur_outer, PlacementType.QUANTIZATION);
        buttonConfig.add(R.id.randomPlacementButton, R.drawable.button_size_sequence_random, PlacementType.RANDOM);
        buttonConfig.setParentButton(R.id.placementButton);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.placementButton);
        buttonConfig.setDefaultSelection(R.id.normalPlacementButton);
    }


    @Override
    public void handleClick(int viewId, PlacementType placementType) {
        viewModel.placementType = placementType;
        if(childSettingsPanelManager != null){
            childSettingsPanelManager.select(viewId);
        }
    }


    private void setupSeekBars(){
        seekBarConfigurator.configure(R.id.randomPlacementDistanceSeekBar,
                R.integer.placement_random_max_distance_default,
                progress -> {
                    viewModel.randomPlacementMaxDistancePercentage = progress;
                    paintHelperManager.getPlacementHelper().updateMaxRandomDistance();
                } );
    }


    private void setupSwitches(){
        SwitchMaterial isLineWidthIncludedSwitch = activity.findViewById(R.id.quantizationButtonIncludeLineWidthSwitch);
        isLineWidthIncludedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isPlacementQuantizationLineWidthIncluded = isChecked);

        SwitchMaterial isQuantizationLockedSwitch = activity.findViewById(R.id.quantizationIsLockedSwitch);
        isQuantizationLockedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isPlacementQuantizationLocked = isChecked);
    }


}