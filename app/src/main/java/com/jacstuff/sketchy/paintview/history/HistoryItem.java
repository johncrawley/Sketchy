package com.jacstuff.sketchy.paintview.history;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.twostep.TrianglePoints;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class HistoryItem {
    private final Bitmap bitmap;
    private PointF connectedLinePreviousDown;
    private final int savedOrientation;
    private boolean hasFirstLineBeenDrawn;
    private PointF connectedTrianglePoint1, connectedTrianglePoint2, connectedTrianglePoint3;
    private boolean hasFirstTriangleBeenDrawn;
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
        this.hasFirstLineBeenDrawn = viewModel.hasFirstLineBeenDrawn;
        this.trianglePoints = viewModel.trianglePoints;
    }

    public void assignSavedStateTo(MainViewModel viewModel){
        assignLineStateToViewModel(viewModel);
        assignTriangleStateToViewModel(viewModel);
    }

    private void assignLineStateToViewModel(MainViewModel viewModel){
        viewModel.nextLineDownX = connectedLinePreviousDown.x;
        viewModel.nextLineDownY = connectedLinePreviousDown.y;
        viewModel.hasFirstLineBeenDrawn = this.hasFirstLineBeenDrawn;
    }


    private void assignTriangleStateToViewModel(MainViewModel viewModel){
        viewModel.variableTrianglePoint1 = connectedTrianglePoint1;
        viewModel.variableTrianglePoint2 = connectedTrianglePoint2;
        viewModel.variableTrianglePoint3 = connectedTrianglePoint3;
        viewModel.hasFirstTriangleBeenDrawn = this.hasFirstTriangleBeenDrawn;
        viewModel.trianglePoints = this.trianglePoints;
    }
}
