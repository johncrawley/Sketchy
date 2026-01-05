package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Path;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerType;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;

public abstract class AbstractShape implements Brushable{

    protected BrushShape brushShape;
    protected DrawerType drawerType;
    protected int brushSize, halfBrushSize, quarterBrushSize;
    protected Path path;
    protected ShadowOffsetType shadowOffsetType;
    protected boolean isBrushLiableToFlicker = false;
    protected boolean usesBrushSizeControl = true;
    protected boolean isDrawnFromCenter;

    public AbstractShape(BrushShape brushShape){
        this.brushShape = brushShape;
        this.drawerType = DrawerType.BASIC;
    }


    public AbstractShape(BrushShape brushShape, DrawerType drawerType){
        this.brushShape = brushShape;
        this.drawerType = drawerType;
    }


    @Override
    public DrawerType getDrawerType(){
        return drawerType;
    }


    @Override
    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
        halfBrushSize = this.brushSize / 2;
        quarterBrushSize = Math.max(2, halfBrushSize / 2);
        recalculateDimensions();
    }


    public void recalculateDimensions(){

    }


    @Override
    public BrushShape getBrushShape(){
        return brushShape;
    }


    @Override
    public void generatePath(PointF point) {
        //do nothing
    }
}
