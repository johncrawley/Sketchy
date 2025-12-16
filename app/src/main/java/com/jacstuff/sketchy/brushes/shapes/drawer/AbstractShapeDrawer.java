package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.shapes.Brushable;
import com.jacstuff.sketchy.easel.Easel;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.paintview.helpers.TileHelper;
import com.jacstuff.sketchy.paintview.helpers.placement.PlacementHelper;

public class AbstractShapeDrawer implements ShapeDrawer {

    final PaintHelperManager paintHelperManager;
   // final KaleidoscopeHelper kaleidoscopeHelper;
   // final KaleidoscopeDrawer kaleidoscopeDrawer;
   // final TileHelper tileHelper;
    Canvas canvas;
    PaintView paintView;
    boolean isColorChangedOnDown = true;
    final Paint paint;
   // final PlacementHelper placementHelper;
    protected boolean isDrawOnMoveModeEnabled;
    Brushable brushShape;


    AbstractShapeDrawer(PaintView paintView){
        this.paintView = paintView;
        this.canvas = paintView.getCanvas();
        this.paintHelperManager = paintView.getPaintHelperManager();
//        kaleidoscopeHelper = paintHelperManager.getKaleidoscopeHelper();
  //      placementHelper = paintHelperManager.getPlacementHelper();
     //   kaleidoscopeDrawer = new KaleidoscopeDrawer(paintView, kaleidoscopeHelper);
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


    public void init(){

        //kaleidoscopeDrawer.initParentDrawer(this);
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


    public void initExtra(){
        //do nothing
    }


    public boolean isColorChangedOnDown(){
        return isColorChangedOnDown;
    }


    @Override
    public void down(PointF point, Easel easel) {
        //do nothing
    }


    @Override
    public void move(PointF point, Easel easel) {
        //do nothing
    }


    @Override
    public void up(PointF point, Easel easel) {
        //do nothing
    }

}