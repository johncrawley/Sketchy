package com.jacstuff.sketchy.controls.settings.placement;

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
        subPanelManager.add(R.id.offsetPlacementButton, R.id.placementOffsetSettingsPanel);
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
        buttonConfig.add(R.id.offsetPlacementButton, R.drawable.button_placement_offset, PlacementType.OFFSET);
        buttonConfig.add(R.id.quantizationPlacementButton, R.drawable.button_placement_quantization, PlacementType.QUANTIZATION);
        buttonConfig.add(R.id.randomPlacementButton, R.drawable.button_placement_random, PlacementType.RANDOM);
        buttonConfig.setParentButton(R.id.placementButton);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.placementButton);
        buttonConfig.setDefaultSelection(R.id.placementLockPanel);
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
                progress -> viewModel.quantizationPlacementSpacing = progress);

        seekBarConfigurator.configure(R.id.placementOffsetXSeekBar,
                R.integer.placement_offset_x_default,
                progress -> viewModel.placementOffsetX = progress);

        seekBarConfigurator.configure(R.id.placementOffsetYSeekBar,
                R.integer.placement_offset_y_default,
                progress -> viewModel.placementOffsetY = progress);
    }


    private void setupSwitches(){
        setupSwitch(R.id.quantizationButtonIncludeLineWidthSwitch, b -> viewModel.isPlacementQuantizationLineWidthIncluded = b);
        setupSwitch(R.id.quantizationIsLockedSwitch, b ->  paintHelperManager.getPlacementHelper().setQuantizationLock(b));
        setupSwitch(R.id.lockHorizontalSwitch, b -> viewModel.isPlacementHorizontalLocked = b);
        setupSwitch(R.id.lockVerticalSwitch, b -> viewModel.isPlacementVerticalLocked = b);
    }

}