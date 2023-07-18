package com.jacstuff.sketchy.controls.settings;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.switchmaterial.SwitchMaterial;

import androidx.core.util.Consumer;

import java.util.function.BiConsumer;

public class SettingsUtils {

    public static void setupSpinner(Activity activity, int spinnerId, int itemsArrayId, final Consumer<String> paintAction){
        Spinner spinner = activity.findViewById(spinnerId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                itemsArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)adapterView.getItemAtPosition(i);
                paintAction.accept(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }


    public static void setupSpinner(Context context, View parentView, int spinnerId, int itemsArrayId, int selectedPosition, final BiConsumer<String, Integer> paintAction){
        Spinner spinner = parentView.findViewById(spinnerId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                itemsArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)adapterView.getItemAtPosition(i);
                paintAction.accept(item, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedListener);
        spinner.setSelection(selectedPosition);
    }


    public static void setupSpinnerWithLabels(Activity activity, int spinnerId, int itemsArrayId, int valuesArrayId, final Consumer<String> paintAction){
        Spinner spinner = activity.findViewById(spinnerId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                itemsArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] size_values = activity.getResources().getStringArray(valuesArrayId);
                paintAction.accept(size_values[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }


    public static void setupSwitch(View parentView, int switchId, Consumer<Boolean> consumer){
        SwitchMaterial switchMaterial = parentView.findViewById(switchId);
        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> consumer.accept(isChecked));
    }

}
