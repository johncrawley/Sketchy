package com.jacstuff.sketchy.brushes.shapes;

import com.jacstuff.sketchy.brushes.BrushShape;
import com.jacstuff.sketchy.brushes.Easel;

public abstract class AbstractShape{

    final BrushShape brushShape;

    public AbstractShape(BrushShape brushShape){
        this.brushShape = brushShape;
    }


    public void onTouchDown(float x, float y, Easel easel){

    }


    public void onTouchMove(float x, float y, Easel easel){

    }


    public void onTouchUp(float x, float y, Easel easel){

    }

    public BrushShape getShape(){
        return brushShape;
    }
}
