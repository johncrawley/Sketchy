package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;

public class LineWidthSeekBar extends AbstractSeekBarConfig {


    public LineWidthSeekBar(MainActivity mainActivity, PaintView paintView){
        super(mainActivity, paintView, R.id.lineWidthSeekBar);
        paintView.setLineWidth(getValueOf(R.integer.line_width_default));
    }


    void adjustSetting(int progress){
        log("Entered adjustSetting(" + progress + ")");
        if(paintView != null){
            paintView.setLineWidth(progress);
        }
    }

    private void log(String msg){
        System.out.println("LineWidthSeekBar: " + msg);
    }
}