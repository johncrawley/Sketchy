package com.jacstuff.sketchy.controls.settings.shape;

import android.os.Build;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonConfigHandler;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;


public class ShapeButtonsConfigurator extends AbstractButtonConfigurator<BrushShape> implements ButtonsConfigurator<BrushShape> {

    private final int minBrushSize;

    public ShapeButtonsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        childSettingsPanelManager.add(R.id.textShapeButton, R.id.settingsPanelTextShapeInclude);
        new TextControls(activity, paintView.getPaintGroup(), seekBarConfigurator);
        minBrushSize = activity.getResources().getInteger(R.integer.brush_size_min_default);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.SHAPE,
                R.id.shapeOptionsLayout);
        buttonConfig.add(R.id.circleShapeButton,            R.drawable.button_shape_circle,             BrushShape.CIRCLE);
        buttonConfig.add(R.id.squareShapeButton,            R.drawable.button_shape_square,             BrushShape.SQUARE);
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buttonConfig.add(R.id.pathShapeButton,              R.drawable.button_shape_path,               BrushShape.PATH);
        }
        buttonConfig.add(R.id.straightLineShapeButton,      R.drawable.button_shape_straight_line,      BrushShape.STRAIGHT_LINE);
        buttonConfig.add(R.id.roundedRectangleShapeButton,  R.drawable.button_shape_rounded_rect,       BrushShape.ROUNDED_RECTANGLE);
        buttonConfig.add(R.id.triangleShapeButton,          R.drawable.button_shape_triangle,           BrushShape.TRIANGLE);
        buttonConfig.add(R.id.diamondShapeButton,           R.drawable.button_shape_diamond,            BrushShape.DIAMOND);
        buttonConfig.add(R.id.pentagonShapeButton,          R.drawable.button_shape_pentagon,           BrushShape.PENTAGON);
        buttonConfig.add(R.id.hexagonShapeButton,           R.drawable.button_shape_hexagon,            BrushShape.HEXAGON);
        buttonConfig.add(R.id.arcShapeButton,               R.drawable.button_shape_arc,                BrushShape.ARC);
        buttonConfig.add(R.id.ovalShapeButton,              R.drawable.button_shape_oval,               BrushShape.OVAL);
        buttonConfig.add(R.id.crescentShapeButton,          R.drawable.button_shape_crescent,           BrushShape.CRESCENT);
        buttonConfig.add(R.id.xShapeButton,                 R.drawable.button_shape_x,                  BrushShape.X);
        buttonConfig.add(R.id.starShapeButton,              R.drawable.button_shape_star,               BrushShape.STAR);
        buttonConfig.add(R.id.bananaShapeButton,            R.drawable.button_shape_x,                  BrushShape.BANANA);
        buttonConfig.add(R.id.waveyLineShapeButton,         R.drawable.button_shape_wavy_line,          BrushShape.WAVY_LINE);
        buttonConfig.add(R.id.convexSquareShapeButton,      R.drawable.button_shape_rounded_rect,       BrushShape.CONVEX_SQUARE);
        buttonConfig.add(R.id.textShapeButton,              R.drawable.button_shape_text,               BrushShape.TEXT);
        buttonConfig.add(R.id.rectangleShapeButton,         R.drawable.button_shape_rectangle,          BrushShape.DRAG_RECTANGLE);
        buttonConfig.add(R.id.lineShapeButton,              R.drawable.button_shape_line,               BrushShape.LINE);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.shapeButton);
        buttonConfig.setDefaultSelection(R.id.circleShapeButton);

        configureSeekBars();
        SwitchMaterial singleDrawModeSwitch = activity.findViewById(R.id.drawOnMoveSwitch);
        singleDrawModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.isDrawOnMoveModeEnabled = isChecked);

    }


    private void configureSeekBars() {
        seekBarConfigurator.configure( R.id.brushSizeSeekBar,
                R.integer.brush_size_default
                , null,
                progress -> {
                    if (paintView != null) {
                        viewModel.brushSize = minBrushSize + progress;
                        viewModel.brushSizeSetBySeekBar = viewModel.brushSize;
                        paintView.setBrushSize(viewModel.brushSize);
                        paintHelperManager.getGradientHelper().recalculateGradientLengthForBrushSize();
                    }
                });

        seekBarConfigurator.configure(R.id.colorTransparencySeekBar,
                R.integer.color_transparency_default,
                progress -> paintHelperManager.getColorHelper().updateTransparency(progress));

    }

    @Override
    public void handleClick(int viewId, BrushShape brushShape){
        paintView.setBrushShape(brushShape);
        if(childSettingsPanelManager != null){
            childSettingsPanelManager.select(viewId);
        }
    }

}
