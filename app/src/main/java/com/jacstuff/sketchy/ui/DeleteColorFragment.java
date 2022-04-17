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

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class DeleteColorFragment extends DialogFragment {


    private MainViewModel viewModel;
    private final int color;


    public static DeleteColorFragment newInstance(int color) {
        return new DeleteColorFragment(color);
    }

    public DeleteColorFragment(int color){
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_delete_color, container, false);
        Dialog dialog =  getDialog();
        if(getActivity() == null){
            return rootView;
        }
        ViewModelProvider vmp = new ViewModelProvider(getActivity());
        viewModel = vmp.get(MainViewModel.class);


        if(dialog != null){
            dialog.setTitle("Remove custom colour");
        }

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupDeleteButton(view);
        setupCancelButton(view);
    }


    private void setupDeleteButton(View parentView){
        Button saveButton = parentView.findViewById(R.id.deleteColorButton);
        saveButton.setOnClickListener((View v) -> deleteColor(color));
    }


    private void setupCancelButton(View parentView){
        Button cancelButton = parentView.findViewById(R.id.cancelDeleteColorButton);
        cancelButton.setOnClickListener((View v) -> dismiss());
    }


    private void updateComponentTextView(TextView textView, int progress){
        textView.setText(String.valueOf(progress));
    }


    private void deleteColor(int color){
        Context context = getContext();
        if(context == null){
            return;
        }
        UserColorStore.delete(color, context);
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