package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;


public class SizeHelper {

    private final MainViewModel viewModel;
    private SizeSequence currentSequence;
    private final PaintView paintView;
    private Map<SizeSequenceType, SizeSequence> sizeSequenceMap;
    private final SizeSequence stationarySequence;


    public SizeHelper(MainViewModel viewModel, PaintView paintView){
        this.viewModel = viewModel;
        this.paintView = paintView;
        stationarySequence = new StationarySizeSequence(viewModel);
        initSizeSequenceMap();
    }


    private void initSizeSequenceMap(){
        sizeSequenceMap = new HashMap<>();
        sizeSequenceMap.put(SizeSequenceType.STATIONARY, stationarySequence);
        sizeSequenceMap.put(SizeSequenceType.INCREASING, new IncreasingSizeSequence(viewModel));
        sizeSequenceMap.put(SizeSequenceType.DECREASING, new DecreasingSizeSequence(viewModel));
        sizeSequenceMap.put(SizeSequenceType.STROBE_INCREASING, new StrobeSizeSequence(viewModel, true));
        sizeSequenceMap.put(SizeSequenceType.STROBE_DECREASING, new StrobeSizeSequence(viewModel, false));
        sizeSequenceMap.put(SizeSequenceType.RANDOM, new RandomSizeSequence(viewModel));
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
