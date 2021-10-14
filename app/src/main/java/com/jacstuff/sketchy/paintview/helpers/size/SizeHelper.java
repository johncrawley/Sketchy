package com.jacstuff.sketchy.paintview.helpers.size;

import android.app.Activity;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.size.initializer.FixedSizeInitializer;
import com.jacstuff.sketchy.paintview.helpers.size.initializer.VaryingSizeInitializer;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;


public class SizeHelper {

    private final MainViewModel viewModel;
    private SizeSequence currentSequence;
    private final PaintView paintView;
    private Map<SizeSequenceType, SizeSequence> sizeSequenceMap;
    private final SizeSequence stationarySequence;
    private final Activity activity;


    public SizeHelper(Activity activity, MainViewModel viewModel, PaintView paintView){
        this.activity = activity;
        this.viewModel = viewModel;
        this.paintView = paintView;
        stationarySequence = new StationarySizeSequence(new FixedSizeInitializer(activity), viewModel);
        initSizeSequenceMap();
    }


    private void initSizeSequenceMap(){
        sizeSequenceMap = new HashMap<>();
        VaryingSizeInitializer varyingSizeInitializer = new VaryingSizeInitializer(activity);
        sizeSequenceMap.put(SizeSequenceType.STATIONARY, stationarySequence);
        sizeSequenceMap.put(SizeSequenceType.INCREASING,        new IncreasingSizeSequence(varyingSizeInitializer, viewModel));
        sizeSequenceMap.put(SizeSequenceType.DECREASING,        new DecreasingSizeSequence(varyingSizeInitializer, viewModel));
        sizeSequenceMap.put(SizeSequenceType.STROBE_INCREASING, new StrobeSizeSequence(varyingSizeInitializer, viewModel, true));
        sizeSequenceMap.put(SizeSequenceType.STROBE_DECREASING, new StrobeSizeSequence(varyingSizeInitializer, viewModel, false));
        sizeSequenceMap.put(SizeSequenceType.RANDOM,            new RandomSizeSequence(varyingSizeInitializer, viewModel));
        setSequence(SizeSequenceType.STATIONARY);
    }


    public void setSequence(SizeSequenceType sequenceType){
        currentSequence = sizeSequenceMap.get(sequenceType);
        if(currentSequence == null){
            currentSequence = stationarySequence;
        }
        currentSequence.init();
    }


    public void onTouchDown(){
        currentSequence.reset();
        assignNextBrushSize();
    }


    public void assignNextBrushSize(){
        if(currentSequence.hasSizeChanged()) {
            paintView.setBrushSize(currentSequence.getNextBrushSize());
        }
    }

    public int getCurrentBrushSize(){
        return currentSequence.getNextBrushSize();
    }


    public void reset(){
       currentSequence.reset();
    }

}
