package com.jacstuff.sketchy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.fragment.app.Fragment;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.controls.settings.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.ui.ColorPickerSeekBarConfigurator;
import com.jacstuff.sketchy.ui.ConnectedBrushIconModifierHelper;
import com.jacstuff.sketchy.ui.SettingsPopup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.viewmodel.ViewModelHelper;

public class DrawFragment extends Fragment {

    private PaintView paintView;
    private SettingsPopup settingsPopup;
    private MainViewModel viewModel;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private ViewModelHelper viewModelHelper;


    public DrawFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_draw, container, false);
        setupButtons(parent);
        return parent;
    }


    private void setupPaintViewAndDefaultSelections(View parentView){
        paintView = parentView.findViewById(R.id.paintView);
      //  connectedBrushIconModifierHelper = new ConnectedBrushIconModifierHelper(this);
        //TODO: remove main activity refs from BrushFactory and all classes referenced within
      //  var brushFactory = new BrushFactory(this);
        final var linearLayout = parentView.findViewById(R.id.paintViewLayout);
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
             //   paintView.init(settingsPopup, brushFactory, viewModel);
                settingsButtonsConfigurator.selectDefaults();
                viewModelHelper.onResume();
            }
        });
    }

    private ColorPickerSeekBarConfigurator colorPickerSeekBarConfigurator;

    private void setupColorPickerSeekBars(){
        colorPickerSeekBarConfigurator.setupOnCreation(R.id.gradientColorPickerSeekBar);
        colorPickerSeekBarConfigurator.setupOnCreation(R.id.shadowColorPickerSeekBar);
    }

    public void setupButtons(View parent){

    }

}
