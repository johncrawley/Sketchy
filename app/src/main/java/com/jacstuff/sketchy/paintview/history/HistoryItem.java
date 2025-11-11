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
    private ConnectedBrushState connectedLineState;
    private ConnectedBrushState connectedTriangleState;
    private TrianglePoints trianglePoints;

    public HistoryItem(Bitmap bitmap, int savedOrientation){
        this.bitmap = bitmap;
        this.savedOrientation = savedOrientation;
    }


    public Bitmap getBitmap(){
        return this.bitmap;
    }


    public int getOrientation(){
        return this.savedOrientation;
    }


    public void updateViewModelState(MainViewModel viewModel){
        this.connectedLinePreviousDown = new PointF(viewModel.nextLineDownX, viewModel.nextLineDownY);
        this.connectedLineState = new ConnectedBrushState(viewModel.connectedLineState);
        this.connectedTriangleState = new ConnectedBrushState(viewModel.connectedTriangleState);
        this.trianglePoints = new TrianglePoints(viewModel.trianglePoints);
    }


    public void assignSavedStateTo(MainViewModel viewModel){
        assignLineStateToViewModel(viewModel);
        assignTriangleStateToViewModel(viewModel);
    }


    private void assignLineStateToViewModel(MainViewModel viewModel){
        viewModel.nextLineDownX = connectedLinePreviousDown.x;
        viewModel.nextLineDownY = connectedLinePreviousDown.y;
        viewModel.connectedLineState = new ConnectedBrushState(this.connectedLineState);
    }


    private void assignTriangleStateToViewModel(MainViewModel viewModel){
        viewModel.connectedTriangleState = new ConnectedBrushState(this.connectedTriangleState);
        viewModel.trianglePoints = this.trianglePoints;
    }
}
