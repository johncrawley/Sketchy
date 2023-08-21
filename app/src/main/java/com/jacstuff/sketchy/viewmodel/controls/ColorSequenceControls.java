package com.jacstuff.sketchy.viewmodel.controls;

import com.jacstuff.sketchy.multicolor.ColorSequenceType;

public class ColorSequenceControls {

    public ColorSequenceType colorSequenceType = ColorSequenceType.RANDOM;
    public int colorSequenceSpinnerSavedPosition = 0;
    public int colorSequenceMinPercentage = 1;
    public int colorSequenceMaxPercentage = 100;
    public int multiShadeBrightnessPercentage = 50;
    public int skippedShades = 1;
    public boolean doesRepeat = true;
    public boolean isResetOnRelease = true;
    public boolean isBlackAndWhitePreserved = true;
}
