package com.jacstuff.sketchy.controls.settings.placement;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonConfigHandler;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.size.SizeSequenceType;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import static com.jacstuff.sketchy.controls.settings.SettingsUtils.setupSpinner2;

public class PlacementSettings  extends AbstractButtonConfigurator<PlacementType> implements ButtonsConfigurator<PlacementType> {



    public PlacementSettings(MainActivity activity, PaintView paintView) {
        super(activity, paintView);

        childSettingsPanelManager.add(R.id.quantizationButton, R.id.placementMainSettingsPanel);
    }


    @Override
    public void configure() {

        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.PLACEMENT,
                R.id.placementOptionsLayout);
        setupSwitches();

        buttonConfig.add(R.id.normalPlacementButton, R.drawable.button_blur_off, PlacementType.NONE);
        buttonConfig.add(R.id.quantizationButton, R.drawable.button_blur_outer, PlacementType.QUANTIZATION);
        buttonConfig.setParentButton(R.id.placementButton);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.placementButton);
        buttonConfig.setDefaultSelection(R.id.normalPlacementButton);
    }


    @Override
    public void handleClick(int viewId, PlacementType placementType) {
        viewModel.isPlacementQuantizationEnabled = placementType == PlacementType.QUANTIZATION;
    }


    public void setupSwitches(){
        SwitchMaterial repeatSequence = activity.findViewById(R.id.quantizationButtonIncludeLineWidthSwitch);
        repeatSequence.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isPlacementQuantizationEnabled = isChecked);

    }


}