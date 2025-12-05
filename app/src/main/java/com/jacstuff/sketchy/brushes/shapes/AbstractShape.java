package com.jacstuff.sketchy.brushes.shapes;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.Easel;
import com.jacstuff.sketchy.brushes.shapes.drawer.Drawer;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;

import java.util.List;

public abstract class AbstractShape implements Brushable{

    final BrushShape brushShape;
    int brushSize, halfBrushSize;
    private Drawer drawer;
    private DrawerFactory.Type drawerType;

    public AbstractShape(BrushShape brushShape){
        this.brushShape = brushShape;
    }


    public AbstractShape(BrushShape brushShape, DrawerFactory.Type drawerType){
        this.brushShape = brushShape;
        this.drawerType = drawerType;
    }


    public void init(DrawerFactory drawerFactory){
        drawer = drawerFactory.get(drawerType);
    }


    public void onTouchDown(float x, float y, Easel easel){

    }


    public void onTouchMove(float x, float y, Easel easel){

    }


    public void onTouchUp(float x, float y, Easel easel){

    }

    @Override
    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
        halfBrushSize = this.brushSize / 2;
        recalculateDimensions();
    }


    void recalculateDimensions(){

    }

    public BrushShape getShape(){
        return brushShape;
    }
}
