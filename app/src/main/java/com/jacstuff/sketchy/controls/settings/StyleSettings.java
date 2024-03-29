package com.jacstuff.sketchy.controls.settings;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;

import static com.jacstuff.sketchy.controls.settings.SettingsUtils.setupSpinner;


public class StyleSettings extends AbstractButtonConfigurator<BrushStyle> implements ButtonsConfigurator<BrushStyle>{


    public StyleSettings(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        setupSpinners();

        subPanelManager.add(R.id.wavyStyleButton, R.id.wavyStyleSettingsPanel);
        subPanelManager.add(R.id.dottedStyleButton, R.id.dashedStyleSettingsPanel);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.STYLE,
                R.id.styleOptionsLayout);
        buttonConfig.add(R.id.dottedStyleButton, R.drawable.button_style_dotted,      BrushStyle.BROKEN_OUTLINE);
        buttonConfig.add(R.id.fillStyleButton,          R.drawable.button_style_fill,        BrushStyle.FILL);
        buttonConfig.add(R.id.outlineStyleButton,       R.drawable.button_style_outline,     BrushStyle.OUTLINE);
        buttonConfig.add(R.id.jaggedStyleButton,        R.drawable.button_style_jagged,      BrushStyle.JAGGED);
        buttonConfig.add(R.id.wavyStyleButton,          R.drawable.button_style_wavy,        BrushStyle.WAVY);
        buttonConfig.add(R.id.doubleEdgeStyleButton,    R.drawable.button_style_double_line, BrushStyle.DOUBLE_EDGE);
        buttonConfig.add(R.id.spikedStyleButton,        R.drawable.button_style_spiked,      BrushStyle.SPIKED);
        buttonConfig.add(R.id.translateStyleButton,     R.drawable.button_style_translate,   BrushStyle.TRANSLATE);

        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.styleButton);
        buttonConfig.setDefaultSelection(R.id.fillStyleButton);
        setupSeekBars();
    }


    @Override
    public void handleClick(int viewId, BrushStyle brushStyle) {
        paintHelperManager.getStyleHelper().setBrushStyle(brushStyle);
    }


    private void setupSeekBars(){
        seekBarConfigurator.configure(R.id.lineWidthSeekBar,
                R.integer.line_width_default,
                progress -> {
                    if(paintView != null) {
                        paintView.setLineWidth(progress);
                        if(viewModel.shadowOffsetType == ShadowOffsetType.USE_STROKE_WIDTH){
                            paintHelperManager.getShadowHelper().updateOffsetFactor();
                        }
                    }
                });

        seekBarConfigurator.configure(R.id.wavyStyleLengthSeekBar,
                R.integer.wavy_style_length_progress,
                progress -> {
                    if(paintView != null) {
                        viewModel.wavyStyleLength = progress;
                        paintHelperManager.getStyleHelper().initCurrentStyle();
                    }
                });

        seekBarConfigurator.configure(R.id.wavyStyleHeightSeekBar,
                R.integer.wavy_style_height_progress,
                progress -> {
                    if(paintView != null) {
                        viewModel.wavyStyleHeight = progress;
                        paintHelperManager.getStyleHelper().initCurrentStyle();
                    }
                });

        seekBarConfigurator.configure(R.id.dashedStyleDashLengthSeekBar,
                R.integer.style_dashed_length_default,
                progress -> {
                    if(paintView != null) {
                        viewModel.dottedStyleDashLength = progress;
                        paintHelperManager.getStyleHelper().initCurrentStyle();
                    }
                });

        seekBarConfigurator.configure(R.id.dashedStyleSpacingSeekBar,
                R.integer.style_dashed_spacing_default,
                progress -> {
                    if(paintView != null) {
                        viewModel.dottedStyleSpacing = progress;
                        paintHelperManager.getStyleHelper().initCurrentStyle();
                    }
                });
    }


    private void setupSpinners(){
        setupSpinner(activity, R.id.strokeCapSpinner, R.array.stroke_cap_array,  x -> paintHelperManager.getStyleHelper().setStokeCap(x));
        setupSpinner(activity, R.id.strokeJoinSpinner,R.array.stroke_join_array, x -> paintHelperManager.getStyleHelper().setStrokeJoin(x));
    }


}
