package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.ShadowType;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;

public class KaleidoscopeButtonsConfigurator implements ButtonsConfigurator<Integer>{

    private MainActivity activity;
    private PaintView paintView;


    public KaleidoscopeButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }


    public void configure(){
        ButtonConfigHandler<Integer> buttonConfig = new ButtonConfigHandler<>(activity, this);
        buttonConfig.put(R.id.kOffButton, R.drawable.k_off_button,1);
        buttonConfig.put(R.id.k2Button,   R.drawable.k_2_button,  2);
        buttonConfig.put(R.id.k5Button,   R.drawable.k_5_button,  5);
        buttonConfig.put(R.id.k6Button,   R.drawable.k_6_button,  6);
        buttonConfig.put(R.id.k7Button,   R.drawable.k_7_button,  7);
        buttonConfig.put(R.id.k8Button,   R.drawable.k_8_button,  8);
        buttonConfig.put(R.id.k9Button,   R.drawable.k_9_button,  9);
        buttonConfig.put(R.id.k10Button,  R.drawable.k_10_button, 10);
        buttonConfig.put(R.id.k12Button,  R.drawable.k_12_button, 12);
        buttonConfig.put(R.id.k16Button,  R.drawable.k_16_button, 16);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.kaleidoscopeSelectionButton);
        buttonConfig.setDefaultSelection(R.id.kOffButton);
        setupKaleidoScopeOptions();
    }

    public void setupKaleidoScopeOptions(){
       Switch kaleidoscopeCentredSwitch = activity.findViewById(R.id.kaleidoscopeCentredSwitch);
       kaleidoscopeCentredSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               paintView.setKaleidoscopeFixed(isChecked);
           }
       });
    }


    @Override
    public void handleClick(int viewId, Integer numberOfSegments){
        paintView.setKaleidoScopeSegments(numberOfSegments);
    }


    @Override
    public void saveSelection(int viewId){
        PaintViewSingleton.getInstance().saveShapeSelectionSetting(viewId);
    }

}