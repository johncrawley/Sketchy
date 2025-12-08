package com.jacstuff.sketchy.brushes.shapes;

import android.graphics.PointF;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.Easel;
import com.jacstuff.sketchy.brushes.shapes.drawer.Drawer;
import com.jacstuff.sketchy.brushes.shapes.drawer.DrawerFactory;

public abstract class AbstractShape implements Brushable{

    final BrushShape brushShape;
    int brushSize, halfBrushSize;
    private Drawer drawer;
    private DrawerFactory.Type drawerType;

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


    public void init(DrawerFactory drawerFactory){
        drawer = drawerFactory.get(drawerType);
    }


    @Override
    public void onTouchDown(PointF p, Easel easel){
        drawer.down(p.x, p.y, easel.getFillPaint());
    }


    @Override
    public void onTouchMove(PointF p, Easel easel){
        drawer.move(p.x, p.y, easel.getFillPaint());
    }


    @Override
    public void onTouchUp(PointF p, Easel easel){
        drawer.up(p.x, p.y, easel.getFillPaint());
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
