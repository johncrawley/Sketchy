package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.helpers.placement.PlacementHelper;
import com.jacstuff.sketchy.paintview.helpers.TileHelper;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public abstract class AbstractDrawer implements Drawer {


    final PaintHelperManager paintHelperManager;
    final KaleidoscopeHelper kaleidoscopeHelper;
    final KaleidoscopeDrawer kaleidoscopeDrawer;
    final TileHelper tileHelper;
    final Canvas canvas;
    Brush brush;
    PaintView paintView;
    MainViewModel viewModel;
    boolean isColorChangedOnDown = true;
    final Paint shadowPaint;
    final Paint paint;
    final PlacementHelper placementHelper;


    AbstractDrawer(PaintView paintView, MainViewModel viewModel){
        this.paintView = paintView;
        this.canvas =  paintView.getCanvas();
        this.viewModel = viewModel;
        this.paintHelperManager = paintView.getPaintHelperManager();
        kaleidoscopeHelper = paintHelperManager.getKaleidoscopeHelper();
        placementHelper = paintHelperManager.getPlacementHelper();
        tileHelper = paintHelperManager.getTileHelper();
        kaleidoscopeDrawer = new KaleidoscopeDrawer(paintView, viewModel, kaleidoscopeHelper);
        shadowPaint = paintView.getShadowPaint();
        paint = paintView.getPaint();
    }

    public void setBrush(Brush brush){
        this.brush = brush;
    }


    public void init(){
        kaleidoscopeDrawer.initParentDrawer(this);
    }


    void updateColorGradientAndAngle(float x, float y){
        paintHelperManager.getColorHelper().assignColors();
        updateGradient(x,y);
        paintHelperManager.getAngleHelper().updateAngle();
    }


    void updateGradient(float x, float y){
        paintHelperManager.getGradientHelper().assignGradient(x, y, viewModel.color);
    }


    @Override
    public void initExtra(){
        //do nothing
    }


    @Override
    public boolean isColorChangedOnDown(){
        return isColorChangedOnDown;
    }
}
