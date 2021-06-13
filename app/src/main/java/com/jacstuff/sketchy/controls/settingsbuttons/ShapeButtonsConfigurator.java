package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.controls.ButtonCategory;
import com.jacstuff.sketchy.paintview.PaintView;


public class ShapeButtonsConfigurator extends AbstractButtonConfigurator<BrushShape> implements ButtonsConfigurator<BrushShape>{


    public ShapeButtonsConfigurator(MainActivity activity, PaintView paintView){
        super(activity, paintView);
        childSettingsPanelManager.add(R.id.textShapeButton, R.id.settingsPanelTextShapeInclude);
    }

    @Override
    public void configure(){
        buttonConfig = new ButtonConfigHandler<>(activity,
                this,
                ButtonCategory.SHAPE,
                R.id.shapeOptionsLayout);
        buttonConfig.add(R.id.squareShapeButton,            R.drawable.button_shape_square,             BrushShape.SQUARE);
        buttonConfig.add(R.id.circleShapeButton,            R.drawable.button_shape_circle,             BrushShape.CIRCLE);
        buttonConfig.add(R.id.lineShapeButton,              R.drawable.button_shape_line,               BrushShape.LINE);
        buttonConfig.add(R.id.straightLineShapeButton,      R.drawable.button_shape_straight_line,      BrushShape.STRAIGHT_LINE);
        buttonConfig.add(R.id.waveyLineShapeButton,         R.drawable.button_shape_wavy_line,          BrushShape.WAVY_LINE);
        buttonConfig.add(R.id.roundedRectangleShapeButton,  R.drawable.button_shape_rounded_rect,       BrushShape.ROUNDED_RECTANGLE);
        buttonConfig.add(R.id.triangleShapeButton,          R.drawable.button_shape_triangle,           BrushShape.TRIANGLE);
        buttonConfig.add(R.id.pentagonShapeButton,          R.drawable.button_shape_pentagon,           BrushShape.PENTAGON);
        buttonConfig.add(R.id.starShapeButton,              R.drawable.button_shape_star,               BrushShape.STAR);
        buttonConfig.add(R.id.hexagonShapeButton,           R.drawable.button_shape_hexagon,            BrushShape.HEXAGON);
        buttonConfig.add(R.id.textShapeButton,              R.drawable.button_shape_text,               BrushShape.TEXT);
        buttonConfig.add(R.id.arcShapeButton,               R.drawable.button_shape_arc,                BrushShape.ARC);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.shapeSelectionButton);
        buttonConfig.setDefaultSelection(R.id.circleShapeButton);
    }


    @Override
    public void handleClick(int viewId, BrushShape brushShape){
        paintView.setBrushShape(brushShape);
        if(childSettingsPanelManager != null){
            childSettingsPanelManager.select(viewId);
        }
    }

}
