package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.twostep.TwoStepBrush;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ArbitraryTriangleDrawer extends TwoStepDrawer implements Drawer{

    public ArbitraryTriangleDrawer(PaintView paintView, MainViewModel viewModel, TwoStepBrush twoStepBrush){
        super(paintView, viewModel, twoStepBrush);
    }

    @Override
    PointF getShapeMidpoint(PointF down, PointF up, boolean isUsingKaleidoscopeOffsets){
        PointF midPoint = twoStepBrush.getShapeMidPoint();
        midPoint.x -= getKaleidoscopeOffsetX(isUsingKaleidoscopeOffsets);
        midPoint.y -= getKaleidoscopeOffsetY(isUsingKaleidoscopeOffsets);
        return midPoint;
    }

}

