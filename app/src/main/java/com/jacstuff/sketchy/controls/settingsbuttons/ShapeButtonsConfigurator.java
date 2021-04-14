package com.jacstuff.sketchy.controls.settingsbuttons;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;


public class ShapeButtonsConfigurator implements ButtonsConfigurator<BrushShape>{

    private MainActivity activity;
    private PaintView paintView;


    public ShapeButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        this.paintView = paintView;
        configure();
    }


    public void configure(){
        ButtonConfigHandler<BrushShape> buttonConfig = new ButtonConfigHandler<>(activity, this);
        buttonConfig.put(R.id.squareShapeButton,            R.drawable.square_shape_button,             BrushShape.SQUARE);
        buttonConfig.put(R.id.circleShapeButton,            R.drawable.circle_shape_button,             BrushShape.CIRCLE);
        buttonConfig.put(R.id.lineShapeButton,              R.drawable.line_shape_button,               BrushShape.LINE);
        buttonConfig.put(R.id.roundedRectangleShapeButton,  R.drawable.rounded_rectangle_shape_button,  BrushShape.ROUNDED_RECTANGLE);
        buttonConfig.put(R.id.triangleShapeButton,          R.drawable.triangle_shape_button,           BrushShape.TRIANGLE);
        buttonConfig.setupClickHandler();
        buttonConfig.setParentButton(R.id.shapeSelectionButton);
        buttonConfig.setDefaultSelection(R.id.circleShapeButton);
    }


    @Override
    public void handleClick(int viewId, BrushShape brushShape){
        paintView.set(brushShape);
        paintView.setBrushSize(activity.getBrushSize());
    }


    @Override
    public void saveSelection(int viewId){
        PaintViewSingleton.getInstance().saveShapeSelectionSetting(viewId);
    }

}
