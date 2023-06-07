package com.jacstuff.sketchy.paintview.helpers.gradient;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.paintview.helpers.color.InfinityModeRandomGradientBlender;
import com.jacstuff.sketchy.utils.ColorUtils;
import com.jacstuff.sketchy.viewmodel.MainViewModel;
import com.jacstuff.sketchy.brushes.GradientType;

import java.util.Random;

public class GradientHelper {

    private Paint paint;
    private GradientType gradientType;
    private final MainViewModel viewModel;
    final int CLAMP_RADIAL_GRADIENT_FACTOR = 12;
    int radialGradientNumerator = 1100;
    float radiusFactor;
    private int maxGradientLength;
    private final Random random;
    private Brush brush;
    private int gradientColor;
    private int linearStartX, linearStartY, linearEndX, linearEndY;
    private final InfinityModeRandomGradientBlender gradientBlender;
    private final KaleidoscopeHelper kaleidoscopeHelper;

    public GradientHelper(MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        this.viewModel = viewModel;
        this.kaleidoscopeHelper = kaleidoscopeHelper;
        random = new Random(System.currentTimeMillis());
        gradientBlender = new InfinityModeRandomGradientBlender(random);
    }


    public void initDimensions(int paintViewWidth, int paintViewHeight){
        maxGradientLength = Math.max(paintViewWidth, paintViewHeight);
        radialGradientNumerator = maxGradientLength;
        calculateGradientLength();
    }


    public void updateBrush(Brush brush){
        this.brush = brush;
    }


    public void init(Paint paint){
        radiusFactor = 1;
        this.paint = paint;
        gradientType = GradientType.NONE;
    }


    public void setGradientType(GradientType gradientType){
        this.gradientType = gradientType;
    }


    public void setLength(int progress){
        radiusFactor = Math.max(1, 200 - progress);
        viewModel.gradientProgress = progress;
        viewModel.gradient = (int)radiusFactor;
        calculateGradientLength();
    }


    public void setRadialOffsetX(int progress){
        viewModel.radialGradientOffsetX = getOffset(progress);
    }


    public void setRadialOffsetY(int progress){
        viewModel.radialGradientOffsetY = getOffset(progress);
    }


    public void setLinearOffset(int progress){
        viewModel.gradientLinearOffsetPercentage = progress;
    }


    public void setColor(int progress){
        viewModel.secondaryColor = ColorUtils.getColorFromSlider(progress);
    }


    private int getOffset(int progress){
        int percentage = progress -100;
        return (int)((maxGradientLength /100f) * percentage);
    }


    public void calculateGradientLength(){
        calculateRadialGradient();
        calculateLinearGradientLength();
    }


    private void calculateRadialGradient(){
        float radius = 1 + (radialGradientNumerator / radiusFactor) ;
        viewModel.radialGradientRadius = (int) (radius * 2f);
        viewModel.clampRadialGradientRadius = 1 + viewModel.radialGradientRadius * CLAMP_RADIAL_GRADIENT_FACTOR;
    }


    private void calculateLinearGradientLength(){
        maxGradientLength = maxGradientLength == 0 ? 1000 : maxGradientLength;
        float length =  maxGradientLength / (float) viewModel.gradient;
        viewModel.linearGradientLength =  Math.max(1, (int) length);
        viewModel.getLinearGradientNoRepeatLength = 1 + viewModel.gradientProgress * 3;
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
        assignGradient(x, y, color, true);
    }


    public void assignGradientForDragShape(PointF down, PointF up, PointF mid, boolean shouldNewRandomColorBeAssigned) {
        if (gradientType == GradientType.NONE) {
            paint.setShader(null);
            return;
        }
        float fullDimensionX = Math.abs(down.x - up.x);
        float fullDimensionY = Math.abs(down.y - up.y);

        gradientColor = getGradientColor(shouldNewRandomColorBeAssigned);
        linearStartX = getLinearCoordinateForDragShape(down.x, up.x, fullDimensionX);
        linearStartY = getLinearCoordinateForDragShape(down.y, up.y, fullDimensionY);
        linearEndX = calculateLinearEndForDragShape(linearStartX);
        linearEndY = calculateLinearEndForDragShape(linearStartY);
        setGradient(mid.x, mid.y, viewModel.color);
    }


    private int getLinearCoordinateForDragShape(float downCoordinate, float upCoordinate, float brushHalfDimension){
        float startCoordinate = Math.min(downCoordinate, upCoordinate);
        return  (int) startCoordinate
               - viewModel.getLinearGradientNoRepeatLength
               + (int)( (brushHalfDimension / 100f) * viewModel.gradientLinearOffsetPercentage);
    }


