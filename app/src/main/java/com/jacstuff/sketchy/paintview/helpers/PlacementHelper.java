package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.Random;

public class PlacementHelper {

    private final MainViewModel viewModel;
    private final PaintView paintView;
    private Paint paint;
    private Random random;

    public PlacementHelper(PaintView paintView, MainViewModel viewModel){
        this.paintView = paintView;
        this.viewModel = viewModel;
        random = new Random(System.currentTimeMillis());
    }


    public void init(Paint paint){
        this.paint = paint;
    }


    public float getX(float x){
        switch (viewModel.placementType){
            case QUANTIZATION:
                return (viewModel.brushSize/2f) + x - (x % (viewModel.brushSize + getLineSizeFactor()));
            case RANDOM:
                return getRandomX();
        }
        return x;
    }

    private float getRandomX(){
        return random.nextInt(paintView.getWidth());
    }


    private float getRandomY(){
        return random.nextInt(paintView.getHeight());
    }


    public float getY(float y){
        switch (viewModel.placementType){
            case QUANTIZATION:
                return (viewModel.brushSize/2f) + y - (y % (viewModel.brushSize + getLineSizeFactor()));
            case RANDOM:
                return getRandomY();
        }
        return y;
    }


    public float getLineSizeFactor(){
       return viewModel.isPlacementQuantizationLineWidthIncluded ? paint.getStrokeWidth() : 0;
    }
}
