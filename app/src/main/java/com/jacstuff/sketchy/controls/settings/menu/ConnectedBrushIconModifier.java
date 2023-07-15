package com.jacstuff.sketchy.controls.settings.menu;

import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.history.DrawHistory;

public class ConnectedBrushIconModifier  {

    private Button shapeButton;
    private final PaintView paintView;
    private final MainActivity activity;
    private final ConnectedBrushState connectedBrushState;
    private int normalIconResId, connectedIconResId;
    private final BrushShape currentBrushShape;


    public ConnectedBrushIconModifier(MainActivity activity, ConnectedBrushState connectedBrushState, BrushShape brushShape){
        this.activity = activity;
        this.paintView = activity.getPaintView();
        this.connectedBrushState = connectedBrushState;
        this.currentBrushShape = brushShape;
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
        connectedBrushState.hasFirstItemBeenDrawn = false;
        assignDefaultIconToShapeButton();
    }


    public boolean isShapeButtonAndInConnectedMode(int viewId){
        return viewId == R.id.shapeButton
                && isUsingThisShapeInConnectedMode()
                && connectedBrushState.hasFirstItemBeenDrawn;
    }


    public void updateIcon(){
        if(!isUsingCurrentBrush()){
            return;
        }
        if(isUsingThisShapeInConnectedMode()){
            if(connectedBrushState.hasFirstItemBeenDrawn){
                assignConnectedIconToShapeButton();
                return;
            }
        }
        assignDefaultIconToShapeButton();
    }


    public boolean isUsingThisShapeInConnectedMode(){
        return  paintView.getCurrentBrush().getBrushShape() == currentBrushShape
                && connectedBrushState.isConnectedModeEnabled;
    }


    void revertIconAndState(){
        connectedBrushState.hasFirstItemBeenDrawn = false;
        paintView.getDrawHistory().updateNewestItemWithState();
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
