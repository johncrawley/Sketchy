package com.jacstuff.sketchy.paintview.helpers;

import android.graphics.Paint;
import com.jacstuff.sketchy.brushes.shapes.drawer.BasicDrawer;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public class TileHelper {

    private Paint paint, previewPaint;
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


    public void init(Paint paint, Paint previewPaint){
        this.paint = paint;
        this.previewPaint = previewPaint;
    }


    public void draw(float x, float y, BasicDrawer basicDrawer) {
        int currentBrushSize = paintHelperManager.getSizeHelper().getCurrentBrushSize();
        int columnOffset = currentBrushSize + spacing;
        for (int i = 0; i < columns; i++) {
            if(isColorUpdated){
                int color = paint.getColor();
                paintHelperManager.getColorHelper().assignColors();
            }
            float offsetX = (columnOffset * i) + x;
            basicDrawer.rotateAndDraw(offsetX, y, paint);
        }
    }


    public void drawPreview(float x, float y, BasicDrawer basicDrawer) {
        int currentBrushSize = paintHelperManager.getSizeHelper().getCurrentBrushSize();
        int columnOffset = currentBrushSize + spacing;
        for (int i = 0; i < columns; i++) {
            float offsetX = (columnOffset * i) + x;
            basicDrawer.rotateAndDraw(offsetX, y, previewPaint);
        }
    }


    public boolean isEnabled(){
        return false;
    }

}
