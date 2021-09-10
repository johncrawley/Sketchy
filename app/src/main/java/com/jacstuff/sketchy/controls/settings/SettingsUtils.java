package com.jacstuff.sketchy.controls.settings;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.core.util.Consumer;

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
}
