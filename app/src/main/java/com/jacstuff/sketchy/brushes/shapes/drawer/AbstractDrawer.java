package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.brushes.shapes.Brushable;
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
    boolean isColorChangedOnDown = true;
    final Paint shadowPaint;
    final Paint paint;
    final PlacementHelper placementHelper;
    protected boolean isDrawOnMoveModeEnabled;
    Brushable brushShape;


    AbstractDrawer(PaintView paintView){
        this.canvas =  paintView.getCanvas();
        this.paintHelperManager = paintView.getPaintHelperManager();
        kaleidoscopeHelper = paintHelperManager.getKaleidoscopeHelper();
        placementHelper = paintHelperManager.getPlacementHelper();
        tileHelper = paintHelperManager.getTileHelper();
        kaleidoscopeDrawer = new KaleidoscopeDrawer(paintView, kaleidoscopeHelper);
        shadowPaint = paintView.getShadowPaint();
        paint = paintView.getPaint();
    }


    public void setBrushShape(Brushable brushShape){
        this.brushShape = brushShape;
    }


    public boolean isDrawOnMoveModeEnabled(){
        return isDrawOnMoveModeEnabled;
    }


    public void setIsDrawOnMoveModeEnabled(boolean isEnabled){
        this.isDrawOnMoveModeEnabled = isEnabled;
    }


    public void setBrush(Brush brush){
        this.brush = brush;
    }


    public void init(){
        kaleidoscopeDrawer.initParentDrawer(this);
    }


    public void setPaintView(PaintView paintView){
        this.paintView = paintView;
    }


    void updateColorGradientAndAngle(float x, float y){
        var isPhmNull = paintHelperManager.getColorHelper() == null;
        log("entered updateColorGradientAndAngle() is color helper null: " + isPhmNull);
        paintHelperManager.getColorHelper().assignColors();
      //  updateGradientMidpoint(x,y);
       // paintHelperManager.getAngleHelper().updateAngle();
    }


    private void log(String msg){
        System.out.println("^^^Abstract Drawer: " + msg);
    }


    void updateGradientMidpoint(float x, float y){
        paintHelperManager.getGradientHelper().assignGradient(x, y);
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
