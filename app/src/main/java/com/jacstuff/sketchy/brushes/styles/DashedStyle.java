package com.jacstuff.sketchy.brushes.styles;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class DashedStyle extends AbstractStyle implements Style {

    private PaintGroup paintGroup;
    private final MainViewModel viewModel;

    public DashedStyle(PaintGroup paintGroup, MainViewModel viewModel){
        this.paintGroup = paintGroup;
        this.viewModel = viewModel;
        assignPath();
    }


    @Override
    public void init(PaintGroup paintGroup, int brushSize ) {
        this.paintGroup = paintGroup;
        this.brushSize = brushSize;
        paintGroup.setStyle(Paint.Style.STROKE);
        assignPath();
    }


    private void assignPath(){
        float onStroke = 1 + viewModel.dottedStyleDashLength;
        float offStroke = 2 + viewModel.dottedStyleSpacing;
        paintGroup.setPathEffect(new DashPathEffect(new float[] {onStroke, offStroke}, 0));
    }


    void onDrawAfterSettingsChanged(){
        assignPath();
    }

}


