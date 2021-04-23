package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.jacstuff.sketchy.brushes.GradientType;

public class GradientHelper {

    private Paint paint;
    private GradientType gradientType;
    private int halfBrushSize = 10;
    private int clampRadialGradientFactor = 12;
    private int clampRadialGradientRadius = 10;
    private int radialGradientRadius = 1;

    public GradientHelper(Paint paint){
        this.paint = paint;
        gradientType = GradientType.NONE;
    }

    public void updateBrushSize(int brushSize){
        this.halfBrushSize = brushSize / 2;
    }

    public void setGradientType(GradientType gradientType){
        this.gradientType = gradientType;
    }



    public void setGradientRadius(int radiusFactor, int canvasWidth){
        radialGradientRadius = 1 + canvasWidth / radiusFactor;
        clampRadialGradientRadius = 1 + radialGradientRadius * clampRadialGradientFactor;
    }


    public void assignGradient(float x, float y, int color, int oldColor){
        switch(gradientType){
            case NONE:
                paint.setShader(null);
                break;
            case DIAGONAL_MIRROR:
                int x1 = (int)x + (halfBrushSize);
                int y1 = (int)y + (halfBrushSize);
                paint.setShader(new LinearGradient(x, y, x1, y1, color, oldColor, Shader.TileMode.MIRROR));
                break;
            case RADIAL_CLAMP:
                paint.setShader(new RadialGradient(0, 0, clampRadialGradientRadius, new int []{color,oldColor}, null, Shader.TileMode.CLAMP ));
                break;
            case RADIAL_REPEAT:
                paint.setShader(new RadialGradient(0, 0, radialGradientRadius, new int []{color,oldColor}, null, Shader.TileMode.REPEAT ));
                break;
            case RADIAL_MIRROR:
                paint.setShader(new RadialGradient(0, 0, radialGradientRadius, new int []{color,oldColor}, null, Shader.TileMode.MIRROR ));
                break;
        }
    }

}
