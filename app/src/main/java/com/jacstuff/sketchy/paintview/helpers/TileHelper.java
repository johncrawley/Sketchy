package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;
import android.view.MotionEvent;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class TileHelper {

    private Paint paint;
    private int columns;
    private int spacing;
    private boolean isColorUpdated;
    private PaintHelperManager paintHelperManager;
    private MainViewModel viewModel;

    public TileHelper(MainViewModel viewModel, PaintHelperManager paintHelperManager){
        this.viewModel = viewModel;
        columns = 10;
        spacing = 30;
        isColorUpdated = true;
        this.paintHelperManager = paintHelperManager;
    }

    public void init(Paint paint){
        this.paint = paint;
    }


    public void draw(MotionEvent event, Brush currentBrush) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return;
        }

        int columnOffset = currentBrush.getBrushSize() + spacing;
        for (int i = 0; i < columns; i++) {
            float offsetX = (columnOffset * i) + event.getX();
            currentBrush.touchDown(offsetX, event.getY(), paint);
            if(isColorUpdated){
                paintHelperManager.getColorHelper().assignColors();
            }
        }
    }

    public boolean isEnabled(){
        return false;
    }

}