    private int calculateLinearEndForDragShape(float startCoordinate){
        return (int) startCoordinate + viewModel.getLinearGradientNoRepeatLength * 2;
    }


    public void assignGradient(float x, float y, int color, boolean shouldNewRandomColorBeAssigned){
        if(gradientType == GradientType.NONE){
            paint.setShader(null);
            return;
        }
        int start = calculateLinearStart();
        gradientColor = getGradientColor(shouldNewRandomColorBeAssigned);
        linearStartX = start;
        linearStartY = start;
        int end = (int) getLinearGradientEnd(linearStartX);
        linearEndX = end;
        linearEndY = end;
        setGradient(x, y, color);
    }


    private void setGradient(float x, float y, int color){
        Shader.TileMode tileMode = viewModel.isLinearGradientRepeated ?
                Shader.TileMode.MIRROR :
                Shader.TileMode.CLAMP;

        switch(gradientType){

            case LINEAR_DIAGONAL:
                paint.setShader(new LinearGradient(linearStartX,
                        linearStartY,
                        linearEndX,
                        linearEndY,
                        color,
                        gradientColor,
                        tileMode));
                break;

            case LINEAR_HORIZONTAL:
                paint.setShader(new LinearGradient( linearStartX,
                        y,
                        linearEndX,
                        y,
                        color,
                        gradientColor,
                        tileMode));
                break;

            case LINEAR_VERTICAL:
                paint.setShader(new LinearGradient(x,
                        linearStartY,
                        x,
                        linearEndY,
                        color,
                        gradientColor,
                        tileMode));
                break;

            case RADIAL_CLAMP:
                paint.setShader(new RadialGradient(getRadialGradientOffsetX(x),
                        getRadialGradientOffsetY(y),
                        viewModel.clampRadialGradientRadius,
                        new int []{color,gradientColor},
                        null,
                        Shader.TileMode.CLAMP ));
                break;

            case RADIAL_REPEAT:
                paint.setShader(new RadialGradient(getRadialGradientOffsetX(x),
                        getRadialGradientOffsetY(y),
                        viewModel.radialGradientRadius,
                        new int []{color,gradientColor},
                        null,
                        Shader.TileMode.REPEAT ));
                break;

            case RADIAL_MIRROR:
                paint.setShader(new RadialGradient(getRadialGradientOffsetX(x),
                        getRadialGradientOffsetY(y),
                        viewModel.radialGradientRadius,
                        new int []{color,gradientColor},
                        null,
                        Shader.TileMode.MIRROR ));
        }
    }


    public float getRadialGradientOffsetX(float x){
        return getInitialOffset(x) + viewModel.radialGradientOffsetX;
    }


    public float getRadialGradientOffsetY(float y){
        return getInitialOffset(y) + viewModel.radialGradientOffsetY;
    }


    public float getInitialOffset(float a){
        return brush == null || brush.isDrawnFromCenter() ? 0 : a;
    }


    private int calculateLinearStart(){
        if(viewModel.isLinearGradientRepeated){
            return viewModel.linearGradientLength;
        }
        float brushEdge = brush.getBrushSize() / 15f;
        return (int)(-brush.getHalfBrushSize() + brushEdge)
                + (int)((brush.getBrushSize() / 130f) * viewModel.gradientLinearOffsetPercentage);
    }


    private float getLinearGradientEnd(float linearGradientStart){
        if(viewModel.isLinearGradientRepeated){
           return linearGradientStart + viewModel.linearGradientLength;
        }
        return linearGradientStart + viewModel.getLinearGradientNoRepeatLength;
    }


    private int getGradientColor(boolean shouldNewRandomColorBeAssigned){
        switch(viewModel.gradientColorType) {
            case SELECTED:
                return viewModel.secondaryColor;

            case PREVIOUS:
                return viewModel.previousColor;

            case RANDOM:
                return getRandomColor(shouldNewRandomColorBeAssigned);
            case RANDOM_BLEND:
                return getNextRandomBlend(shouldNewRandomColorBeAssigned);
        }
        return Color.TRANSPARENT;
    }


    private int getRandomColor(boolean shouldNewRandomColorBeAssigned){
        if(viewModel.isRandomColorDisabled()){
            return getNextRandomBlend(shouldNewRandomColorBeAssigned);
        }
        if(shouldNewRandomColorBeAssigned){
            if(kaleidoscopeHelper.isInfinityModeEnabled()){
                return gradientBlender.getNextInfinityModeShade();
            }
            return ColorUtils.getRandomColor(random);
        }
        return gradientColor;
    }


    private int getNextRandomBlend(boolean shouldNewRandomColorBeAssigned){
        if(shouldNewRandomColorBeAssigned){
                return gradientBlender.getNextInfinityModeShade();
        }
        return gradientColor;
    }

}
