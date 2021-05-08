package com.jacstuff.sketchy.brushes.shapes;

import androidx.annotation.NonNull;

class Point{
    float x, y;
    void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    @NonNull
    public String toString(){
        return " x,y: " + x + "," + y;
    }
}
