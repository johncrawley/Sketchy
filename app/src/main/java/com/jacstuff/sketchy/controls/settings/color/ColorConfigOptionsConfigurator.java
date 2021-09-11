package com.jacstuff.sketchy.controls.settings.color;

import android.widget.SeekBar;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.seekbars.SimpleSeekBar;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonConfigHandler;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.multicolor.ColorSequenceType;
import com.jacstuff.sketchy.multicolor.SequenceColorSelector;
import com.jacstuff.sketchy.paintview.PaintView;

import java.util.HashMap;
import java.util.Map;

import static com.jacstuff.sketchy.controls.settings.SettingsUtils.setupSpinner;

public class ColorConfigOptionsConfigurator  extends AbstractButtonConfigurator<Void> implements ButtonsConfigurator<Void> {

    private Map<String, ColorSequenceType> sequenceTypeMap;

    public ColorConfigOptionsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        initSequenceTypeMap();
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity, this, ButtonCategory.COLOR_CONFIG, R.id.colorConfigLayout);
        SequenceColorSelector sequenceColorSelector = paintHelperManager.getColorHelper().getSequenceColorSelector();
        new ColorTransparencySeekBar(activity, paintView);

       new SimpleSeekBar(activity,
                        R.id.colorSequenceMaxIndexSeekBar,
                        R.integer.seek_bar_color_sequence_max_range_default,
                        progress -> {
                            viewModel.getColorSequenceControls().colorSequenceMaxPercentage = progress;
                            sequenceColorSelector.updateRangeIndexes();
                        });

        new SimpleSeekBar(activity,
                R.id.colorSequenceMinIndexSeekBar,
                R.integer.seek_bar_color_sequence_min_range_default,
                progress -> {
                    viewModel.getColorSequenceControls().colorSequenceMinPercentage = progress;
                    SeekBar maxRangeSeekBar = activity.findViewById(R.id.colorSequenceMaxIndexSeekBar);
                    sequenceColorSelector.updateRangeIndexes();
                });

        new SimpleSeekBar(activity,
                R.id.colorSequenceStepsSeekBar,
                R.integer.seek_bar_color_sequence_steps_default,
                progress -> {
                    viewModel.getColorSequenceControls().skippedShades = 1 + progress;
                    sequenceColorSelector.updateRangeIndexes();
                });

        setupSpinner(activity,
                R.id.colorSequenceTypeSpinner,
                R.array.color_sequence_type_array,
                x -> paintHelperManager.getColorHelper().getSequenceColorSelector().setSequenceType(sequenceTypeMap.get(x)));

        setupSwitches();
    }


    @Override
    public void handleClick(int viewId, Void actionType) {
        //do nothing
    }

    private void setupSwitches(){

        SwitchMaterial colorSequenceRepeatSwitch = activity.findViewById(R.id.colorSequenceRepeatSwitch);
        colorSequenceRepeatSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.getColorSequenceControls().doesRepeat = isChecked);


        SwitchMaterial colorSequenceResetOnReleaseSwitch = activity.findViewById(R.id.colorSequenceResetOnReleaseSwitch);
        colorSequenceResetOnReleaseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.getColorSequenceControls().isResetOnRelease = isChecked);
    }


    private void initSequenceTypeMap(){
        sequenceTypeMap = new HashMap<>();
        put(sequenceTypeMap, R.string.spinner_item_color_sequence_forwards, ColorSequenceType.FORWARDS);
        put(sequenceTypeMap, R.string.spinner_item_color_sequence_backwards, ColorSequenceType.BACKWARDS);
        put(sequenceTypeMap, R.string.spinner_item_color_sequence_strobe, ColorSequenceType.STROBE);
        put(sequenceTypeMap, R.string.spinner_item_color_sequence_random, ColorSequenceType.RANDOM);
    }


    private <T> void put(Map<String, T> map, int id, T item){
        String str = getStr(id);
        map.put(str, item);
    }


    private String getStr(int resId){
        return activity.getResources().getString(resId);
    }



}
