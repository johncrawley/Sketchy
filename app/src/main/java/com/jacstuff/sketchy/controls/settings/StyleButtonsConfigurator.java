package com.jacstuff.sketchy.controls.settings;


import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;


public class StyleButtonsConfigurator extends AbstractButtonConfigurator<BrushStyle> implements ButtonsConfigurator<BrushStyle>{


    public StyleButtonsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.STYLE,
                R.id.styleOptionsLayout);
        buttonConfig.add(R.id.brokenOutlineStyleButton, R.drawable.broken_outline_style_button, BrushStyle.BROKEN_OUTLINE);
        buttonConfig.add(R.id.fillStyleButton,          R.drawable.fill_style_button,           BrushStyle.FILL);
        buttonConfig.add(R.id.outlineStyleButton,       R.drawable.outline_style_button,        BrushStyle.OUTLINE);
        buttonConfig.add(R.id.jaggedStyleButton,        R.drawable.jagged_style_button,           BrushStyle.JAGGED);
        buttonConfig.add(R.id.wavyStyleButton,          R.drawable.button_style_wavy_b,               BrushStyle.WAVY);
        buttonConfig.add(R.id.doubleEdgeStyleButton,   R.drawable.double_edge_style_button,       BrushStyle.DOUBLE_EDGE);
        buttonConfig.add(R.id.spikedStyleButton,       R.drawable.spiked_style_button,            BrushStyle.SPIKED);
        buttonConfig.add(R.id.translateStyleButton,    R.drawable.translate_outline_style_button, BrushStyle.TRANSLATE);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.styleSelectionButton);
        buttonConfig.setDefaultSelection(R.id.fillStyleButton);
    }

    @Override
    public void handleClick(int viewId, BrushStyle brushStyle) {
        paintView.setBrushStyle(brushStyle);
    }

}
