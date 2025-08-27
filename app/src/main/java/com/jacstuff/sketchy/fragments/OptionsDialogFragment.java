package com.jacstuff.sketchy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import java.util.function.Consumer;

public class OptionsDialogFragment extends DialogFragment{


    public static OptionsDialogFragment newInstance() {
        return new OptionsDialogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.options_dialog, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupButtons(view);
    }


    private void setupButtons(View parentView){
        setupButton(parentView, R.id.openButton, ()-> doOnMainActivity(MainActivity::startDialogForOpenDocument));

    }


    private void setupButton(View parentView, int resId, Runnable onClick){
        Button button = parentView.findViewById(resId);
        button.setOnClickListener(v -> onClick.run());
    }


    private void doOnMainActivity(Consumer<MainActivity> consumer){
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity != null){
            consumer.accept(mainActivity);
        }
    }


    private String getStr(int resId){
        return getResources().getString(resId);
    }


}
