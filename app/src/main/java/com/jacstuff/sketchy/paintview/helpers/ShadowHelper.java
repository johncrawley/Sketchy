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

    private Paint paint;
    private int halfBrushSize;

    public ShadowHelper(Paint paint){
        this.paint = paint;
        this.halfBrushSize = 10;

    }

    public void setShadowSize(int size, int halfBrushSize){
        shadowSize = size;
        shadowOffsetFactor = halfBrushSize / 4;
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
            //paint.setShadowLayer(1, 0, 0, Color.TRANSPARENT);
            return;
        }

        switch (shadowType) {
            case CENTER:
                shadowOffsetX = 0;
                shadowOffsetY = 0;
                break;
            case NORTH:
                shadowOffsetX = 0;
                shadowOffsetY = -shadowOffsetFactor;
                break;
            case NORTH_WEST:
                shadowOffsetX = -shadowOffsetFactor;
                shadowOffsetY = -shadowOffsetFactor;
                break;
            case WEST:
                shadowOffsetX = -shadowOffsetFactor;
                shadowOffsetY = 0;
                break;
            case SOUTH_WEST:
                shadowOffsetX = -shadowOffsetFactor;
                shadowOffsetY = shadowOffsetFactor;
                break;
            case SOUTH:
                shadowOffsetX = 0;
                shadowOffsetY = shadowOffsetFactor;
                break;
            case SOUTH_EAST:
                shadowOffsetX = shadowOffsetFactor;
                shadowOffsetY = shadowOffsetFactor;
                break;
            case EAST:
                shadowOffsetX = shadowOffsetFactor;
                shadowOffsetY = 0;
                break;
            case NORTH_EAST:
                shadowOffsetX = shadowOffsetFactor;
                shadowOffsetY = -shadowOffsetFactor;
                break;
        }

        paint.setShadowLayer(shadowSize, shadowOffsetX, shadowOffsetY, Color.BLACK);
    }

}
