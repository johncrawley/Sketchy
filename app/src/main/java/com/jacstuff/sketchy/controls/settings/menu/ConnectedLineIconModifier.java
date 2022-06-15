package com.jacstuff.sketchy.controls.settings.menu;

import android.widget.Button;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.R;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class ConnectedLineIconModifier {


    private final Button shapeButton;
    private final PaintView paintView;
    private final MainViewModel viewModel;


    public ConnectedLineIconModifier(PaintView paintView, MainViewModel viewModel, MainActivity activity){
        this.paintView = paintView;
        this.viewModel = viewModel;
        shapeButton = activity.findViewById(R.id.shapeButton);
    }


    boolean handleSpecialMode(int viewId){
        if(isUsingLineShapeInConnectedMode(viewId)) {
            if(viewModel.hasFirstLineBeenDrawn) {
                viewModel.hasFirstLineBeenDrawn = false;
                shapeButton.setBackgroundResource(R.drawable.button_shape_line);
                return true;
            }
        }
        return false;
    }


    private boolean isUsingLineShapeInConnectedMode(int viewId){
        return viewId == R.id.shapeButton
                &&  paintView.getCurrentBrush().getBrushShape() == BrushShape.LINE
                && viewModel.isConnectedLinesModeEnabled;
    }

}
