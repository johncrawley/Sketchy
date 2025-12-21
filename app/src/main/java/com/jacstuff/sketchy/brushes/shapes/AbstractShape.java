package com.jacstuff.sketchy.brushes.shapes;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;

public abstract class AbstractShape implements Brushable{

    final BrushShape brushShape;
    private final DrawerFactory.Type drawerType;
    int brushSize, halfBrushSize;

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
        recalculateDimensions();
    }


    void recalculateDimensions(){

    }

    @Override
    public BrushShape getBrushShape(){
        return brushShape;
    }
}
