package com.jacstuff.sketchy.paintview.history;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.twostep.TrianglePoints;
import com.jacstuff.sketchy.controls.settings.menu.ConnectedBrushState;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class HistoryItem {
    private final Bitmap bitmap;
    private PointF connectedLinePreviousDown;
    private final int savedOrientation;
    private boolean hasFirstTriangleBeenDrawn;
    private ConnectedBrushState connectedLineState;
    private TrianglePoints trianglePoints;

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
        this.connectedLineState = viewModel.connectedLineState;
        this.trianglePoints = new TrianglePoints(viewModel.trianglePoints);
        this.hasFirstTriangleBeenDrawn = viewModel.hasFirstTriangleBeenDrawn;
    }


    public void assignSavedStateTo(MainViewModel viewModel){
        assignLineStateToViewModel(viewModel);
        assignTriangleStateToViewModel(viewModel);
    }


    private void assignLineStateToViewModel(MainViewModel viewModel){
        viewModel.nextLineDownX = connectedLinePreviousDown.x;
        viewModel.nextLineDownY = connectedLinePreviousDown.y;
        viewModel.connectedLineState = this.connectedLineState;
    }


    private void assignTriangleStateToViewModel(MainViewModel viewModel){
        viewModel.hasFirstTriangleBeenDrawn = this.hasFirstTriangleBeenDrawn;
        viewModel.trianglePoints = this.trianglePoints;
    }
}
