package com.jacstuff.sketchy.paintview.helpers.size;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;


public class SizeHelper {

    private final MainViewModel viewModel;
    private SizeSequence currentSequence;
    private Map<SizeSequenceType, SizeSequence> sizeSequenceMap;


    public SizeHelper(MainViewModel viewModel){
        this.viewModel = viewModel;
        initSizeSequenceMap();
    }


    private void initSizeSequenceMap(){
        sizeSequenceMap = new HashMap<>();
        sizeSequenceMap.put(SizeSequenceType.STATIONARY, new StationarySizeSequence());
        sizeSequenceMap.put(SizeSequenceType.INCREASING, new IncreasingSizeSequence(viewModel));
    }


    public void setSequence(SizeSequenceType sequenceType){
        currentSequence = sizeSequenceMap.get(sequenceType);
    }


    public void assignNextBrushSize(){
        currentSequence.assignNextBrushSize();
    }

}
