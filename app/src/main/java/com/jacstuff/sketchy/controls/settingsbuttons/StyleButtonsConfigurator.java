package com.jacstuff.sketchy.controls.settingsbuttons;


import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;


public class StyleButtonsConfigurator implements ButtonsConfigurator<BrushStyle>{

    private MainActivity activity;
    private PaintView paintView;


    public StyleButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }


    void configure(){
        ButtonClickHandler<BrushStyle> clickHandler = new ButtonClickHandler<>(activity, this);
        clickHandler.put(R.id.brokenOutlineStyleButton,  BrushStyle.BROKEN_OUTLINE);
        clickHandler.put(R.id.fillStyleButton,           BrushStyle.FILL);
        clickHandler.put(R.id.outlineStyleButton,        BrushStyle.OUTLINE);
        clickHandler.put(R.id.thickOutlineStyleButton,   BrushStyle.THICK_OUTLINE);
        clickHandler.setupClickHandler();
        clickHandler.setDefaultSelection(R.id.fillStyleButton);
    }

    @Override
    public void handleClick(int viewId, BrushStyle brushStyle) {
        paintView.set(brushStyle);
    }

    @Override
    public void saveSelection(int viewId) {
        PaintViewSingleton.getInstance().saveStyleSelectionSetting(viewId);
    }
}
