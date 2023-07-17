package com.jacstuff.sketchy.fragments;

import static com.jacstuff.sketchy.controls.settings.SettingsUtils.setupSpinner;
import static com.jacstuff.sketchy.controls.settings.SettingsUtils.setupSwitch;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.seekbars.SeekBarConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.multicolor.ColorSequenceType;
import com.jacstuff.sketchy.multicolor.SequenceColorSelector;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;

public class ColorSettingsDialogFragment extends DialogFragment implements ButtonsConfigurator<Void> {

    private Map<String, ColorSequenceType> sequenceTypeMap;
    private MainViewModel viewModel;
    private PaintHelperManager paintHelperManager;
    private SeekBarConfigurator seekBarConfigurator;

    public static ColorSettingsDialogFragment newInstance() {
        return new ColorSettingsDialogFragment();
    }

    @Override
    public void onClick(int viewId, Void actionType){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.controls_color_config, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configure();
        initSequenceTypeMap();
    }


    public void configure(){
        Activity activity = getActivity();
        if(activity == null){
            return;
        }
        MainActivity mainActivity = (MainActivity)activity;
        paintHelperManager = mainActivity.getPaintHelperManager();
        viewModel = mainActivity.getViewModel();
        seekBarConfigurator = new SeekBarConfigurator(mainActivity);
        //ButtonConfigHandler<Void> buttonConfig = new ButtonConfigHandler<>(mainActivity, this, ButtonCategory.COLOR_CONFIG, R.id.colorConfigLayout);

        View parentView = getView();
        setupSeekBars(parentView);
        setupSpinners(mainActivity, parentView);
        setupSwitches(parentView);
       // buttonConfig.setParentButton(R.id.colorMenuButton);
    }


    @Override
    public void handleClick(int viewId, Void actionType) {
        //do nothing
    }

    @Override
    public void handleDefaultClick(int viewId, Void actionType) {

    }


    private void setupSpinners(MainActivity activity, View parentView){

        setupSpinner(getContext(), parentView,
                R.id.colorSequenceTypeSpinner,
                R.array.color_sequence_type_array,
                viewModel.getColorSequenceControls().colorSequenceSpinnerSavedPosition,
                (str, i ) -> {
                    ColorSequenceType type = sequenceTypeMap.get(str);
                    activity.getPaintHelperManager().getColorHelper().getAllColorsSequenceSelector().setSequenceType(type);
                    setVisibilityOnBlendSeekBar(activity, type);
                    viewModel.getColorSequenceControls().colorSequenceSpinnerSavedPosition = i;
                } );

    }


    private void setVisibilityOnBlendSeekBar(MainActivity activity, ColorSequenceType selectedColorSequenceType){
        View blendControl = activity.findViewById(R.id.colorSequenceGradationSeekBar);
        View blendControlLabel = activity.findViewById(R.id.colorSequenceGradationSeekBarLabel);
        if(blendControl != null){
            int visibility = selectedColorSequenceType == ColorSequenceType.BLEND ? View.VISIBLE : View.INVISIBLE;
            blendControl.setVisibility(visibility);
            blendControlLabel.setVisibility(visibility);
        }
    }

    private void log(String msg){
        System.out.println("^^^ ColorSettingsDialogFragment: " + msg);
    }


    private void setupSeekBars(View parentView){
        SequenceColorSelector allColorsSequenceSelector = paintHelperManager.getColorHelper().getAllColorsSequenceSelector();
        ColorSelector shadeColorSelector = paintHelperManager.getColorHelper().getShadeColorSelector();

        seekBarConfigurator.configureForFragment( parentView, R.id.multiShadeBrightnessSeekBar,
                viewModel.getColorSequenceControls().multiShadeBrightnessPercentage,
                progress -> {
                    viewModel.getColorSequenceControls().multiShadeBrightnessPercentage = Math.max(1, progress);
                    log("multishadebrightness seek bar adjusted to  : " + progress);

                } );

        seekBarConfigurator.configure(R.id.colorSequenceMaxIndexSeekBar,
                R.integer.seek_bar_color_sequence_max_range_default,
                progress -> {
                    viewModel.getColorSequenceControls().colorSequenceMaxPercentage = progress;
                    shadeColorSelector.updateRangeIndexes();
                });

        seekBarConfigurator.configure(R.id.colorSequenceMinIndexSeekBar,
                R.integer.seek_bar_color_sequence_min_range_default,
                progress -> {
                    viewModel.getColorSequenceControls().colorSequenceMinPercentage = progress;
                    shadeColorSelector.updateRangeIndexes();
                });

        seekBarConfigurator.configure(R.id.colorSequenceGradationSeekBar,
                R.integer.seek_bar_color_sequence_gradation_default,
                progress -> {
                    viewModel.getColorSequenceControls().skippedShades = 1 + progress;
                    allColorsSequenceSelector.updateRangeIndexes();
                });
    }


    private void setupSwitches(View parentView){
        setupSwitch(parentView, R.id.colorSequenceRepeatSwitch, b -> viewModel.getColorSequenceControls().doesRepeat = b);
        setupSwitch(parentView, R.id.colorSequenceResetOnReleaseSwitch, b -> viewModel.getColorSequenceControls().isResetOnRelease = b);
    }


    private void initSequenceTypeMap(){
        sequenceTypeMap = new HashMap<>();
        put(sequenceTypeMap, R.string.spinner_item_color_sequence_forwards, ColorSequenceType.FORWARDS);
        put(sequenceTypeMap, R.string.spinner_item_color_sequence_backwards, ColorSequenceType.BACKWARDS);
        put(sequenceTypeMap, R.string.spinner_item_color_sequence_over_and_back, ColorSequenceType.STROBE);
        put(sequenceTypeMap, R.string.spinner_item_color_sequence_random, ColorSequenceType.RANDOM);
        put(sequenceTypeMap, R.string.spinner_item_color_sequence_blend, ColorSequenceType.BLEND);
    }


    private <T> void put(Map<String, T> map, int id, T item){
        String str = getStr(id);
        map.put(str, item);
    }


    private String getStr(int resId){
        return getResources().getString(resId);
    }


}
