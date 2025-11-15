package com.jacstuff.sketchy.controls.settings.menu;

import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.function.Supplier;

public class ConnectedBrushIconModifier  {

    private Button shapeButton;
    private final PaintView paintView;
    private final MainActivity activity;
    private int normalIconResId, connectedIconResId;
    private final BrushShape currentBrushShape;
    private final MainViewModel viewModel;
    private final Supplier<ConnectedBrushState> connectedBrushStateSupplier;


    public ConnectedBrushIconModifier(MainActivity activity, ConnectedBrushState connectedBrushState, Supplier<ConnectedBrushState> supplier, BrushShape brushShape){
        this.activity = activity;
        this.viewModel = activity.getViewModel();
        this.paintView = activity.getPaintView();
       // this.connectedBrushState = connectedBrushState;
        this.currentBrushShape = brushShape;
        this.connectedBrushStateSupplier = supplier;
    }


    public void assignShapeButton(){
        if(shapeButton == null) {
            shapeButton = activity.findViewById(R.id.shapeButton);
        }
    }


    public BrushShape getBrushShape(){
        return currentBrushShape;
    }


    public void assignNormalIconId(int normalIconResId){
        this.normalIconResId = normalIconResId;
    }


    public void assignConnectedIconResId(int connectedIconResId){
        this.connectedIconResId = connectedIconResId;
    }


    public void setConnectedIconAndState(){
        var connectedBrushState = connectedBrushStateSupplier.get();
        if(connectedBrushState.isConnectedModeEnabled()){
            connectedBrushState.setFirstItemDrawn(true);
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
        var connectedBrushState = connectedBrushStateSupplier.get();
        connectedBrushState.setFirstItemDrawn(false);
        assignDefaultIconToShapeButton();
    }


    public boolean isShapeButtonAndInConnectedMode(int viewId){

        return viewId == R.id.shapeButton
                && isUsingThisShapeInConnectedMode()
                && connectedBrushStateSupplier.get().isFirstItemDrawn();
    }


    public void updateIcon(){
        if(!isUsingCurrentBrush()){
            return;
        }
        if(isUsingThisShapeInConnectedMode()){
            if(connectedBrushStateSupplier.get().isFirstItemDrawn()){
                assignConnectedIconToShapeButton();
                return;
            }
        }
        assignDefaultIconToShapeButton();
    }


    public boolean isUsingThisShapeInConnectedMode(){
        return  paintView.getCurrentBrush().getBrushShape() == currentBrushShape
                && connectedBrushStateSupplier.get().isConnectedModeEnabled();
    }


    void revertIconAndState(){
        connectedBrushStateSupplier.get().setFirstItemDrawn(false);
        viewModel.drawHistory.updateNewestItemWithState();
        paintView.getCurrentBrush().reset();
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


    private void assignConnectedIconToShapeButton(){
        shapeButton.setBackgroundResource(connectedIconResId);
    }

}
