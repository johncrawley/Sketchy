package com.jacstuff.sketchy.brushes.shapes.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.shapes.Brush;
import com.jacstuff.sketchy.paintview.KaleidoscopeDrawer;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.paintview.helpers.KaleidoscopeHelper;
import com.jacstuff.sketchy.paintview.helpers.PaintHelperManager;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

public abstract class AbstractDrawer implements Drawer {


    final PaintHelperManager paintHelperManager;
    final KaleidoscopeHelper kaleidoscopeHelper;
    final KaleidoscopeDrawer kaleidoscopeDrawer;
    final Canvas canvas;
    Brush brush;
    PaintView paintView;
    boolean isColorChangedOnDown = true;
    final Paint shadowPaint;
    final Paint paint;


    AbstractDrawer(PaintView paintView, MainViewModel viewModel){
        this.paintView = paintView;
        this.canvas =  paintView.getCanvas();
        this.paintHelperManager = paintView.getPaintHelperManager();
        kaleidoscopeHelper = paintHelperManager.getKaleidoscopeHelper();
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


    @Override
    public boolean isColorChangedOnDown(){
        return isColorChangedOnDown;
    }
}
