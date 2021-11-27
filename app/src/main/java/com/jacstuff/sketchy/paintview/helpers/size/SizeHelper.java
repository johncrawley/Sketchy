package com.jacstuff.sketchy.paintview.helpers.size;


import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.BrushSizeSeekBarManager;
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
    private final VaryingSizeInitializer varyingSizeInitializer;


    public SizeHelper(MainViewModel viewModel, PaintView paintView, BrushSizeSeekBarManager brushSizeSeekBarManager){
        this.viewModel = viewModel;
        this.paintView = paintView;
        stationarySequence = new StationarySizeSequence(new FixedSizeInitializer(brushSizeSeekBarManager), viewModel);
        varyingSizeInitializer = new VaryingSizeInitializer(brushSizeSeekBarManager);
        initSizeSequenceMap();

    }


    private void initSizeSequenceMap(){
        sizeSequenceMap = new HashMap<>();
        sizeSequenceMap.put(SizeSequenceType.STATIONARY, stationarySequence);
        sizeSequenceMap.put(SizeSequenceType.INCREASING,        new IncreasingSizeSequence(varyingSizeInitializer, viewModel));
        sizeSequenceMap.put(SizeSequenceType.DECREASING,        new DecreasingSizeSequence(varyingSizeInitializer, viewModel));
        sizeSequenceMap.put(SizeSequenceType.STROBE_INCREASING, new StrobeSizeSequence(varyingSizeInitializer, viewModel, true));
        sizeSequenceMap.put(SizeSequenceType.STROBE_DECREASING, new StrobeSizeSequence(varyingSizeInitializer, viewModel, false));
        sizeSequenceMap.put(SizeSequenceType.RANDOM,            new RandomSizeSequence(varyingSizeInitializer, viewModel));
        sizeSequenceMap.put(SizeSequenceType.CENTER_POINT,      new CenterPointSizeSequence(varyingSizeInitializer, viewModel));
        setSequence(SizeSequenceType.STATIONARY);
    }


    public void setSequence(SizeSequenceType sequenceType){
        currentSequence = sizeSequenceMap.get(sequenceType);
        if(currentSequence == null){
            currentSequence = stationarySequence;
        }
        currentSequence.init();
    }


    public void onTouchDown(float x, float y){
        currentSequence.reset();
        assignNextBrushSize(x,y);
    }


    public void assignNextBrushSize(float x, float y){
        if(currentSequence.hasSizeChanged()) {
            paintView.setBrushSize(currentSequence.getNextBrushSize(x,y));
        }
    }

    public int getCurrentBrushSize(float x, float y){
        return currentSequence.getNextBrushSize(x, y);
    }


    public void reset(){
       currentSequence.reset();
    }

}
