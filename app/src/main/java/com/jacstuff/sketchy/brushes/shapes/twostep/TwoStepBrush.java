package com.jacstuff.sketchy.brushes.shapes.twostep;

import android.graphics.PointF;

public interface TwoStepBrush{

    boolean isInFirstStep();
    boolean isInSecondStep();
    void setStateTo(StepState stepState);

    PointF getLineMidPoint();
    PointF getShapeMidPoint();
}
