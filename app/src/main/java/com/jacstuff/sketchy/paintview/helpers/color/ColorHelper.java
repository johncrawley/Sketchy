package com.jacstuff.sketchy.paintview.helpers.color;

import android.graphics.Color;
import android.graphics.Paint;

import com.jacstuff.sketchy.multicolor.ColorSelector;
import com.jacstuff.sketchy.multicolor.SequenceColorSelector;
import com.jacstuff.sketchy.multicolor.ShadeColorSelector;
import com.jacstuff.sketchy.multicolor.SingleColorSelector;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.List;

public class ColorHelper {

    private final MainViewModel viewModel;
    private ColorSelector colorSelector;
    private InfinityModeColorBlender infinityModeColorBlender;
    private Paint paint, shadowPaint;
    private final KaleidoscopeHelper kaleidoscopeHelper;
    private final SequenceColorSelector allColorsSequenceSelector;
    private final ShadeColorSelector shadeColorSelector;
    private final PaintView paintView;

    public ColorHelper(PaintView paintView, MainViewModel viewModel, KaleidoscopeHelper kaleidoscopeHelper){
        this.paintView = paintView;
        this.viewModel = viewModel;
        this.kaleidoscopeHelper = kaleidoscopeHelper;
        allColorsSequenceSelector = new SequenceColorSelector(viewModel);
        shadeColorSelector = new ShadeColorSelector(viewModel);
        colorSelector = new SingleColorSelector();
        colorSelector.setColorList(Color.GREEN);
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


    public SequenceColorSelector getAllColorsSequenceSelector(){
        return this.allColorsSequenceSelector;
    }


    public ShadeColorSelector getShadeColorSelector(){
        return this.shadeColorSelector;
    }


    public void updateTransparency(int value){
        viewModel.colorAlpha = 255 - value;
    }


    public void resetCurrentIndex(){
        colorSelector.resetCurrentIndex();
    }


    public void assignColors(){
        log("entered assignColors()");
        if(kaleidoscopeHelper.isEnabled()
                && viewModel.isInfinityModeEnabled
                && paintView.getCurrentBrush().isColorChangedOnDown()){
            infinityModeColorBlender.assignNextInfinityModeColor();
            log("kaleidoscope is enabled, returning()");
            return;
        }
        if(colorSelector == null){
            colorSelector = new SingleColorSelector();
            colorSelector.setColorList(Color.BLUE);
        }
        int nextColor = colorSelector.getNextColor();
        log("assignColors() next color: " + nextColor);
        if(viewModel.color != nextColor){
            viewModel.previousColor = viewModel.color;
        }
        viewModel.color = nextColor;
        setColorAndAlpha(paint);
        setColorAndAlpha(shadowPaint);
    }


    private void log(String msg){
        System.out.println("^^^ ColorHelper: " + msg);
    }


    private void setColorAndAlpha(Paint paint){
        paint.setColor(viewModel.color);
        paint.setAlpha(viewModel.colorAlpha);
    }

}
