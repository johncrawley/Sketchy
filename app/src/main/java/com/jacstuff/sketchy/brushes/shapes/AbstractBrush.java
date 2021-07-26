package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.sketchy.brushes.BrushDrawer;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.drawer.BasicDrawer;
import com.jacstuff.sketchy.brushes.shapes.drawer.Drawer;
import com.jacstuff.sketchy.brushes.styles.FillStyle;
import com.jacstuff.sketchy.brushes.styles.Style;
import com.jacstuff.sketchy.paintview.PaintGroup;
import com.jacstuff.sketchy.paintview.PaintView;
import com.jacstuff.sketchy.viewmodel.MainViewModel;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractBrush implements  Brush{

    Canvas canvas;
    PaintGroup paintGroup;
    int brushSize;
    Style currentStyle;
    int halfBrushSize;
    BrushShape brushShape;
    BrushDrawer brushDrawer;
    private final Map<BrushStyle, Style> styleMap;
    private final FillStyle fillStyle;
    Drawer drawer;
    MainViewModel mainViewModel;
    PaintView paintView;


    AbstractBrush(BrushShape brushShape){
        styleMap = new HashMap<>();
        fillStyle = new FillStyle();
        currentStyle = fillStyle;
        this.brushShape = brushShape;
        brushDrawer = BrushDrawer.DEFAULT;
    }

    @Override
    public void init(PaintView paintView, MainViewModel mainViewModel){
        this.paintView = paintView;
        this.paintGroup = paintView.getPaintGroup();
        this.canvas = paintView.getCanvas();
        this.mainViewModel = mainViewModel;
        drawer = getDrawer();
        drawer.init();
    }


    Drawer getDrawer(){
        return new BasicDrawer(this, paintView, mainViewModel);
    }


    public void touchDown(float x, float y, Paint paint){
        drawer.down(x,y,paint);
    }


    public void touchMove(float x, float y, Paint paint){
        drawer.move(x,y,paint);
    }


    public void touchUp(float x, float y, Paint paint){
        drawer.up(x,y,paint);
    }


    public void add(BrushStyle brushStyle, Style style){
        styleMap.put(brushStyle, style);
    }


    public BrushShape getBrushShape(){
        return this.brushShape;
    }


    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        this.halfBrushSize = brushSize / 2;
        currentStyle.setBrushSize(paintGroup, brushSize);
        currentStyle.notifyStyleChange();
    }


    public void setStyle(BrushStyle style){
        currentStyle = styleMap.get(style);
        if(currentStyle == null){
            currentStyle = fillStyle;
        }
        currentStyle.init(paintGroup, brushSize);
    }


    public void notifyStrokeWidthChanged(){
        currentStyle.notifyStyleChange();
    }


    public final void onTouchDown(float x, float y, Paint paint){
        currentStyle.onDraw();
        onBrushTouchDown(x, y, paint);
    }


    void onBrushTouchDown(float x, float y, Paint paint){
        //do nothing
    }


    public void onTouchMove(float x, float y, Paint paint){
        onTouchDown(x ,y, paint);
    }


    public void onTouchUp(float x, float y, Paint paint){
        //do nothing
    }

    public void onTouchUp(float x, float y, float offsetX, float offsetY, Paint paint){
        //do nothing
    }

    public BrushDrawer getBrushDrawer(){
        return brushDrawer;
    }

}
