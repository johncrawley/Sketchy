package com.jacstuff.sketchy.controls.seekbars;

import android.widget.Button;
import android.widget.ImageButton;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.controls.ButtonUtils;
import com.jacstuff.sketchy.paintview.PaintView;

public class AngleSeekBar extends AbstractSeekBarConfig {

    private Button parentButton;
    private ButtonUtils buttonUtils;

    public AngleSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.angleSeekBar, R.integer.angle_default);
        parentButton = mainActivity.findViewById(R.id.angleSelectionButton);
        buttonUtils = new ButtonUtils(mainActivity);
    }


    void adjustSetting(int progress){
        if(paintView != null){
            paintView.setAnglePreset(AngleType.OTHER);
            paintView.setAngle(progress);
            if(parentButton != null){
                parentButton.setBackgroundResource(R.drawable.degrees_button);
                buttonUtils.deselectButton(paintView.getCurrentAngleButtonViewId(), mainActivity.getSettingsButtonsLayoutParams());
            }
        }
    }
}