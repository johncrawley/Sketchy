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

public class ColorHelper {

    private final MainViewModel viewModel;
    private ColorSelector singleColorSelector;
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
        singleColorSelector = new SingleColorSelector();
        singleColorSelector.setColorList(Color.GREEN);
    }


    public void init(Paint paint, Paint shadowPaint){
        this.paint = paint;
        this.shadowPaint = shadowPaint;
        paint.setColor(viewModel.color);
        this.shadowPaint.setColor(viewModel.color);
        infinityModeColorBlender = new InfinityModeColorBlender(viewModel, singleColorSelector, paint);
    }


    public void setSingleColorSelector(ColorSelector singleColorSelector){
        this.singleColorSelector = singleColorSelector;
        if(infinityModeColorBlender != null){
            infinityModeColorBlender.setColorSelector(singleColorSelector);
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
        singleColorSelector.resetCurrentIndex();
    }

    public void assignColors(){

        if(singleColorSelector == null){
            log2("assignColors() color selector was null, initializing...2");
            singleColorSelector = new SingleColorSelector();
            singleColorSelector.setColorList(Color.YELLOW);
        }
        int nextColor = singleColorSelector.getNextColor();
        viewModel.color = nextColor;
        setColorAndAlpha(paint);
    }

    private void log2(String msg){
        System.out.println("%%%% ColorHelper: " + msg);
    }
/*

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

 */


    private void log(String msg){
        System.out.println("^^^ ColorHelper: " + msg);
    }


    private void setColorAndAlpha(Paint paint){
        paint.setColor(viewModel.color);
        paint.setAlpha(viewModel.colorAlpha);
    }

}
