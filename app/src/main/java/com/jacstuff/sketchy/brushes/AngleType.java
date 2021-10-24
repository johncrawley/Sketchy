package com.jacstuff.sketchy.brushes;

public enum AngleType {
    ZERO(0),
    THIRTY(30),
    FORTY_FIVE(45),
    SIXTY(60),
    NINTY(90),
    ONE_THREE_FIVE(135),
    ONE_FIFTY(150),
    ONE_EIGHTY(180),
    TWO_TWENTY_FIVE(225),
    TWO_SEVENTY(270),
    THREE_FIFTEEN(315),
    MINUS_ONE(-1,true),
    PLUS_ONE(1,true),
    MINUS_FIVE(-5,true),
    PLUS_FIVE(5,true),
    MINUS_FIFTEEN(-15,true),
    PLUS_FIFTEEN(15,true),
    MINUS_THIRTY(-30, true),
    PLUS_THIRTY(30, true),
    RANDOM(0, "?"),
    OTHER(0, "_");

    private final int angleValue;
    private final boolean isIncremental;
    private final String str;

    public int get(){
        return angleValue;
    }

    public boolean isIncremental(){
        return this.isIncremental;
    }


    public String getStr(){
        return this.str;
    }

    AngleType(int angleValue){
        this(angleValue, false);
    }

    AngleType(int angleValue, String str){
        this(angleValue, false, str);
    }


    AngleType(int angleValue, boolean isIncremental){
        this.angleValue = angleValue;
        this.isIncremental = isIncremental;
        str = isIncremental && angleValue > 0 ? "+" + angleValue : "" + angleValue;
    }


    AngleType(int angleValue, boolean isIncremental, String str){
        this.str = str;
        this.angleValue = angleValue;
        this.isIncremental = isIncremental;
    }

}
