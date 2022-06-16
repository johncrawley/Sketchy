package com.jacstuff.sketchy.paintview.history;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class HistoryItem {
    private final Bitmap bitmap;
    private PointF connectedLinePreviousDown;
    private final int savedOrientation;
    private boolean hasFirstLineBeenDrawn;

    public HistoryItem(Bitmap bitmap, int savedOrientation, MainViewModel viewModel){
        this.bitmap = bitmap;
        this.savedOrientation = savedOrientation;
        updateViewModelState(viewModel);
    }


    public Bitmap getBitmap(){
        return this.bitmap;
    }


    public int getOrientation(){
        return this.savedOrientation;
    }


    public void updateViewModelState(MainViewModel viewModel){
        this.connectedLinePreviousDown = new PointF(viewModel.nextLineDownX, viewModel.nextLineDownY);
        this.hasFirstLineBeenDrawn = viewModel.hasFirstLineBeenDrawn;
    }

    public void assignSavedStateTo(MainViewModel viewModel){
        viewModel.nextLineDownX = connectedLinePreviousDown.x;
        viewModel.nextLineDownY = connectedLinePreviousDown.y;
        viewModel.hasFirstLineBeenDrawn = this.hasFirstLineBeenDrawn;
    }
}
