package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.brushes.GradientType;

public class GradientHelper {

    private Paint paint;
    private GradientType gradientType;
    private final MainViewModel viewModel;
    final int CLAMP_RADIAL_GRADIENT_FACTOR = 12;
    final int RADIAL_GRADIENT_NUMERATOR= 1100;
    int radiusFactor;
    private int maxGradientLength;


    public GradientHelper(MainViewModel viewModel){
        this.viewModel = viewModel;
    }


    public void initDimensions(int paintViewWidth, int paintViewHeight){
        maxGradientLength = Math.max(paintViewWidth, paintViewHeight);
    }


    public void init(Paint paint){
        radiusFactor = 1;
        this.paint = paint;
        gradientType = GradientType.NONE;
    }


    public void setGradientType(GradientType gradientType){
        this.gradientType = gradientType;
    }


    public void setGradientRadius(int progress){
        radiusFactor = Math.max(1, progress);
        viewModel.gradient = radiusFactor;
        calculateGradientLength();
    }


    public void setGradientOffsetX(int progress){
        viewModel.radialGradientOffsetX = getOffset(progress);
    }


    public void setGradientOffsetY(int progress){
        viewModel.radialGradientOffsetY = getOffset(progress);
    }


    public void setGradientColor(int progress){
        int r = 0;
        int g = 0;
        int b = 0;
        int MAX = 256;
        int highest = MAX - 1;
        int modVal = progress % MAX;
        int minusModVal = MAX - modVal;

        if(progress < MAX){
            b = progress;
        } else if(progress < MAX * 2) {
            g = modVal;
            b = minusModVal;
        } else if(progress < MAX * 3) {
            g = highest;
            b = modVal;
        } else if(progress < MAX * 4) {
            r = modVal;
            g = minusModVal;
            b = minusModVal;
        } else if(progress < MAX * 5) {
            r = highest;
            g = 0;
            b = modVal;
        } else if(progress < MAX * 6) {
            r = highest;
            g = modVal;
            b = minusModVal;
        } else if(progress < MAX * 7) {
            r = highest;
            g = highest;
            b = modVal;
        }
        viewModel.secondaryColor = Color.argb(255,r,g,b);
    }


    private int getOffset(int progress){
        int percentage = progress -100;
        return (int)((maxGradientLength /100f) * percentage);
    }


    public void calculateGradientLength(){
        viewModel.radialGradientRadius = 1 + RADIAL_GRADIENT_NUMERATOR / radiusFactor;
        viewModel.clampRadialGradientRadius = 1 + viewModel.radialGradientRadius * CLAMP_RADIAL_GRADIENT_FACTOR;
        viewModel.linearGradientLength = calculateLinearGradientLength();
    }


    private int calculateLinearGradientLength(){
        float length =  maxGradientLength / (float) viewModel.gradient;
        return Math.max(1, (int) length);
    }


    public void recalculateGradientLengthForBrushSize(){
        if(gradientType == GradientType.NONE){
            return;
        }
        viewModel.gradientMaxLength = maxGradientLength;
        calculateGradientLength();
    }


    public void setGradientColorType(String type){
        viewModel.gradientColorType = GradientColorType.valueOf(type.toUpperCase());
    }


    public void assignGradient(float x, float y, int color){
        int gradientColor = getGradientColor();

        switch(gradientType){
            case NONE:
                paint.setShader(null);
                break;

            case DIAGONAL_MIRROR:
                paint.setShader(new LinearGradient(-viewModel.linearGradientLength,
                        -viewModel.linearGradientLength,
                        viewModel.linearGradientLength,
                        viewModel.linearGradientLength,
                        color,
                        gradientColor,
                        Shader.TileMode.MIRROR));
                break;

            case HORIZONTAL_MIRROR:
                paint.setShader(new LinearGradient( -viewModel.linearGradientLength,
                        y,
                        viewModel.linearGradientLength,
                        y,
                        color,
                        gradientColor,
                        Shader.TileMode.MIRROR));
                break;

            case VERTICAL_MIRROR:
                paint.setShader(new LinearGradient(x,
                        - viewModel.linearGradientLength,
                        x,
                        viewModel.linearGradientLength,
                        color,
                        gradientColor,
                        Shader.TileMode.MIRROR));
                break;

            case RADIAL_CLAMP:
                paint.setShader(new RadialGradient(viewModel.radialGradientOffsetX,
                        viewModel.radialGradientOffsetY,
                        viewModel.clampRadialGradientRadius,
                        new int []{color,gradientColor},
                        null,
                        Shader.TileMode.CLAMP ));
                break;

            case RADIAL_REPEAT:
                paint.setShader(new RadialGradient(viewModel.radialGradientOffsetX,
                        viewModel.radialGradientOffsetY,
                        viewModel.radialGradientRadius,
                        new int []{color,gradientColor},
                        null,
                        Shader.TileMode.REPEAT ));
                break;

            case RADIAL_MIRROR:
                paint.setShader(new RadialGradient(viewModel.radialGradientOffsetX,
                        viewModel.radialGradientOffsetY,
                        viewModel.radialGradientRadius,
                        new int []{color,gradientColor},
                        null,
                        Shader.TileMode.MIRROR ));
        }
    }


    private int getGradientColor(){
        switch(viewModel.gradientColorType) {
            case SELECTED:
                return viewModel.secondaryColor;

            case PREVIOUS:
                return viewModel.previousColor;
        }
        return Color.TRANSPARENT;
    }

}
