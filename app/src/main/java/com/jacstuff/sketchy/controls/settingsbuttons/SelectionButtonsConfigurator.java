package com.jacstuff.sketchy.controls.settingsbuttons;

import android.view.View;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;

import java.util.Collection;

public class SelectionButtonsConfigurator implements ButtonsConfigurator<Integer> {

    private MainActivity activity;
    private Collection<Integer> layoutIds;


    public SelectionButtonsConfigurator(MainActivity activity){
        this.activity = activity;
        configure();
    }


    public void configure(){
        ButtonClickHandler<Integer> clickHandler = new ButtonClickHandler<>(activity, this);
        clickHandler.put(R.id.gradientSelectionButton, R.id.includeGradientControls);
        clickHandler.put(R.id.shapeSelectionButton, R.id.includeShapeControls);
        clickHandler.put(R.id.styleSelectionButton,   R.id.includeStyleControls);
        clickHandler.put(R.id.angleSelectionButton, R.id.includeAngleControls);
        clickHandler.setupClickHandler();
        layoutIds = clickHandler.getEntries();
        clickHandler.setDefaultSelection(R.id.shapeSelectionButton);
    }


    @Override
    public void handleClick(int viewId, Integer layoutId) {
        hideAllPanels();
        activity.findViewById(layoutId).setVisibility(View.VISIBLE);
    }

    @Override
    public void saveSelection(int viewId) {

    }

    private void hideAllPanels(){
        for(int layoutId : layoutIds){
            activity.findViewById(layoutId).setVisibility(View.INVISIBLE);
        }
    }



}
