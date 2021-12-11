package com.jacstuff.sketchy.paintview.helpers.placement;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.Random;

public class RandomPlacement {

    private final Random random;
    private final MainViewModel viewModel;
    private int maxScreenDimension;


    public RandomPlacement(MainViewModel viewModel){
        this.viewModel = viewModel;
        random = new Random(System.currentTimeMillis());
    }


    void initDimensions(int width, int height){
        maxScreenDimension = Math.max(width, height);
        updateMaxRandomDistance();
    }


    public float getX(float x){
       return getRandom(x);
    }


    public float getY(float y){
       return getRandom(y);
    }


    private float getRandom(float a){
        int bound = 2 + Math.max(1, (2 * (int)viewModel.randomPlacementMaxDistance));
        int randomValue = random.nextInt(bound);
        return (a - viewModel.randomPlacementMaxDistance) + randomValue;
    }


    public void updateMaxRandomDistance(){
        viewModel.randomPlacementMaxDistance = ((maxScreenDimension / 200f) * viewModel.randomPlacementMaxDistancePercentage);
    }
}
