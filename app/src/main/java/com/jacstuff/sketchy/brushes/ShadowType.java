package com.jacstuff.sketchy.brushes;

public enum ShadowType {
    NONE(0,0),
    CENTER(0,0),
    NORTH(0,-1),
    NORTH_WEST(-1, -1),
    NORTH_EAST(1, -1),
    EAST(1,0),
    WEST(-1, 0),
    SOUTH(0,1),
    SOUTH_WEST(-1,1),
    SOUTH_EAST(1, 1);

    public int offsetX, offsetY;

    ShadowType(int offsetX, int offsetY){
       this.offsetX = offsetX;
       this.offsetY = offsetY;
   }
}
