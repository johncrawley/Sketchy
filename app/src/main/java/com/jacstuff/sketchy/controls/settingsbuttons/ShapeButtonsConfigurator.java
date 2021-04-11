package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;
import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShapeButtonsConfigurator {

    private Map<Integer, BrushShape> shapeActionsMap;
    private Set<Integer> shapeButtonIds;
    private MainActivity activity;
    private ButtonUtils buttonUtils;
    private PaintView paintView;


    public ShapeButtonsConfigurator(MainActivity activity, PaintView paintView){
        this.activity = activity;
        buttonUtils = new ButtonUtils(activity);
        this.paintView = paintView;
    }

    public void configure(){
        setupMap();
        setupClickHandler();
    }


    private void setupMap(){
        shapeActionsMap = new HashMap<>();
        shapeActionsMap.put(R.id.squareShapeButton, BrushShape.SQUARE);
        shapeActionsMap.put(R.id.circleShapeButton, BrushShape.CIRCLE);
        shapeActionsMap.put(R.id.lineShapeButton,   BrushShape.LINE);
        shapeActionsMap.put(R.id.roundedRectangleShapeButton, BrushShape.ROUNDED_RECTANGLE);
        shapeActionsMap.put(R.id.triangleShapeButton, BrushShape.TRIANGLE);
        shapeButtonIds = shapeActionsMap.keySet();
    }


    private void setupClickHandler(){
        View.OnClickListener shapeListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               handleClick(view);
                PaintViewSingleton.getInstance().saveShapeSelectionSetting(view.getId());
            }
        };

        for(int id: shapeButtonIds){
            Button button = (Button)activity.findViewById(id);
            button.setOnClickListener(shapeListener);
        }
    }

    private void handleClick(View view){
        int viewId = view.getId();
        buttonUtils.switchSelection(viewId, shapeButtonIds);
        setShape(viewId);
    }


    private void setShape(int viewId){
        BrushShape brushShape = shapeActionsMap.get(viewId);
        paintView.set(brushShape);
        paintView.setBrushSize(activity.getBrushSize());
    }




}
