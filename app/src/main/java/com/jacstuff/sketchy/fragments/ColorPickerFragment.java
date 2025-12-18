package com.jacstuff.sketchy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.settings.SettingsButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.ui.SettingsPopup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.viewmodel.ViewModelHelper;

public class ColorPickerFragment extends Fragment {

    private PaintView paintView;
    private SettingsPopup settingsPopup;
    private MainViewModel viewModel;
    private SettingsButtonsConfigurator settingsButtonsConfigurator;
    private ViewModelHelper viewModelHelper;
    private PaintHelperManager paintHelperManager;


    public ColorPickerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_color_picker, container, false);
        setupButtons(parent);
        viewModel = getMainActivity().getViewModel();
        viewModelHelper = getMainActivity().getViewModelHelper();

        return parent;
    }




    private void setupButtons(View parent){

    }


    private MainActivity getMainActivity(){
        return (MainActivity) getActivity();
    }
}
