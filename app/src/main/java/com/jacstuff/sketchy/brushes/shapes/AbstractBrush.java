package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.jacstuff.sketchy.MainActivity;
import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.BrushStyle;
import com.jacstuff.sketchy.brushes.shapes.drawer.Drawer;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
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
    private final Map<BrushStyle, Style> styleMap;
    private final FillStyle fillStyle;
    Drawer drawer;
    MainViewModel mainViewModel;
    MainActivity mainActivity;
    PaintView paintView;
    DrawerFactory.Type drawerType;


    AbstractBrush(BrushShape brushShape){
        styleMap = new HashMap<>();
        fillStyle = new FillStyle();
        currentStyle = fillStyle;
        this.brushShape = brushShape;
        this.drawerType = DrawerFactory.Type.BASIC;
    }


    public void onTouchMove(Point p, Canvas canvas, Paint paint){
    }

    public void onTouchUp(Point p, Canvas canvas, Paint paint){
    }


    @Override
    public void init(PaintView paintView, MainActivity mainActivity, DrawerFactory drawerFactory){
        this.paintView = paintView;
        this.paintGroup = paintView.getPaintGroup();
        this.canvas = paintView.getCanvas();
        this.mainActivity = mainActivity;
        this.mainViewModel = mainActivity.getViewModel();
        drawer = drawerFactory.get(drawerType);
        drawer.init();
        postInit();
    }


    void postInit(){
        // do nothing
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


    public void reinitialize(){
        drawer.setBrush(this);
    }


    public void setStyle(BrushStyle style){
        currentStyle = styleMap.get(style);
        if(currentStyle == null){
            currentStyle = fillStyle;
        }
        currentStyle.init(paintGroup, brushSize);
    }

    @Override
    public int getBrushSize(){
        return brushSize;
    }

    public void notifyStrokeWidthChanged(){
        currentStyle.notifyStyleChange();
    }


    public final void onTouchDown(float x, float y, Paint paint){
        currentStyle.onDraw();
        onBrushTouchDown(x, y, paint);
    }

    @Override
    public final void onTouchDown(Point p, Canvas canvas, Paint paint){
        currentStyle.onDraw();
        onBrushTouchDown(p, canvas, paint);
    }


    void onBrushTouchDown(Point p, Canvas canvas, Paint paint){
        //do nothing
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

    public boolean isColorChangedOnDown(){
        return drawer.isColorChangedOnDown();
    }


    public void onTouchDownKaleidoscope(Point p, Canvas canvas, Paint paint){}
    public void onTouchMoveKaleidoscope(Point p, Canvas canvas, Paint paint){}
    public void onTouchUpKaleidoscope(Point p, Canvas canvas, Paint paint){}

}
