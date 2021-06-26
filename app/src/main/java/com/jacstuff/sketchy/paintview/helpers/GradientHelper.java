package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.brushes.GradientType;

public class GradientHelper {

    private Paint paint;
    private GradientType gradientType;
    private final int MAX_GRADIENT_FACTOR;
    private final MainViewModel viewModel;


    public GradientHelper(MainViewModel viewModel, int maxGradientFactor){
        this.viewModel = viewModel;
        MAX_GRADIENT_FACTOR = maxGradientFactor;
    }


    public void init(Paint paint){
        this.paint = paint;
        gradientType = GradientType.NONE;
    }


    public void updateBrushSize(int brushSize){
        viewModel.halfBrushSize = brushSize / 2;
    }


    public void setGradientType(GradientType gradientType){
        this.gradientType = gradientType;
    }


    public void setGradientRadius(int radiusFactor){
        final int CLAMP_RADIAL_GRADIENT_FACTOR = 12;
        final int RADIAL_GRADIENT_NUMERATOR= 1100;
        viewModel.radialGradientRadius = 1 + RADIAL_GRADIENT_NUMERATOR / radiusFactor;
        viewModel.clampRadialGradientRadius = 1 + viewModel.radialGradientRadius * CLAMP_RADIAL_GRADIENT_FACTOR;
        viewModel.linearGradientLength = 1 +  viewModel.halfBrushSize  - ((viewModel.halfBrushSize * radiusFactor)/MAX_GRADIENT_FACTOR);
    }


    public void assignGradient(float x, float y, int color, int oldColor){
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
                paint.setShader(new RadialGradient(0, 0, viewModel.clampRadialGradientRadius, new int []{color,oldColor}, null, Shader.TileMode.CLAMP ));
                break;
            case RADIAL_REPEAT:
                paint.setShader(new RadialGradient(0, 0, viewModel.radialGradientRadius, new int []{color,oldColor}, null, Shader.TileMode.REPEAT ));
                break;
            case RADIAL_MIRROR:
                paint.setShader(new RadialGradient(0, 0, viewModel.radialGradientRadius, new int []{color,oldColor}, null, Shader.TileMode.MIRROR ));
                break;
        }
    }

}
