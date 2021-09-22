package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Color;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.ShadowType;

public class ShadowHelper {


    private ShadowType shadowType = ShadowType.NONE;
    private int shadowSize;
    private int shadowOffsetX;
    private int shadowOffsetY;
    private int shadowOffsetFactor;
    private boolean hasSizeBeenUpdated;
    private Paint paint;


    public void setShadowSize(int size){
        shadowSize = 1 + size;
        hasSizeBeenUpdated = true;
        assignShadow();
    }

    public void init(Paint paint){
        this.paint = paint;
    }


    public void updateOffsetFactor(int halfBrushSize){
        shadowOffsetFactor = halfBrushSize / 4;
        assignShadow();
    }


    public boolean isShadowEnabled(){
        return shadowType != ShadowType.NONE;
    }


    public void setType(ShadowType shadowType){
        this.shadowType = shadowType;
        assignShadow();
    }


    public void assignShadow() {
        if(shadowType == ShadowType.NONE){
            paint.clearShadowLayer();
            return;
        }

        int previousX = shadowOffsetX;
        int previousY = shadowOffsetY;

        shadowOffsetX = shadowOffsetFactor * shadowType.offsetX;
        shadowOffsetY = shadowOffsetFactor * shadowType.offsetY;

        if(previousX == shadowOffsetX && previousY == shadowOffsetY && !hasSizeBeenUpdated){
            return;
        }
        hasSizeBeenUpdated = false;
        paint.setShadowLayer(shadowSize, shadowOffsetX, shadowOffsetY, Color.BLACK);
    }

}
