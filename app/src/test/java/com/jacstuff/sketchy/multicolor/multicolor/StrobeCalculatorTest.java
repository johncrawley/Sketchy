package com.jacstuff.sketchy.multicolor.multicolor;

import com.jacstuff.sketchy.multicolor.StrobeCalculator;
import com.jacstuff.sketchy.viewmodel.ControlsHolder;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.viewmodel.controls.ColorSequenceControls;

import org.junit.Before;
import org.junit.Test;


public class StrobeCalculatorTest {

    StrobeCalculator strobeCalculator;
    ColorSequenceControls colorSequenceControls = new ColorSequenceControls();

    @Before
    public void setup(){
        ControlsHolder viewModel = new MainViewModel();
        viewModel.setColorSequenceControls(colorSequenceControls);
        strobeCalculator = new StrobeCalculator(viewModel);
    }


    @Test
    public void nextIndexWillBeWithinLimits(){

        colorSequenceControls.colorSequenceMaxPercentage = 100;
        colorSequenceControls.colorSequenceMinPercentage = 50;

        colorSequenceControls.skippedShades = 10;
        int maxIndex = 12;
        int nextIndex = strobeCalculator.getNextStrobeIndex(10, 0, maxIndex);
        assert(nextIndex < maxIndex);

        colorSequenceControls.skippedShades = 11;
        nextIndex = strobeCalculator.getNextStrobeIndex(15, 14, 18);
        assert(nextIndex < maxIndex);
    }
}
