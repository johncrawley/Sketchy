package com.jacstuff.sketchy.paintview.helpers.placement;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.Random;

public class RandomPlacement {

    private final Random random;
    private final MainViewModel viewModel;
    private final int maxScreenDimension;

    public RandomPlacement(MainViewModel viewModel, PaintView paintView){
        this.viewModel = viewModel;
        maxScreenDimension = (int) (Math.max(paintView.getWidth(), paintView.getHeight()) * 1.4f);
        random = new Random(System.currentTimeMillis());
    }


    public float getX(float x){
       return getRandom(x);
    }


    public float getY(float y){
       return getRandom(y);
    }


    private float getRandom(float a){
        int bound = 2 * (int)viewModel.randomPlacementMaxDistance;
        return  (a -viewModel.randomPlacementMaxDistance) + random.nextInt(bound);
    }



    public void updateMaxRandomDistance(){
        viewModel.randomPlacementMaxDistance = 2 + (maxScreenDimension / 200f) * viewModel.randomPlacementMaxDistancePercentage;
    }
}
