package com.jacstuff.sketchy.controls.seekbars;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.ui.SettingsPopup;

public class SeekBarConfigurator {


    public SeekBarConfigurator(MainActivity mainActivity, PaintView paintView){
        new LineWidthSeekBar(mainActivity, paintView);
        new BrushSizeSeekBar(mainActivity, paintView);
        new GradientSizeSeekBar(mainActivity, paintView);
        new BlurSeekBar(mainActivity, paintView);
        new ShadowRadiusSeekBar(mainActivity, paintView);

        setupIgnoreLayouts(mainActivity);
    }

    private void setupIgnoreLayouts(MainActivity mainActivity){
        SettingsPopup settingsPopup = mainActivity.getSettingsPopup();
        settingsPopup.registerToIgnore(R.id.gradientSettingsLayout);
        settingsPopup.registerToIgnore(R.id.shadowSettingsLayout);
        settingsPopup.registerToIgnore(R.id.blurSettingsLayout);
        settingsPopup.registerToIgnore(R.id.styleSettingsLayout);
        settingsPopup.registerToIgnore(R.id.kaleidoscopeSettingsLayout);
        settingsPopup.registerToIgnore(R.id.angleSettingsLayout);
    }
}
