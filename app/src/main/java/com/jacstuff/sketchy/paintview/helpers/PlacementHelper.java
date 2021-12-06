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
                return getRandomX(x);
        }
        return x;
    }



    private float getRandomY(float y){
        return ( y-viewModel.randomPlacementMaxDistanceY) + (2 * (random.nextInt((int)viewModel.randomPlacementMaxDistanceY)));
    }


    private float getRandomX(float x){
        return (x -viewModel.randomPlacementMaxDistanceX) + (2 * (random.nextInt((int)viewModel.randomPlacementMaxDistanceX)));
    }


    public float getY(float y){
        switch (viewModel.placementType){
            case QUANTIZATION:
                return (viewModel.brushSize/2f) + y - (y % (viewModel.brushSize + getLineSizeFactor()));
            case RANDOM:
                return getRandomY(y);
        }
        return y;
    }


    public void updateMaxRandomDistance(){
        viewModel.randomPlacementMaxDistanceX = 2 + (paintView.getWidth() / 100f) * viewModel.randomPlacementMaxDistancePercentage;
        viewModel.randomPlacementMaxDistanceY = 2 + (paintView.getHeight() / 100f) * viewModel.randomPlacementMaxDistancePercentage;

    }


    public float getLineSizeFactor(){
       return viewModel.isPlacementQuantizationLineWidthIncluded ? paint.getStrokeWidth() : 0;
    }
}
