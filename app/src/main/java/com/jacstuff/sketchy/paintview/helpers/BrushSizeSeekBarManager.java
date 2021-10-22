package com.jacstuff.sketchy.paintview.helpers;

import android.app.Activity;
import android.widget.SeekBar;

import com.jacstuff.sketchy.R;

public class BrushSizeSeekBarManager {

    private final SeekBar brushSizeSeekBar;
    private boolean isStandardSizeModeEnabled;
    private boolean isCurrentShapeAffectedByBrushSize;

    public BrushSizeSeekBarManager(Activity activity){
        brushSizeSeekBar = activity.findViewById(R.id.brushSizeSeekBar);
    }


    public void setCurrentShapeAffectedByBrushSize(boolean b){
        isCurrentShapeAffectedByBrushSize = b;
        updateSeekBarState();
    }


    public void setStandardSizeModeEnabled(boolean b){
        isStandardSizeModeEnabled = b;
        updateSeekBarState();
    }


    private void updateSeekBarState(){
        if(isStandardSizeModeEnabled && isCurrentShapeAffectedByBrushSize){
            brushSizeSeekBar.setEnabled(true);
            return;
        }
        brushSizeSeekBar.setEnabled(false);
    }


}
