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
        ButtonClickHandler<BrushShape> clickHandler = new ButtonClickHandler<>(activity, this);
        clickHandler.put(R.id.squareShapeButton, BrushShape.SQUARE);
        clickHandler.put(R.id.circleShapeButton, BrushShape.CIRCLE);
        clickHandler.put(R.id.lineShapeButton,   BrushShape.LINE);
        clickHandler.put(R.id.roundedRectangleShapeButton, BrushShape.ROUNDED_RECTANGLE);
        clickHandler.put(R.id.triangleShapeButton, BrushShape.TRIANGLE);
        clickHandler.setupClickHandler();
        clickHandler.setDefaultSelection(R.id.circleShapeButton);
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
