package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.Path;
import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;
import com.jacstuff.sketchy.paintview.helpers.shadow.ShadowOffsetType;

public abstract class AbstractShape implements Brushable{

    BrushShape brushShape;
    DrawerFactory.Type drawerType;
    int brushSize, halfBrushSize, quarterBrushSize;
    Path path;
    ShadowOffsetType shadowOffsetType;

    public AbstractShape(BrushShape brushShape){
        this.brushShape = brushShape;
        this.drawerType = DrawerFactory.Type.BASIC;
    }


    public AbstractShape(BrushShape brushShape, DrawerFactory.Type drawerType){
        this.brushShape = brushShape;
        this.drawerType = drawerType;
    }


    @Override
    public DrawerFactory.Type getDrawerType(){
        return drawerType;
    }


    @Override
    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
        halfBrushSize = this.brushSize / 2;
        quarterBrushSize = Math.max(2, halfBrushSize / 2);
        recalculateDimensions();
    }


    void recalculateDimensions(){

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
