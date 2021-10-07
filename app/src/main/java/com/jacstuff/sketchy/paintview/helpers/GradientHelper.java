package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.brushes.GradientType;

public class GradientHelper {

    private Paint paint;
    private GradientType gradientType;
    private final int MAX_GRADIENT_PROGRESS;
    private final MainViewModel viewModel;
    final int CLAMP_RADIAL_GRADIENT_FACTOR = 12;
    final int RADIAL_GRADIENT_NUMERATOR= 1100;
    int radiusFactor;


    public GradientHelper(MainViewModel viewModel, int maxGradientFactor){
        this.viewModel = viewModel;
        MAX_GRADIENT_PROGRESS = maxGradientFactor;
    }


    public void init(Paint paint){
        radiusFactor = 1;
        this.paint = paint;
        gradientType = GradientType.NONE;
    }


    public void updateBrushSize(int brushSize){
        viewModel.halfBrushSize = brushSize / 2;
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
        int highest = MAX-1;
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
        return (int)((viewModel.brushSize /100f) * percentage);
    }



    public void calculateGradientLength(){
        viewModel.radialGradientRadius = 1 + RADIAL_GRADIENT_NUMERATOR / radiusFactor;
        viewModel.clampRadialGradientRadius = 1 + viewModel.radialGradientRadius * CLAMP_RADIAL_GRADIENT_FACTOR;
        viewModel.linearGradientLength = calculateLinearGradientLength();
    }


    private int calculateLinearGradientLength(){
        float progressPercentage = ((float)radiusFactor / MAX_GRADIENT_PROGRESS)* 100f;

        if(progressPercentage > 80){
            return 1 + (int)(100 - progressPercentage);
        }
        else if(progressPercentage > 40){
            return (int)((viewModel.gradientMaxLength /  progressPercentage) * 10);
        }
        float progressPercentage2 = Math.max(1, progressPercentage) + 10;
        float minusFraction = ((float)viewModel.gradientMaxLength / 100) * progressPercentage2;
        int calculatedGradientLength = viewModel.gradientMaxLength  - (int)(minusFraction);
        return Math.max(1, calculatedGradientLength);
    }


    public void recalculateGradientLengthForBrushSize(){
        if(gradientType == GradientType.NONE){
            return;
        }
        viewModel.gradientMaxLength = viewModel.halfBrushSize;
        calculateGradientLength();
    }


    public void recalculateGradientLengthForRectangle(Point p, float width, float height){
        if(gradientType == GradientType.NONE){
            return;
         }
        viewModel.gradientMaxLength = (int)((width + height)/1.4142f);
        calculateGradientLength();
        assignGradient(p.x, p.y, viewModel.color, viewModel.secondaryColor);
    }


    public void assignGradient(float x, float y, int color, int oldColor2){
       // oldColor = Color.TRANSPARENT;
        int oldColor = viewModel.secondaryColor;
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
                        oldColor,
                        Shader.TileMode.MIRROR));
                break;
            case HORIZONTAL_MIRROR:
                paint.setShader(new LinearGradient( -viewModel.linearGradientLength,
                        y,
                        viewModel.linearGradientLength,
                        y,
                        color,
                        oldColor,
                        Shader.TileMode.MIRROR));
                break;
            case VERTICAL_MIRROR:
                paint.setShader(new LinearGradient(x, - viewModel.linearGradientLength, x,  + viewModel.linearGradientLength, color, oldColor, Shader.TileMode.MIRROR));
                break;
            case RADIAL_CLAMP:
                paint.setShader(new RadialGradient(viewModel.radialGradientOffsetX,
                        viewModel.radialGradientOffsetY,
                        viewModel.clampRadialGradientRadius,
                        new int []{color,oldColor},
                        null,
                        Shader.TileMode.CLAMP ));
                break;
            case RADIAL_REPEAT:
                paint.setShader(new RadialGradient(viewModel.radialGradientOffsetX,
                        viewModel.radialGradientOffsetY,
                        viewModel.radialGradientRadius,
                        new int []{color,oldColor},
                        null,
                        Shader.TileMode.REPEAT ));
                break;
            case RADIAL_MIRROR:
                paint.setShader(new RadialGradient(viewModel.radialGradientOffsetX,
                        viewModel.radialGradientOffsetY,
                        viewModel.radialGradientRadius,
                        new int []{color,oldColor},
                        null,
                        Shader.TileMode.MIRROR ));
                break;
        }
    }

}
