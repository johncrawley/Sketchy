package com.jacstuff.sketchy.controls.seekbars;

import android.widget.ImageButton;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.AngleType;
import com.jacstuff.sketchy.paintview.PaintView;

public class AngleSeekBar extends AbstractSeekBarConfig {

    private ImageButton parentButton;

    public AngleSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.angleSeekBar, R.integer.angle_default);
        parentButton = mainActivity.findViewById(R.id.angleSelectionButton);
    }


    void adjustSetting(int progress){
        if(paintView != null){
            paintView.setAnglePreset(AngleType.OTHER);
            paintView.setAngle(progress);
            if(parentButton != null){
                parentButton.setImageResource(R.drawable.degrees_button);
            }
        }
    }
}