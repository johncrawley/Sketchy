package com.jacstuff.sketchy.brushes.styles;

import com.jacstuff.sketchy.paintview.PaintGroup;

public class AbstractStyle {

    boolean haveSettingsChanged = false;
    int brushSize;

    public void onDraw(PaintGroup paint){
        if(haveSettingsChanged){
            onDrawAfterSettingsChanged();
            haveSettingsChanged = false;
        }
    }


    public void setBrushSize(PaintGroup paintGroup, int brushSize) {
        this.brushSize = brushSize;
    }


    public void notifyStyleChange(){
        haveSettingsChanged = true;
    }


    void onDrawAfterSettingsChanged(){

    }
}
