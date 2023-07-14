package com.jacstuff.sketchy.controls.settings.shape;

import android.os.Build;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.controls.settings.AbstractButtonConfigurator;
import com.jacstuff.sketchy.controls.settings.ButtonConfigHandler;
import com.jacstuff.sketchy.controls.settings.ButtonsConfigurator;
import com.jacstuff.sketchy.paintview.PaintView;


public class ShapeSettings extends AbstractButtonConfigurator<BrushShape> implements ButtonsConfigurator<BrushShape> {

    private final int minBrushSize;

    public ShapeSettings(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        configureSubPanel();
        new TextControls(activity, paintView.getPaintGroup(), seekBarConfigurator);
        minBrushSize = activity.getResources().getInteger(R.integer.brush_size_min_default);
    }


    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.SHAPE,
                R.id.shapeOptionsLayout);
        createButtons();
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.shapeButton);
        buttonConfig.setDefaultSelection(R.id.circleShapeButton);
        configureSeekBars();
        configureSwitches();
    }


    @Override
    public void handleClick(int viewId, BrushShape brushShape){
        paintView.setBrushShape(brushShape);
    }


    private void createButtons(){
        buttonConfig.add(R.id.circleShapeButton,            R.drawable.button_shape_circle,             BrushShape.CIRCLE);
        buttonConfig.add(R.id.squareShapeButton,            R.drawable.button_shape_square,             BrushShape.SQUARE);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buttonConfig.add(R.id.pathShapeButton,          R.drawable.button_shape_path,               BrushShape.PATH);
        }

        buttonConfig.add(R.id.smoothPathShapeButton,        R.drawable.button_shape_smooth_path,        BrushShape.SMOOTH_PATH);
        buttonConfig.add(R.id.straightLineShapeButton,      R.drawable.button_shape_straight_line,      BrushShape.STRAIGHT_LINE);
        buttonConfig.add(R.id.roundedRectangleShapeButton,  R.drawable.button_shape_rounded_rect,       BrushShape.ROUNDED_RECTANGLE);
        buttonConfig.add(R.id.arcShapeButton,               R.drawable.button_shape_arc,                BrushShape.ARC);
        buttonConfig.add(R.id.triangleShapeButton,          R.drawable.button_shape_triangle,           BrushShape.TRIANGLE);
        buttonConfig.add(R.id.diamondShapeButton,           R.drawable.button_shape_diamond,            BrushShape.DIAMOND);
        buttonConfig.add(R.id.pentagonShapeButton,          R.drawable.button_shape_pentagon,           BrushShape.PENTAGON);
        buttonConfig.add(R.id.hexagonShapeButton,           R.drawable.button_shape_hexagon,            BrushShape.HEXAGON);
        buttonConfig.add(R.id.semicircleShapeButton,        R.drawable.button_shape_semicircle,         BrushShape.SEMICIRCLE);
        buttonConfig.add(R.id.ovalShapeButton,              R.drawable.button_shape_oval,               BrushShape.OVAL);
        buttonConfig.add(R.id.crescentShapeButton,          R.drawable.button_shape_crescent,           BrushShape.CRESCENT);
        buttonConfig.add(R.id.trapezoidShapeButton,         R.drawable.button_shape_trapezoid,          BrushShape.TRAPEZOID);
        buttonConfig.add(R.id.parallelogramShapeButton,     R.drawable.button_shape_parallelogram,      BrushShape.PARALLELOGRAM);
        buttonConfig.add(R.id.xShapeButton,                 R.drawable.button_shape_x,                  BrushShape.X);
        buttonConfig.add(R.id.starShapeButton,              R.drawable.button_shape_star,               BrushShape.STAR);
        buttonConfig.add(R.id.bananaShapeButton,            R.drawable.button_shape_banana,             BrushShape.BANANA);
        buttonConfig.add(R.id.waveyLineShapeButton,         R.drawable.button_shape_wavy_line,          BrushShape.WAVY_LINE);
        buttonConfig.add(R.id.astroidShapeButton,           R.drawable.button_shape_astroid,            BrushShape.ASTROID);
        buttonConfig.add(R.id.pointedOvalShapeButton,       R.drawable.button_shape_pointed_oval,       BrushShape.POINTED_OVAL);
        buttonConfig.add(R.id.textShapeButton,              R.drawable.button_shape_text,               BrushShape.TEXT);
        buttonConfig.add(R.id.rectangleShapeButton,         R.drawable.button_shape_rectangle,          BrushShape.DRAG_RECTANGLE);
        buttonConfig.add(R.id.variableCircleShapeButton,    R.drawable.button_variable_circle,          BrushShape.VARIABLE_CIRCLE);
        buttonConfig.add(R.id.lineShapeButton,              R.drawable.button_shape_line,               BrushShape.LINE);
        buttonConfig.add(R.id.curveShapeButton,             R.drawable.button_shape_curve,              BrushShape.CURVE);
        buttonConfig.add(R.id.triangleArbitraryShapeButton, R.drawable.button_shape_triangle_arbitrary, BrushShape.TRIANGLE_ARBITRARY);
        buttonConfig.add(R.id.spiralShapeButton,            R.drawable.button_shape_spiral,             BrushShape.SPIRAL);
        buttonConfig.add(R.id.crazySpiralShapeButton,       R.drawable.button_shape_crazy_spiral,       BrushShape.CRAZY_SPIRAL);
        buttonConfig.add(R.id.randomShapeButton ,           R.drawable.button_shape_random,             BrushShape.RANDOM);
    }


    private void configureSubPanel(){
        subPanelManager.add(R.id.textShapeButton, R.id.settingsPanelTextShapeInclude);
        subPanelManager.add(R.id.astroidShapeButton, R.id.settingsPanelAstroidShapeInclude);
        subPanelManager.add(R.id.rectangleShapeButton, R.id.settingsPanelRectangle);
        subPanelManager.add(R.id.randomShapeButton, R.id.settingsPanelRandomBrushInclude);
        subPanelManager.add(R.id.crazySpiralShapeButton, R.id.settingsPanelCrazySpiralBrushInclude);
        subPanelManager.add(R.id.triangleShapeButton, R.id.settingsPanelTriangleBrushInclude);
        subPanelManager.add(R.id.lineShapeButton, R.id.settingsPanelLineBrushInclude);
        subPanelManager.add(R.id.arcShapeButton, R.id.settingsPanelArcBrushInclude);
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

        seekBarConfigurator.configure(R.id.randomShapeVerticesSeekBar,
                R.integer.brush_random_vertices_default,
                progress -> {
                    viewModel.randomBrushNumberOfPoints = progress;
                    paintView.recalculateBrush();
                } );

        seekBarConfigurator.configure(R.id.astroidShapeCurveSeekBar,
                R.integer.astroid_shape_curve_seek_bar_default,
                progress -> {
                    viewModel.astroidShapeCurveRate = progress;
                    paintView.recalculateBrush();
                } );

        seekBarConfigurator.configure(R.id.triangleHeightSeekBar,
                R.integer.brush_triangle_height_default,
                progress -> {
                    viewModel.triangleHeight = progress;
                    paintView.recalculateBrush();
                } );

        seekBarConfigurator.configure(R.id.crazySpiralShapeTypeSeekBar,
                R.integer.brush_crazy_spiral_type_default,
                progress -> {
                    viewModel.crazySpiralType = progress;
                    paintView.recalculateBrush();
                } );

        seekBarConfigurator.configure(R.id.arcStartingAngleSeekBar,
                R.integer.brush_arc_starting_angle_default,
                progress -> viewModel.arcShapeStartingAngle = progress);

        seekBarConfigurator.configure(R.id.arcSweepAngleSeekBar,
                R.integer.brush_arc_sweep_angle_default,
                progress -> viewModel.arcShapeAngleSweepAngle = progress);
    }


    private void configureSwitches(){
        setupSwitch(R.id.drawOnMoveSwitch, b -> viewModel.isDrawOnMoveModeEnabled = b);
        setupSwitch(R.id.rectangleSnapToEdgesSwitch, b -> viewModel.isRectangleSnappedToEdges = b);
        setupSwitch(R.id.randomBrushMorphEnabledSwitch, b -> viewModel.doesRandomBrushMorph = b);
        setupSwitch(R.id.crazySpiralAltModeEnabledSwitch, b -> viewModel.isCrazySpiralAltModeEnabled = b);
        setupSwitch(R.id.connectedLineModeEnabled, b -> viewModel.connectedLineState.isConnectedModeEnabled = b);
        setupSwitch(R.id.arcBrushDrawFromCentre, b -> viewModel.isArcShapeDrawnFromCentre = b);
    }
}
