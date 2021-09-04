package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.paintview.InfinityModeColorBlender;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ColorHelper {

    private final MainViewModel viewModel;
    private final PaintView paintView;
    private ColorSelector colorSelector;
    private InfinityModeColorBlender infinityModeColorBlender;
    private Paint paint, shadowPaint;


    public ColorHelper(MainViewModel viewModel, PaintView paintView){
        this.viewModel = viewModel;
        this.paintView = paintView;
    }


    public void init(Paint paint, Paint shadowPaint){
        this.paint = paint;
        this.shadowPaint = shadowPaint;
        paint.setColor(viewModel.color);
        shadowPaint.setColor(viewModel.color);
        infinityModeColorBlender = new InfinityModeColorBlender(viewModel, colorSelector, paint);
    }


    public void setColorSelector(ColorSelector colorSelector){
        this.colorSelector = colorSelector;
        if(infinityModeColorBlender != null){
            infinityModeColorBlender.setColorSelector(colorSelector);
        }
    }


    public void assignNextInfinityModeColor(){
        infinityModeColorBlender.assignNextInfinityModeColor();
    }


    public void updateTransparency(int value){
        viewModel.colorTransparency = value;
        paint.setAlpha(value);
        shadowPaint.setAlpha(value);
        System.out.println("ColorHelper.updateTransparency(" + value + ") invoked");
    }


    public int getNextColor(){
        return colorSelector.getNextColor();
    }

    public void resetCurrentIndex(){
        colorSelector.resetCurrentIndex();
    }


}
