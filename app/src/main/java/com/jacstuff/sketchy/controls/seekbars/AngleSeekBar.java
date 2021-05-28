package com.jacstuff.sketchy.controls.seekbars;

import android.widget.Button;
import android.widget.ImageButton;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.paintview.PaintView;

import java.util.Set;

public class AngleSeekBar extends AbstractSeekBarConfig {

    private Button parentButton;
    private ButtonUtils buttonUtils;
    private Set<Integer> buttonIds;


    public AngleSeekBar(MainActivity mainActivity, PaintView paintView, Set<Integer> buttonIds ){
        super(mainActivity, paintView, R.id.angleSeekBar, R.integer.angle_default);
        parentButton = mainActivity.findViewById(R.id.angleSelectionButton);
        buttonUtils = new ButtonUtils(mainActivity);
        this.buttonIds = buttonIds;
    }


    void adjustSetting(int progress){
        if(paintView != null){
            paintView.setAnglePreset(AngleType.OTHER);
            paintView.setAngle(progress);
            if(parentButton != null){
                String buttonText = "" + progress + mainActivity.getString(R.string.degrees_symbol);
                parentButton.setText(buttonText);
                buttonUtils.switchSelection(R.id.angleSeekBar, buttonIds, mainActivity.getSettingsButtonsLayoutParams());
            }
        }
    }
}