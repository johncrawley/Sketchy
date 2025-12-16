package com.jacstuff.sketchy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.fragment.app.Fragment;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushFactory;
import com.jacstuff.sketchy.controls.settings.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
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
    private PaintHelperManager paintHelperManager;


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
        viewModel = getMainActivity().getViewModel();
        viewModelHelper = getMainActivity().getViewModelHelper();
        setupPaintViewAndDefaultSelections(parent);
        return parent;
    }


    private void setupPaintViewAndDefaultSelections(View parentView){
        paintView = parentView.findViewById(R.id.paintView);
        var paintHelperManager = getMainActivity().getPaintHelperManager();
        paintHelperManager.initBrushSizeManager(parentView);
        viewModelHelper.setPaintView(paintView);
        // paintView.setPaintHelperManager(paintHelperManager);
      //  connectedBrushIconModifierHelper = new ConnectedBrushIconModifierHelper(this);
        var brushFactory = new BrushFactory(getMainActivity());
        final var linearLayout = parentView.findViewById(R.id.paintViewLayout);
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
              //  paintView.init(brushFactory, viewModel, paintHelperManager);
                paintView.init(viewModel);
              //  paintHelperManager.setPaintView(paintView, getContext());
               // settingsButtonsConfigurator.selectDefaults();
                viewModelHelper.onResume();
            }
        });
    }


    private ColorPickerSeekBarConfigurator colorPickerSeekBarConfigurator;

    private void setupColorPickerSeekBars(){
        colorPickerSeekBarConfigurator.setupOnCreation(R.id.gradientColorPickerSeekBar);
        colorPickerSeekBarConfigurator.setupOnCreation(R.id.shadowColorPickerSeekBar);
    }


    private MainActivity getMainActivity(){
        return (MainActivity) getActivity();
    }


    public void setupButtons(View parent){

    }

}
