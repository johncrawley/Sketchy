package com.jacstuff.sketchy.controls.colorbuttons;

import android.widget.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RandomShadeButtonsState {

    private final Set<Button> selectedButtons;
    private boolean isMultiSelected;

    public RandomShadeButtonsState(){
        this.selectedButtons = new HashSet<>();
    }


    public void setSelected(Button button){
        selectedButtons.add(button);
    }


    public void deselect(Button button){
        selectedButtons.remove(button);
    }

    public void deselectAll(){selectedButtons.clear();}

    public void selectMulti(){
        isMultiSelected = true;
    }

    public int getSelectedCount(){
        return selectedButtons.size();
    }

    public Collection<Button> getSelected(){
        return new ArrayList<>(selectedButtons);
    }


    public boolean isMultiSelected(){
        return isMultiSelected;
    }


    public void deselectMulti(){
        isMultiSelected = false;
    }

}
