package com.jacstuff.sketchy.viewmodel.controls;

import com.jacstuff.sketchy.multicolor.ColorSequenceType;

public class ColorSequenceControls {

    public ColorSequenceType colorSequenceType = ColorSequenceType.FORWARDS;
    public int colorSequenceMinPercentage = 80;
    public int colorSequenceMaxPercentage = 100;
    public int multiShadeBrightnessPercentage = 50;
    public int skippedShades = 1;
    public boolean doesRepeat = true;
    public boolean isResetOnRelease = true;
    public boolean areAllShadesUsed = false;
    public boolean isBlackAndWhitePreserved = true;
}
