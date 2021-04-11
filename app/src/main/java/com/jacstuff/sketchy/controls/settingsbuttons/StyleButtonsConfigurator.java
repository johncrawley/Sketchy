package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;
import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.PaintViewSingleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StyleButtonsConfigurator {

    private Map<Integer, BrushStyle> styleActionsMap;
    private Set<Integer> styleButtonIds;
    private MainActivity activity;
    private ButtonUtils buttonUtils;
    private PaintView paintView;


    public StyleButtonsConfigurator(MainActivity activity, PaintView paintView){

        this.activity = activity;
        buttonUtils = new ButtonUtils(activity);
        this.paintView = paintView;
    }

    void configure(){
        setupMap();
        setupClickHandler();
    }


    private void setupMap(){
        styleActionsMap = new HashMap<>();
        styleActionsMap.put(R.id.brokenOutlineStyleButton,  BrushStyle.BROKEN_OUTLINE);
        styleActionsMap.put(R.id.fillStyleButton,           BrushStyle.FILL);
        styleActionsMap.put(R.id.outlineStyleButton,        BrushStyle.OUTLINE);
        styleActionsMap.put(R.id.thickOutlineStyleButton,   BrushStyle.THICK_OUTLINE);
        styleButtonIds = styleActionsMap.keySet();
    }


    private void setupClickHandler(){

        View.OnClickListener styleButtonClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               handleClick(view);
                PaintViewSingleton.getInstance().saveStyleSelectionSetting(view.getId());
            }
        };

        for(int id: styleButtonIds){
            Button button = (Button)activity.findViewById(id);
            button.setOnClickListener(styleButtonClickListener);
        }
    }


    private void handleClick(View view){
        int viewId = view.getId();
        buttonUtils.switchSelection(viewId, styleButtonIds);
        setShape(viewId);
    }



    private void setShape(int viewId){
        BrushStyle brushStyle = styleActionsMap.get(viewId);
        paintView.set(brushStyle);
    }

}
