package com.jacstuff.sketchy.model;

public class TextControlsDto {

    private String text;
    private float skew;

    public TextControlsDto(){
        text = "";
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }


    public void setTextSkew(float skew){
        this.skew = (skew - 100) / 100;
    }

    public float getTextSkew(){
        return skew;
    }
}
