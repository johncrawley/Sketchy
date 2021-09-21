package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.controls.settings.gradient.GradientSizeSeekBar;
import com.jacstuff.sketchy.paintview.PaintView;

import java.util.Arrays;

public class SeekBarConfigurator {


    public SeekBarConfigurator(MainActivity mainActivity, PaintView paintView){
        //new LineWidthSeekBar(mainActivity, paintView);
        new BrushSizeSeekBar(mainActivity, paintView);
        new BlurSeekBar(mainActivity, paintView);
        new ShadowRadiusSeekBar(mainActivity, paintView);

        setupIgnoreLayouts(mainActivity);
    }


    private void setupIgnoreLayouts(MainActivity mainActivity){
        for(int id : Arrays.asList(R.id.gradientSettingsLayout,
                R.id.shadowSettingsLayout,
                R.id.blurSettingsLayout,
                R.id.styleSettingsLayout,
                R.id.kaleidoscopeSettingsLayout,
                R.id.angleSettingsLayout
                )){
            mainActivity.getSettingsPopup().registerToIgnore(id);
        }
    }
}
