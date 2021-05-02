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
        ButtonConfigHandler<BrushStyle> buttonConfig = new ButtonConfigHandler<>(activity, this);
        buttonConfig.put(R.id.brokenOutlineStyleButton, R.drawable.broken_outline_style_button, BrushStyle.BROKEN_OUTLINE);
        buttonConfig.put(R.id.fillStyleButton,          R.drawable.fill_style_button,           BrushStyle.FILL);
        buttonConfig.put(R.id.outlineStyleButton,       R.drawable.outline_style_button,        BrushStyle.OUTLINE);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.styleSelectionButton);
        buttonConfig.setDefaultSelection(R.id.fillStyleButton);
    }

    @Override
    public void handleClick(int viewId, BrushStyle brushStyle) {
        paintView.setBrushStyle(brushStyle);
    }

    @Override
    public void saveSelection(int viewId) {
        PaintViewSingleton.getInstance().saveStyleSelectionSetting(viewId);
    }
}
