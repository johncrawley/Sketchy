package com.jacstuff.sketchy.ui;

import android.app.Activity;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.widget.SeekBar;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ColorPickerSeekBarConfigurator {

    private final Activity activity;
    private final MainViewModel viewModel;


    public ColorPickerSeekBarConfigurator(Activity activity, MainViewModel viewModel){
        this.activity = activity;
        this.viewModel = viewModel;
    }


    public void setupOnCreation(int seekBarId){
        View colorPickerSeekBar = activity.findViewById(seekBarId);
        colorPickerSeekBar.addOnLayoutChangeListener((view, i, i1, i2, i3, i4, i5, i6, i7) -> setupColorPickerSeekbar(seekBarId));
    }

    private void setupColorPickerSeekbar(int seekBarId){
        SeekBar colorPickerSeekBar = activity.findViewById(seekBarId);
        int width = colorPickerSeekBar.getWidth() - (colorPickerSeekBar.getPaddingStart() + colorPickerSeekBar.getPaddingEnd());
        LinearGradient linearGradient = new LinearGradient(0, 0,  width, 0,
                new int[] { 0xFF000000, 0xFF0000FF, 0xFF00FF00, 0xFF00FFFF,
                        0xFFFF0000, 0xFFFF00FF, 0xFFFFFF00, 0xFFFFFFFF},
                null, Shader.TileMode.CLAMP);
        ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setShader(linearGradient);
        colorPickerSeekBar.setProgressDrawable(shape);
        colorPickerSeekBar.setMax(256*7-1);
        Integer savedSeekBarProgress = viewModel.seekBarValue.get(seekBarId);
        if(savedSeekBarProgress != null) {
            colorPickerSeekBar.setProgress(savedSeekBarProgress);
        }
    }
}
