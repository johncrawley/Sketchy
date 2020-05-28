package com.jacstuff.sketchy.controls.brushSize;

import android.content.Context;
import android.widget.SeekBar;

import com.jacstuff.sketchy.BrushShape;
import com.jacstuff.sketchy.BrushStyle;
import com.jacstuff.sketchy.R;

public class BrushSizeConfig {
    private Context context;
    private SeekBar brushSizeSeekBar;
    private BrushStyle brushStyle;
    private BrushShape brushShape;
    private BrushSizeProfile defaultProfile, lineOutlineProfile;

    public BrushSizeConfig(Context context, SeekBar seekBar, BrushShape defaultBrushShape, BrushStyle defaultBrushStyle){
        this.context = context;
        this.brushSizeSeekBar = seekBar;
        this.brushShape = defaultBrushShape;
        this.brushStyle = defaultBrushStyle;
        setupBrushSizeProfiles();
    }

    private void setupBrushSizeProfiles(){
        int min = getInteger(R.integer.brush_size_min_default);
        int max = getInteger(R.integer.brush_size_max_default);
        defaultProfile = new BrushSizeProfile(min, max);

        int min2 = getInteger(R.integer.brush_size_min_line_outline);
        int max2 = getInteger(R.integer.brush_size_max_line_outline);
        lineOutlineProfile = new BrushSizeProfile(min2, max2);
    }

    private int getInteger(int dimenId){
        return context.getResources().getInteger(dimenId);
    }


    public void set(BrushShape brushShape){
        this.brushShape = brushShape;
        adjustBrushSizeLimitsIfNecessary();
    }

    public void set(BrushStyle brushStyle){
        this.brushStyle = brushStyle;
        adjustBrushSizeLimitsIfNecessary();
    }


    private void adjustBrushSizeLimitsIfNecessary(){
        if(brushShape == BrushShape.LINE && brushStyle == BrushStyle.OUTLINE){
            lineOutlineProfile.applyTo(brushSizeSeekBar);
            return;
        }
        defaultProfile.applyTo(brushSizeSeekBar);
    }
}
