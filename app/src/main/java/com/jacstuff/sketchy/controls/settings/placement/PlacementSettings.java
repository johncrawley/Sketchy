package com.jacstuff.sketchy.controls.settings.placement;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonConfigHandler;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;

public class PlacementSettings  extends AbstractButtonConfigurator<PlacementType> implements ButtonsConfigurator<PlacementType> {


    public PlacementSettings(MainActivity activity, PaintView paintView) {
        super(activity, paintView);
        subPanelManager.add(R.id.quantizationPlacementButton, R.id.placementQuantizationSettingsPanel);
        subPanelManager.add(R.id.randomPlacementButton, R.id.placementRandomSettingsPanel);
        subPanelManager.registerButtonWithoutPanel(R.id.normalPlacementButton);
    }


    @Override
    public void configure() {

        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.PLACEMENT,
                R.id.placementOptionsLayout);
        setupSwitches();
        setupSeekBars();

        buttonConfig.add(R.id.normalPlacementButton, R.drawable.button_placement_normal, PlacementType.NORMAL);
        buttonConfig.add(R.id.quantizationPlacementButton, R.drawable.button_placement_quantization, PlacementType.QUANTIZATION);
        buttonConfig.add(R.id.randomPlacementButton, R.drawable.button_placement_random, PlacementType.RANDOM);
        buttonConfig.setParentButton(R.id.placementButton);

        buttonConfig.setupClickHandler();
        buttonConfig.setDefaultSelection(R.id.placementLockPanel);
        buttonConfig.setParentButton(R.id.placementButton);
        buttonConfig.setDefaultSelection(R.id.normalPlacementButton);
    }


    @Override
    public void handleClick(int viewId, PlacementType placementType) {
        viewModel.placementType = placementType;
    }


    private void setupSeekBars(){
        seekBarConfigurator.configure(R.id.randomPlacementDistanceSeekBar,
                R.integer.placement_random_max_distance_default,
                progress -> {
                    viewModel.randomPlacementMaxDistancePercentage = progress;
                    paintHelperManager.getPlacementHelper().updateMaxRandomDistance();
                } );

        seekBarConfigurator.configure(R.id.placementQuantizationSpacingSeekBar,
                R.integer.quantization_spacing_default,
                progress -> {
                    viewModel.quantizationPlacementSpacing = progress;
                } );
    }


    private void setupSwitches(){
        SwitchMaterial isLineWidthIncludedSwitch = activity.findViewById(R.id.quantizationButtonIncludeLineWidthSwitch);
        isLineWidthIncludedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isPlacementQuantizationLineWidthIncluded = isChecked);

        SwitchMaterial isQuantizationLockedSwitch = activity.findViewById(R.id.quantizationIsLockedSwitch);
        isQuantizationLockedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> paintHelperManager.getPlacementHelper().setQuantizationLock(isChecked));


        SwitchMaterial lockHorizontalSwitch = activity.findViewById(R.id.lockHorizontalSwitch);
        lockHorizontalSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isPlacementHorizontalLocked = isChecked);


        SwitchMaterial lockVerticalSwitch = activity.findViewById(R.id.lockVerticalSwitch);
        lockVerticalSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isPlacementVerticalLocked = isChecked);
    }


}