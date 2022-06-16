package com.jacstuff.sketchy.controls.settings.menu;

import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.history.DrawHistory;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ConnectedLineIconModifier {

    private Button shapeButton;
    private final PaintView paintView;
    private final MainViewModel viewModel;
    private DrawHistory drawHistory;
    private final MainActivity activity;


    public ConnectedLineIconModifier(PaintView paintView, MainViewModel viewModel, MainActivity activity){
        this.paintView = paintView;
        this.viewModel = viewModel;
        this.activity = activity;
    }


    public void setDrawHistory(DrawHistory drawHistory){
        this.drawHistory = drawHistory;
    }


    public void assignShapeButton(){
        if(shapeButton == null) {
            shapeButton = activity.findViewById(R.id.shapeButton);
        }
    }


    public void setConnectedIconAndState(){
        if(viewModel.isConnectedLinesModeEnabled){
            viewModel.hasFirstLineBeenDrawn = true;
            shapeButton.setBackgroundResource(R.drawable.button_shape_line_connected);
        }
    }


    public void resetIconAndState(){
        viewModel.hasFirstLineBeenDrawn = false;
        assignDefaultLineIconToShapeButton();
    }


    public boolean isUsingLineShapeInConnectedMode(){
        return  paintView.getCurrentBrush().getBrushShape() == BrushShape.LINE
                && viewModel.isConnectedLinesModeEnabled;
    }


    public boolean isShapeButtonAndInConnectedLineMode(int viewId){
        return viewId == R.id.shapeButton
                && isUsingLineShapeInConnectedMode()
                && viewModel.hasFirstLineBeenDrawn;
    }


    public void updateLineIconBackground(){
        if(isUsingLineShapeInConnectedMode()){
            if(viewModel.hasFirstLineBeenDrawn){
                assignConnectedLineIconToShapeButton();
                return;
            }
        }
        assignDefaultLineIconToShapeButton();
    }


    void revertIconAndState(){
        viewModel.hasFirstLineBeenDrawn = false;
        drawHistory.updateNewestItemWithState();
        assignDefaultLineIconToShapeButton();
    }


    private void assignDefaultLineIconToShapeButton(){
        shapeButton.setBackgroundResource(R.drawable.button_shape_line);
    }


    private void assignConnectedLineIconToShapeButton(){
        shapeButton.setBackgroundResource(R.drawable.button_shape_line_connected);
    }

}
