package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.paintview.InfinityModeColorBlender;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ColorHelper {

    private final MainViewModel viewModel;
    private ColorSelector colorSelector;
    private InfinityModeColorBlender infinityModeColorBlender;
    private Paint paint, shadowPaint;
    private final KaleidoscopeHelper kaleidoscopeHelper;


    public ColorHelper(MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        this.viewModel = viewModel;
        this.kaleidoscopeHelper = kaleidoscopeHelper;
    }


    public void init(Paint paint, Paint shadowPaint){
        this.paint = paint;
        this.shadowPaint = shadowPaint;
        paint.setColor(viewModel.color);
        this.shadowPaint.setColor(viewModel.color);
        infinityModeColorBlender = new InfinityModeColorBlender(viewModel, colorSelector, paint);
    }


    public void setColorSelector(ColorSelector colorSelector){
        this.colorSelector = colorSelector;
        if(infinityModeColorBlender != null){
            infinityModeColorBlender.setColorSelector(colorSelector);
        }
    }


    public void updateTransparency(int value){
        //value += 1;
        viewModel.colorTransparency = 255 - value;
    }


    public void resetCurrentIndex(){
        colorSelector.resetCurrentIndex();
    }


    public void assignColors(){
        if(kaleidoscopeHelper.isEnabled() && viewModel.isInfinityModeEnabled){
            infinityModeColorBlender.assignNextInfinityModeColor();
            return;
        }
        int nextColor = colorSelector.getNextColor();
        if(viewModel.color != nextColor){
            viewModel.previousColor = viewModel.color;
        }
        viewModel.color = nextColor;
        paint.setColor(viewModel.color);
        shadowPaint.setColor(viewModel.color);
        paint.setAlpha(viewModel.colorTransparency);
        shadowPaint.setAlpha(viewModel.colorTransparency);
    }

}
