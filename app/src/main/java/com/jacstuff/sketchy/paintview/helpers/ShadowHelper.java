package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Color;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.ShadowType;
import com.jacstuff.sketchy.brushes.shapes.drawer.KaleidoscopeDrawer;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ShadowHelper {


    private ShadowType shadowType = ShadowType.NONE;

    private float shadowOffsetX, shadowOffsetY;
    private int shadowOffsetFactor;
    private boolean hasSizeBeenUpdated;
    private boolean hasDistanceBeenUpdated;
    private Paint paint;
    private  MainViewModel viewModel;
    private PaintView paintView;


    public ShadowHelper(PaintView paintView, MainViewModel viewModel){
        this.paintView = paintView;
        this.viewModel = viewModel;
    }


    public void setShadowSize(int size){
        viewModel.shadowSize = 1 + size;
        hasSizeBeenUpdated = true;
        assignShadow();
    }


    public void setShadowDistance(int progress){
        hasDistanceBeenUpdated = true;
        viewModel.shadowDistance = 1 + progress;
        assignShadow();
    }


    public void init(Paint paint){
        this.paint = paint;
    }


    public void updateOffsetFactor(int halfBrushSize){
        shadowOffsetFactor = halfBrushSize * 4;
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

        float previousX = shadowOffsetX;
        float previousY = shadowOffsetY;

        shadowOffsetX = ((shadowOffsetFactor * shadowType.offsetX) /100f) * viewModel.shadowDistance;
        shadowOffsetY = ((shadowOffsetFactor * shadowType.offsetY) / 100f) * viewModel.shadowDistance;

        if(previousX == shadowOffsetX && previousY == shadowOffsetY && !hasSizeBeenUpdated && !hasDistanceBeenUpdated){
            return;
        }
        hasSizeBeenUpdated = false;
        hasDistanceBeenUpdated = false;
        paint.setShadowLayer(viewModel.shadowSize, shadowOffsetX, shadowOffsetY, Color.BLACK);
    }

}
