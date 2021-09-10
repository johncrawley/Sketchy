package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;

import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.multicolor.SequenceColorSelector;
import com.jacstuff.sketchy.paintview.InfinityModeColorBlender;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ColorHelper {

    private final MainViewModel viewModel;
    private ColorSelector colorSelector;
    private InfinityModeColorBlender infinityModeColorBlender;
    private Paint paint, shadowPaint;
    private final KaleidoscopeHelper kaleidoscopeHelper;
    private final SequenceColorSelector sequenceColorSelector;


    public ColorHelper(MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        this.viewModel = viewModel;
        this.kaleidoscopeHelper = kaleidoscopeHelper;
        sequenceColorSelector = new SequenceColorSelector(viewModel);
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

    public SequenceColorSelector getSequenceColorSelector(){
        return this.sequenceColorSelector;
    }


    public void updateTransparency(int value){
        viewModel.colorAlpha = 255 - value;
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

        setColorAndAlpha(paint);
        setColorAndAlpha(shadowPaint);
    }


    private void setColorAndAlpha(Paint paint){
        paint.setColor(viewModel.color);
        paint.setAlpha( viewModel.colorAlpha);
    }

}
