package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class PlacementHelper {

    private final MainViewModel viewModel;
    private Paint paint;

    public PlacementHelper(MainViewModel viewModel){
        this.viewModel = viewModel;
    }


    public void init(Paint paint){
        this.paint = paint;
    }


    public float getX(float x){
        if(viewModel.isPlacementQuantizationEnabled){
            return (viewModel.brushSize/2f) + x - (x % (viewModel.brushSize + getLineSizeFactor()));
        }
        return x;
    }


    public float getY(float y){
        if(viewModel.isPlacementQuantizationEnabled){
            return (viewModel.brushSize/2f) + y - (y % (viewModel.brushSize + getLineSizeFactor()));
        }
        return y;
    }


    public float getLineSizeFactor(){
       return viewModel.isPlacementQuantizationLineWidthIncluded ? paint.getStrokeWidth() : 0;
    }
}
