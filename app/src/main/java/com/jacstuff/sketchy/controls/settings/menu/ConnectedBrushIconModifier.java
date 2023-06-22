package com.jacstuff.sketchy.controls.settings.menu;

import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.history.DrawHistory;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ConnectedBrushIconModifier  {

    private Button shapeButton;
    private final PaintView paintView;
    private final MainViewModel viewModel;
    private DrawHistory drawHistory;
    private final MainActivity activity;
    private final ConnectedBrushState connectedBrushState;
    private int normalIconResId, connectedIconResId;
    private BrushShape currentBrushShape;


    public ConnectedBrushIconModifier(PaintView paintView, MainViewModel viewModel, MainActivity activity, ConnectedBrushState connectedBrushState, BrushShape brushShape){
        this.paintView = paintView;
        this.viewModel = viewModel;
        this.activity = activity;
        this.connectedBrushState = connectedBrushState;
        this.currentBrushShape = brushShape;
    }


    public void setDrawHistory(DrawHistory drawHistory){
        this.drawHistory = drawHistory;
    }


    public void assignShapeButton(){
        if(shapeButton == null) {
            shapeButton = activity.findViewById(R.id.shapeButton);
        }
    }

    public void assignNormalIconId(int normalIconResId){
        this.normalIconResId = normalIconResId;
    }


    public void assignConnectedIconResId(int connectedIconResId){
        this.connectedIconResId = connectedIconResId;
    }


    public void setConnectedIconAndState(){
        if(connectedBrushState.isConnectedModeEnabled){
            connectedBrushState.hasFirstItemBeenDrawn = true;
            switchToConnectedIcon();
        }
    }


    private void switchToNormalIcon(){
        shapeButton.setBackgroundResource(normalIconResId);
    }


    private void switchToConnectedIcon(){
        shapeButton.setBackgroundResource(connectedIconResId);
    }


    public void resetIconAndState(){
        viewModel.hasFirstLineBeenDrawn = false;
        assignDefaultIconToShapeButton();
    }


    public boolean isUsingLineShapeInConnectedMode(){
        return  paintView.getCurrentBrush().getBrushShape() == BrushShape.LINE
                && viewModel.isConnectedLinesModeEnabled;
    }


    public boolean isShapeButtonAndInConnectedLineMode(int viewId){
        return viewId == R.id.shapeButton
                && isUsingLineShapeInConnectedMode()
                && viewModel.hasFirstLineBeenDrawn;
    }


    public void updateLineIconBackground(){
        if(!isUsingCurrentBrush()){
            return;
        }
        if(isUsingLineShapeInConnectedMode()){
            if(viewModel.hasFirstLineBeenDrawn){
                assignConnectedLineIconToShapeButton();
                return;
            }
        }
        assignDefaultIconToShapeButton();
    }


    void revertIconAndState(){
        viewModel.hasFirstLineBeenDrawn = false;
        drawHistory.updateNewestItemWithState();
        assignDefaultIconToShapeButton();
    }


    private void assignDefaultIconToShapeButton(){
        if(isUsingCurrentBrush()){
            switchToNormalIcon();
        }
    }


    private boolean isUsingCurrentBrush(){
        return paintView != null
                && paintView.getCurrentBrush() != null
                && paintView.getCurrentBrush().getBrushShape() == currentBrushShape;
    }


    private void assignConnectedLineIconToShapeButton(){
        shapeButton.setBackgroundResource(R.drawable.button_shape_line_connected);
    }

}
