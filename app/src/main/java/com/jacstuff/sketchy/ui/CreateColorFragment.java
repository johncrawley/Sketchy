package com.jacstuff.sketchy.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class CreateColorFragment extends DialogFragment {


    private MainViewModel viewModel;
    private TextView redComponentTextView, greenComponentTextView, blueComponentTextView;
    private LinearLayout colorPreviewLayout;


    public static CreateColorFragment newInstance() {
        return new CreateColorFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_color, container, false);
        Dialog dialog =  getDialog();
        if(getActivity() == null){
            return rootView;
        }
        ViewModelProvider vmp = new ViewModelProvider(getActivity());
        viewModel = vmp.get(MainViewModel.class);

        redComponentTextView = rootView.findViewById(R.id.redComponentTextView);
        greenComponentTextView = rootView.findViewById(R.id.greenComponentTextView);
        blueComponentTextView = rootView.findViewById(R.id.blueComponentTextView);
        colorPreviewLayout = rootView.findViewById(R.id.colorPreviewLayout);

        setupSeekBars(rootView);
        if(dialog != null){
            dialog.setTitle("Configure");
        }

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOkButton(view);
        setupCancelButton(view);
    }


    private void setupOkButton(View parentView){
        Button saveButton = parentView.findViewById(R.id.saveColorButton);
        saveButton.setOnClickListener((View v) -> saveColor(getColorFromComponents()));
    }


    private int getColorFromComponents(){
        int r = getColorFrom(redComponentTextView);
        int g = getColorFrom(greenComponentTextView);
        int b = getColorFrom(blueComponentTextView);
        return Color.argb(255, r, g, b);
    }


    private void setupCancelButton(View parentView){
        Button cancelButton = parentView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener((View v) -> dismiss());
    }


    private void setupSeekBars(View parentView){
        setupSeekBar(parentView, R.id.redComponentSeekBar, R.id.redComponentTextView);
        setupSeekBar(parentView, R.id.greenComponentSeekBar, R.id.greenComponentTextView);
        setupSeekBar(parentView, R.id.blueComponentSeekBar, R.id.blueComponentTextView);
    }


    private void setupSeekBar(View parentView, int seekBarId, int textViewId){
        SeekBar seekBar = parentView.findViewById(seekBarId);
        TextView textView = parentView.findViewById(textViewId);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateComponentTextView(textView, i);
                updateColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void updateComponentTextView(TextView textView, int progress){
        textView.setText(String.valueOf(progress));
    }


    private void updateColor(){
        int color = getColorFromComponents();
        colorPreviewLayout.setBackgroundColor(color);
    }


    private int getColorFrom(TextView textView){
        return Integer.parseInt(textView.getText().toString());
    }


    private void saveColor(int color){
        Context context = getContext();
        if(context == null){
            return;
        }
        if(UserColorStore.save(color, context)){
            createColorAndShadeButtonsFor(color);
            createColorSavedToast();
        }
        else {
            createColorExistsToast();
        }
    }


    private void createColorAndShadeButtonsFor(int color){
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null) {
            activity.addUserGeneratedColorAndShadeButtons(color);

        }
        else{
            System.out.println("^^^CreateColorFragment.createColorAndShadeButtonsFor() activity is null!");
        }
    }


    private void createColorSavedToast(){

    }


    private void createColorExistsToast(){

    }


    public void onDismiss(@NonNull DialogInterface dialog){
        super.onDismiss(dialog);
        Activity activity = getActivity();
    }


}