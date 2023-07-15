package com.jacstuff.sketchy.controls.settings.menu;

public class ConnectedBrushState {

    public ConnectedBrushState(){}

    public ConnectedBrushState(ConnectedBrushState existingBrushState){
        this.hasFirstItemBeenDrawn = existingBrushState.hasFirstItemBeenDrawn;
        this.isConnectedModeEnabled = existingBrushState.isConnectedModeEnabled;
    }

    public boolean hasFirstItemBeenDrawn = false;
    public boolean isConnectedModeEnabled = false;
}
