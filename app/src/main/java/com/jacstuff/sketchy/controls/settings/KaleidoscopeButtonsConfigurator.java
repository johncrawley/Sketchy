package com.jacstuff.sketchy.controls.settings;

import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;

public class KaleidoscopeButtonsConfigurator extends AbstractButtonConfigurator<Integer> implements ButtonsConfigurator<Integer>{


    public KaleidoscopeButtonsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
    }

    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.KALEIDOSCOPE,
                R.id.kaleidoscopeOptionsLayout);
        buttonConfig.add(R.id.kOffButton, R.drawable.k_off_button,1);
        buttonConfig.add(R.id.k2Button,   R.drawable.k_2_button,  2);
        buttonConfig.add(R.id.k5Button,   R.drawable.k_5_button,  5);
        buttonConfig.add(R.id.k6Button,   R.drawable.k_6_button,  6);
        buttonConfig.add(R.id.k7Button,   R.drawable.k_7_button,  7);
        buttonConfig.add(R.id.k8Button,   R.drawable.k_8_button,  8);
        buttonConfig.add(R.id.k9Button,   R.drawable.k_9_button,  9);
        buttonConfig.add(R.id.k10Button,  R.drawable.k_10_button, 10);
        buttonConfig.add(R.id.k12Button,  R.drawable.k_12_button, 12);
        buttonConfig.add(R.id.k16Button,  R.drawable.k_16_button, 16);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.kaleidoscopeSelectionButton);
        buttonConfig.setDefaultSelection(R.id.kOffButton);
        setupKaleidoscopeOptions();
    }


    public void setupKaleidoscopeOptions(){
       SwitchMaterial kaleidoscopeGlitchMode = activity.findViewById(R.id.kaleidoscopeGlitchMode);
       kaleidoscopeGlitchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               viewModel.isGlitchModeEnabled = isChecked;
           }
       });


        SwitchMaterial kaleidoscopeCentredSwitch = activity.findViewById(R.id.kaleidoscopeCentredSwitch);
        kaleidoscopeCentredSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.isKaleidoscopeCentred = isChecked;
            }
        });
    }


    @Override
    public void handleClick(int viewId, Integer numberOfSegments){
        paintView.setKaleidoscopeSegments(numberOfSegments);
    }

}