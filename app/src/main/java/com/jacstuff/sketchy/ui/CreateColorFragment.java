package com.jacstuff.sketchy.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CreateColorFragment extends DialogFragment {


    private MainViewModel viewModel;
    private TextView redComponentTextView, greenComponentTextView, blueComponentTextView;


    public static CreateColorFragment newInstance() {
        return new CreateColorFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_color_panel, container, false);
        Dialog dialog =  getDialog();
        if(getActivity() == null){
            return rootView;
        }
        ViewModelProvider vmp = new ViewModelProvider(getActivity());
        viewModel = vmp.get(MainViewModel.class);

        redComponentTextView = rootView.findViewById(R.id.redComponentTextView);
        greenComponentTextView = rootView.findViewById(R.id.greenComponentTextView);
        blueComponentTextView = rootView.findViewById(R.id.blueComponentTextView);

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
        saveButton.setOnClickListener((View v) -> {
            int r = getColorFrom(redComponentTextView);
            int g = getColorFrom(greenComponentTextView);
            int b = getColorFrom(blueComponentTextView);
            saveColor(r,g,b);
        });
    }


    private void setupCancelButton(View parentView){
        Button cancelButton = parentView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener((View v) -> dismiss());
    }


    private int getColorFrom(TextView textView){
        return Integer.parseInt(textView.getText().toString());
    }


    private void saveColor(int r, int g, int b){
        Context context = getContext();
        if(context == null){
            return;
        }
        String prefsName = context.getString(R.string.shared_prefs_name);
        SharedPreferences prefs = context.getSharedPreferences(prefsName,0);
        String savedColorsPrefName = context.getString(R.string.shared_prefs_name);
        Set<String> savedColors = prefs.getStringSet(savedColorsPrefName, Collections.emptySet());
        assert savedColors != null;
        Set<String> savedColorSet = new HashSet<>(savedColors);
        String color = String.valueOf(Color.argb(0, r, g, b));
        if (!savedColorSet.contains(color)) {
            savedColorSet.add(color);
            prefs.edit().putStringSet(savedColorsPrefName, savedColorSet).apply();

        } else {
            createColorExistsToast();
        }
    }


    private void createColorExistsToast(){

    }


    public void onDismiss(@NonNull DialogInterface dialog){
        Activity activity = getActivity();

    }


}