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

    public ShadowHelper(Paint paint){
        this.paint = paint;
    }


    public void setShadowSize(int size, int halfBrushSize){
        shadowSize = size;
        shadowOffsetFactor = halfBrushSize / 4;
        hasSizeBeenUpdated = true;
        assignShadow();
    }


    public void updateOffsetFactor(int halfBrushSize){
        shadowOffsetFactor = halfBrushSize / 4;
        assignShadow();
    }


    public boolean isShadowEnabled(){
        return shadowType != ShadowType.NONE;
    }

    public void set(ShadowType shadowType){
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
