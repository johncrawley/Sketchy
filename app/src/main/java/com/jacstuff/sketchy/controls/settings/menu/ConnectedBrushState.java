package com.jacstuff.sketchy.controls.settings.menu;

public class ConnectedBrushState {

    public ConnectedBrushState(){}

    public ConnectedBrushState(ConnectedBrushState existingBrushState){
        this.hasFirstItemBeenDrawn = existingBrushState != null && existingBrushState.isFirstItemDrawn();
        this.isConnectedModeEnabled = existingBrushState != null && existingBrushState.isConnectedModeEnabled();
    }


    private void log(String msg){
        System.out.println("ConnectedBrushState: " + msg);
    }

    public void setConnectedModeEnabled(boolean isEnabled){
        isConnectedModeEnabled = isEnabled;
    }

    public boolean isConnectedModeEnabled(){
        return isConnectedModeEnabled;
    }


    public void setFirstItemDrawn(boolean isDrawn){
        hasFirstItemBeenDrawn = isDrawn;
    }


    public boolean isFirstItemDrawn(){
        return hasFirstItemBeenDrawn;
    }

    private boolean hasFirstItemBeenDrawn = false;
    private boolean isConnectedModeEnabled = false;
}
