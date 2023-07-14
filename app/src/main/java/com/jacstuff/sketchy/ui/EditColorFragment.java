package com.jacstuff.sketchy.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class EditColorFragment extends DialogFragment {

    private TextView redComponentTextView, greenComponentTextView, blueComponentTextView;
    private int initialRedComponent, initialGreenComponent, initialBlueComponent;
    private LinearLayout colorPreviewLayout;
    public final static String ORIGINAL_COLOR_TAG = "amended_color";
    public final static String COLOR_INDEX_TAG = "color_index";
    private int originalColor;
    private int colorIndex;
    private MainActivity activity;


    public static EditColorFragment newInstance() {
        return new EditColorFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_color, container, false);
        Dialog dialog =  getDialog();
        Bundle bundle = getArguments();
        if(getActivity() == null || bundle == null){
            return rootView;
        }
        activity = (MainActivity)getActivity();
        originalColor = bundle.getInt(ORIGINAL_COLOR_TAG);
        colorIndex = bundle.getInt(COLOR_INDEX_TAG);
        assignInitialColorComponents();
        setupComponentTextViews(rootView);
        setupColorPreview(rootView);

        setupSeekBars(rootView);
        if(dialog != null){
            dialog.setTitle(activity.getString(R.string.edit_color_dialog_title));
        }

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOkButton(view);
        setupCancelButton(view);
    }


    private void assignInitialColorComponents(){
        initialRedComponent = Color.red(originalColor);
        initialGreenComponent = Color.green(originalColor);
        initialBlueComponent = Color.blue(originalColor);
    }


    private void setupComponentTextViews(View rootView){
        redComponentTextView = assignTextView(rootView, R.id.redComponentTextView, initialRedComponent);
        greenComponentTextView = assignTextView(rootView, R.id.greenComponentTextView, initialGreenComponent);
        blueComponentTextView = assignTextView(rootView, R.id.blueComponentTextView, initialBlueComponent);
    }


    private TextView assignTextView(View rootView, int textViewId, int initialValue){
        TextView textView = rootView.findViewById(textViewId);
        String text = String.valueOf(initialValue);
        textView.setText(text);
        return textView;
    }


    private void setupColorPreview(View rootView){
        colorPreviewLayout = rootView.findViewById(R.id.colorPreviewLayout);
        colorPreviewLayout.setBackgroundColor(originalColor);
    }


    private void setupOkButton(View parentView){
        Button saveButton = parentView.findViewById(R.id.saveColorButton);
        saveButton.setOnClickListener((View v) -> {
            editColor(getColorFromComponents());
            dismiss();
        });
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
        setupSeekBar(parentView, R.id.redComponentSeekBar, R.id.redComponentTextView, initialRedComponent);
        setupSeekBar(parentView, R.id.greenComponentSeekBar, R.id.greenComponentTextView, initialGreenComponent);
        setupSeekBar(parentView, R.id.blueComponentSeekBar, R.id.blueComponentTextView, initialBlueComponent);
    }


    private void setupSeekBar(View parentView, int seekBarId, int textViewId, int colorComponent){
        SeekBar seekBar = parentView.findViewById(seekBarId);
        seekBar.setProgress(colorComponent);
        //seekBar.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
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


    private void editColor(int color){
        Context context = getContext();
        if(context == null){
            return;
        }
        UserColorStore.editColor(color, colorIndex, context);
        activity.replaceColorButton(colorIndex, color);
    }

}