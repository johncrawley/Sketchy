package com.jacstuff.sketchy;

public class Orb {

    private int x,y;
    private int dx,dy;

    public Orb(int x, int y, boolean dx, boolean dy, int changeDir){

        this.x = x;
        this.y = y;
        changeDirection(dx, dy);

    }


    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }


    public void update(){
        x += dx;
        y += dy;
    }

    public void changeDirection(boolean dx, boolean dy){
        this.dx = dx ? 1 : -1;
        this.dy = dy ? 1 : -1;
    }

}
